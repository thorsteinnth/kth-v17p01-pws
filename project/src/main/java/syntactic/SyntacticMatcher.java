package syntactic;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SyntacticMatcher
{
    private final static Logger LOG = LoggerFactory.getLogger(SyntacticMatcher.class);
    private List<List<ServiceContainer>> parsedServiceContainers;
    private Comparer comparer;

    public static void main(String[] args)
    {
        LOG.info("Starting Syntactic Matcher...");
        new SyntacticMatcher();
    }

    public SyntacticMatcher()
    {
        parseWSDLs();
        this.comparer = new SyntacticComparer();

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

    private void parseWSDLs()
    {
        File[] WSDLs = getWSDLs();
        this.parsedServiceContainers = new ArrayList<>();
        for (int i = 0; i < WSDLs.length; i++)
        {
            try
            {
                List<ServiceContainer> serviceContainers = Parser.parseServices(WSDLs[i]);
                this.parsedServiceContainers.add(serviceContainers);
                LOG.info("Parsed services for: " + WSDLs[i] + " - " + serviceContainers);
            }
            catch (Exception ex)
            {
                LOG.error("Could not parse WSDL: " + WSDLs[i] + " - " + ex.toString());
            }
        }
    }

    private File[] getWSDLs()
    {
        try
        {
            return (new File(getClass().getResource("/WSDLs").toURI())).listFiles();
        }
        catch (URISyntaxException ex)
        {
            LOG.error(ex.toString());
            return new File[0];
        }
    }

    private void generateOutputXML(WSMatching wsMatching)
    {
        File syntacticOutputXML = new File("output_xml/SyntacticOutput.xml");

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

    private class SyntacticComparer extends Comparer
    {
        @Override
        public List<MatchedElement> compareElementContainers(
                List<MatchedElement> matchedElements,
                PartContainer outPutPartContainer,
                PartContainer inputPartContainer)
        {
            // Compare sub elements

            for (TypeNameTuple typeNameOutput : outPutPartContainer.subelements)
            {
                for (TypeNameTuple typeNameInput : inputPartContainer.subelements)
                {
                    // Not taking types into account. No mention of it in the assignment doc,
                    // and some sub elements do not have a type.
                    //if (typeNameOutput.type.equals(typeNameInput.type))
                    {
                        // Disregarding null names
                        if (typeNameOutput.name == null || typeNameInput.name == null)
                            continue;

                        //LOG.debug("Getting similarity for: " + typeNameOutput.name + " - " + typeNameInput.name);

                        double distance = EditDistance.getSimilarity(typeNameOutput.name, typeNameInput.name);

                        if (distance >= 0.8)
                        {
                            if (!typeNameOutput.name.equals(typeNameInput.name))
                            {
                                LOG.debug("Different words matching with distance "
                                        + distance + ": " + typeNameOutput.name + " - " + typeNameInput.name);
                            }

                            MatchedElement matchedElement = new MatchedElement();
                            matchedElement.setOutputElement(typeNameOutput.name);
                            matchedElement.setInputElement(typeNameOutput.name);
                            matchedElement.setScore(distance);
                            matchedElements.add(matchedElement);
                        }
                    }
                }
            }

            return matchedElements;
        }
    }
}
