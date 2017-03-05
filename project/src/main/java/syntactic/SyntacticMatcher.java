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
    private WSMatching wsMatching;
    private List<List<ServiceContainer>> parsedServiceContainers;

    public static void main(String[] args)
    {
        LOG.info("Starting Syntactic Matcher...");
        new SyntacticMatcher();
    }

    public SyntacticMatcher()
    {
        parseWSDLs();
        this.wsMatching = new WSMatching();

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
                            compare(outputService, inputService);
                    }
                }

                //break; // for now
            }

            //break;
        }

        generateOutputXML(this.wsMatching);
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

    /**
     * Compare outputs of operations of wsdl1 with inputs of operations of wsdl2
     * @param outputService
     * @param inputService
     */
    private void compare(ServiceContainer outputService, ServiceContainer inputService)
    {
        LOG.info("Comparing: " + outputService.name + " - " + inputService.name);

        Matching matching = null;
        List<MatchedOperation> matchedOperations = new ArrayList<>();

        for (PortContainer outputServicePortContainer : outputService.portContainers)
        {
            for (OperationContainer outputOC : outputServicePortContainer.portTypeContainer.operations)
            {
                // For each operation compare it to every operation for the input service,
                // and if there are matches add the Matched operation to the list of matched operations
                // in the Matched object for the two services

                matchedOperations = findMatchedOperations(outputOC, inputService.portContainers);

                if (matchedOperations.size() > 0)
                {
                    if (matching == null)
                    {
                        matching = new Matching();
                        matching.setOutputServiceName(outputService.name);
                        matching.setInputServiceName(inputService.name);
                    }

                    matching.setMatchedOperation(matchedOperations);
                    matching.setWsScore(calculateWsScore(matchedOperations));
                }
            }
        }

        if (matching != null)
        {
            List<Matching> matchingList = this.wsMatching.getMatching();
            matchingList.add(matching);
            this.wsMatching.setMatching(matchingList);
        }
    }

    private List<MatchedOperation> findMatchedOperations(
            OperationContainer outputOC,
            List<PortContainer> inputServicePortContainers)
    {
        List<MatchedOperation> matchedOperations = new ArrayList<>();
        List<MatchedElement> matchedElements = new ArrayList<>();

        for (PortContainer inputServicePortContainer : inputServicePortContainers)
        {
            for (OperationContainer inputOC : inputServicePortContainer.portTypeContainer.operations)
            {
                // Compare outputOC to every inputOC
                matchedElements = findMatchedElements(outputOC, inputOC);

                if (matchedElements.size() > 0)
                {
                    MatchedOperation matchedOperation = new MatchedOperation();
                    matchedOperation.setOutputOperationName(outputOC.name);
                    matchedOperation.setInputOperationName(inputOC.name);
                    matchedOperation.setMatchedElement(matchedElements);
                    matchedOperation.setOpScore(calculateOpScore(matchedElements));
                    matchedOperations.add(matchedOperation);
                }
            }
        }

        return matchedOperations;
    }

    private List<MatchedElement> findMatchedElements(OperationContainer outputOC, OperationContainer inputOC)
    {
        //LOG.debug("Finding matched elements for operations: " + outputOC.name + " - " + inputOC.name);

        List<MatchedElement> matchedElements = new ArrayList<>();

        for (ElementContainer outputElements : outputOC.outputMessage.elements)
        {
            for (ElementContainer inputElements : inputOC.inputMessage.elements)
            {
                matchedElements = compareElementContainers(matchedElements, outputElements, inputElements);
            }
        }

        return matchedElements;
    }

    private List<MatchedElement> compareElementContainers(
            List<MatchedElement> matchedElements,
            ElementContainer outPutElementContainer,
            ElementContainer inputElementContainer)
    {
        // Compare subelements

        for (TypeNameTuple typeNameOutput : outPutElementContainer.subelements)
        {
            for (TypeNameTuple typeNameInput : inputElementContainer.subelements)
            {
                // Not taking types into account. No mention of it in the assignment doc,
                // and some elements do not have a type.
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

    private double calculateWsScore(List<MatchedOperation> matchedOperations)
    {
        double totalScore = 0;

        for (MatchedOperation mo : matchedOperations)
        {
            totalScore = totalScore + mo.getOpScore();
        }

        return totalScore / matchedOperations.size();
    }

    private double calculateOpScore(List<MatchedElement> matchedElements)
    {
        double totalScore = 0;

        for (MatchedElement me : matchedElements)
        {
            totalScore = totalScore + me.getScore();
        }

        return totalScore / matchedElements.size();
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
}
