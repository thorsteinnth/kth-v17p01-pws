package semantic;

import common.Parser;
import common.ServiceContainer;
import ontology.MyOntManager;
import org.mindswap.pellet.owlapi.Reasoner;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static void main(String[] args)
    {
        LOG.debug("Starting Semantic Matcher...");
        new SemanticMatcher();
    }

    public SemanticMatcher()
    {
        parseSAWSDLs();

        this.ontsum = new  MyOntManager();
        this.manager = ontsum.initializeOntologyManager();
        this.ontology = ontsum.initializeOntology(manager, ontLocation);
        this.reasoner = ontsum.initializeReasoner(ontology, manager);
        this.mapName_OWLClass = ontsum.loadClasses(reasoner);
    }

    // TODO : Find Service -> Port -> PortType -> Operations (input / output) -> MessageContainer (has multiple parts/elements)
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
                List<ServiceContainer> serviceContainers = Parser.parseServices(SAWSDLs[i]);
                this.parsedServiceContainers.add(serviceContainers);
                LOG.info("Parsed services for SAWSDL " + i + ": " + SAWSDLs[i] + " - " + serviceContainers);
            }
            catch (Exception ex)
            {
                LOG.error("Could not parse SAWSDL: " + SAWSDLs[i] + " - " + ex.toString());
            }
        }
    }

    private void compare(ServiceContainer outputService, ServiceContainer inputService)
    {}

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
}
