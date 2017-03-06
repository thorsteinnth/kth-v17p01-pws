package semantic;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import common.*;
import ontology.MyOntManager;
import org.mindswap.pellet.owlapi.Reasoner;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SemanticMatcher {

    private final static Logger LOG = LoggerFactory.getLogger(SemanticMatcher.class);

    private OWLOntologyManager manager = null;
    private OWLOntology ontology = null;
    private Reasoner reasoner = null;
    private String ontLocation = "file:data/SUMO.owl";
    private MyOntManager ontsum = null;
    private HashMap<String, OWLClass> mapName_OWLClass;
    private List<List<ServiceContainer>> parsedServiceContainers;
    private Comparer comparer;
    private Parser parser;

    public static void main(String[] args)
    {
        LOG.debug("Starting Semantic Matcher...");
        new SemanticMatcher();
    }

    public SemanticMatcher()
    {
        this.ontsum = new MyOntManager();
        this.manager = ontsum.initializeOntologyManager();
        this.ontology = ontsum.initializeOntology(manager, ontLocation);
        this.reasoner = ontsum.initializeReasoner(ontology, manager);
        this.mapName_OWLClass = ontsum.loadClasses(reasoner);

        this.comparer = new SemanticComparer();
        this.parser = new Parser(true);
        parseSAWSDLs();

        for (int i = 0; i < parsedServiceContainers.size(); i++)
        {
            for (int y = i; y < parsedServiceContainers.size(); y++)
            {
                if (i == y)
                {
                    // Don't want to compare a file to itself
                    continue;
                }

                for (ServiceContainer outputService : parsedServiceContainers.get(i))
                {
                    for (ServiceContainer inputService : parsedServiceContainers.get(y))
                    {
                        if (outputService != null && outputService.portContainers != null
                                && inputService != null && inputService.portContainers != null)
                            this.comparer.compare(outputService, inputService);
                    }
                }
            }
        }

        generateOutputXML(this.comparer.getWsMatching());
    }

    // TODO : Find Service -> Port -> PortType -> Operations (input / output) -> MessageContainer (has multiple elements/parts)
    // TODO : -> for each part/element find the type, then lookup the the element name in the xsd:schema (by element type)
    // TODO : - the element name can be used as a parameter into the getMatchingDegreeFunction

    private void parseSAWSDLs()
    {
        File[] SAWSDLs = getSAWSDLs();
        this.parsedServiceContainers = new ArrayList<>();
        for (int i = 0; i < SAWSDLs.length; i++)
        {
            try
            {
                List<ServiceContainer> serviceContainers = this.parser.parseServices(SAWSDLs[i]);
                this.parsedServiceContainers.add(serviceContainers);
                LOG.info("Parsed services for SAWSDL " + i + ": " + SAWSDLs[i] + " - " + serviceContainers);
            }
            catch (Exception ex)
            {
                LOG.error("Could not parse SAWSDL: " + SAWSDLs[i] + " - " + ex.toString());
            }
        }
    }

    /**
     * Calculates the matching degree for higher level types that can all be found in
     * the SUMO.owl ontology file
     * @param outputType
     * @param inputType
     * @return matching degree from 1 to 0
     */
    private double getMatchingDegree(String outputType, String inputType) {

        OWLClass outputClass = mapName_OWLClass.get(outputType.toLowerCase());
        OWLClass inputClass = mapName_OWLClass.get(inputType.toLowerCase());

        if (outputClass == null || inputClass == null)
            return 0;

        if (outputClass.equals(inputClass)){
            return 1; // Exact
        }
        else if (reasoner.isSubClassOf(inputClass, outputClass)) {
            return 0.8; // Subsumption
        }
        else if (reasoner.isSubClassOf(outputClass, inputClass)) {
            return 0.6; // Plug-in
        }
        else if (ontsum.findRelationship (outputClass, inputClass, reasoner).size() > 0) {
            return 0.5; // Structural
        }
        else {
            return 0;
        }
    }

    private File[] getSAWSDLs()
    {
        try
        {
            return (new File(getClass().getResource("/SAWSDLs").toURI())).listFiles();
        }
        catch (URISyntaxException ex)
        {
            LOG.error(ex.toString());
            return new File[0];
        }
    }

    private void generateOutputXML(WSMatching wsMatching)
    {
        File syntacticOutputXML = new File("output_xml/SemanticOutput.xml");

        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(WSMatching.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.kth.se/ict/id2208/Matching Output.xsd ");
            jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                @Override
                public String getPreferredPrefix(String arg0, String arg1, boolean arg2) {
                    return "tns";
                }
            });
            jaxbMarshaller.marshal(wsMatching, syntacticOutputXML);
        }
        catch (JAXBException ex)
        {
            System.err.println(ex);
        }
    }

    private class SemanticComparer extends Comparer
    {
        @Override
        public List<MatchedElement> compareElementContainers(
                PartContainer outputPartContainer,
                PartContainer inputPartContainer)
        {
            List<MatchedElement> matchedElements = new ArrayList<>();

            if (outputPartContainer.SAWSDLModelReference == null || inputPartContainer.SAWSDLModelReference == null)
                return matchedElements;

            double matchingDegree = getMatchingDegree(
                    outputPartContainer.getSAWSDLModelReferenceRightSideOfHashtag(),
                    inputPartContainer.getSAWSDLModelReferenceRightSideOfHashtag()
            );

            // Matching threshold 0.5
            if (matchingDegree >= 0.5)
            {
                if (!outputPartContainer.name.equals(inputPartContainer.name))
                {
                    LOG.debug("Different words matching matching degree "
                            + matchingDegree + ": " + outputPartContainer.name + " - " + inputPartContainer.name);
                }

                MatchedElement matchedElement = new MatchedElement();
                matchedElement.setOutputElement(outputPartContainer.name);
                matchedElement.setInputElement(inputPartContainer.name);
                matchedElement.setScore(matchingDegree);
                matchedElements.add(matchedElement);
            }

            return matchedElements;
        }
    }
}
