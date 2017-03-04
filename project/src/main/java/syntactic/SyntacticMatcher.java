package syntactic;

import com.predic8.schema.ComplexType;
import com.predic8.schema.Element;
import com.predic8.schema.Schema;
import com.predic8.wsdl.*;
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
                List<ServiceContainer> serviceContainers = parseServices(WSDLs[i]);
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

    //region Homemade parsers
    /*

    private ServiceContainer parseService(File wsdl) throws SAXException
    {
        ServiceContainer serviceContainer = new ServiceContainer();

        try
        {
            DocumentBuilder builder = this.documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.parse(wsdl);

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            XPathExpression getServiceNameExpr = xpath.compile("//wsdl:service");
            NodeList serviceNameNodes = (NodeList) getServiceNameExpr.evaluate(doc, XPathConstants.NODESET);
            String serviceName = "";

            for (int i = 0; i < serviceNameNodes.getLength(); i++) {
                Node node = serviceNameNodes.item(i);
                serviceName = node.getAttributes().getNamedItem("name").getNodeValue();

                if (!serviceName.equals(""))
                    break;
            }

            serviceContainer.name = serviceName;

            XPathExpression getPortTypesExptr = xpath.compile("//wsdl:portType");
            NodeList portTypeNodes = (NodeList) getPortTypesExptr.evaluate(doc, XPathConstants.NODESET);

            serviceContainer.portTypeContainers = parsePortTypes(doc, portTypeNodes);
        }
        catch (ParserConfigurationException|XPathExpressionException|IOException ex)
        {
            LOG.error(ex.toString());
        }
        finally
        {
            return serviceContainer;
        }
    }

    private List<PortTypeContainer> parsePortTypes(Document doc, NodeList portTypeNodeList)
    {
        try
        {
            List<PortTypeContainer> portTypeContainers = new ArrayList<>();

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            for (int i = 0; i < portTypeNodeList.getLength(); i++)
            {
                Node node = portTypeNodeList.item(i);
                String portTypeName = node.getAttributes().getNamedItem("name").getNodeValue();
                PortTypeContainer portTypeContainer = new PortTypeContainer(portTypeName);

                XPathExpression operationExpr = xpath.compile("wsdl:operation");
                NodeList operationNodeList = (NodeList) operationExpr.evaluate(node, XPathConstants.NODESET);

                portTypeContainer.operations = parseOperations(doc, operationNodeList);

                portTypeContainers.add(portTypeContainer);
            }

            return portTypeContainers;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return new ArrayList<>();
        }
    }

    private List<OperationContainer> parseOperations(Document doc, NodeList operationNodeList)
    {
        try
        {
            List<OperationContainer> operationContainers = new ArrayList<>();

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            for (int i = 0; i < operationNodeList.getLength(); i++)
            {
                Node operationNode = operationNodeList.item(i);
                String operationName = operationNode.getAttributes().getNamedItem("name").getNodeValue();
                OperationContainer operationContainer = new OperationContainer(operationName);

                String operationInputMessageName = xpath.compile("wsdl:input/@message").evaluate(operationNode);
                operationContainer.inputMessage = parseMessage(doc, operationInputMessageName);

                String operationOutputMessageName = xpath.compile("wsdl:output/@message").evaluate(operationNode);
                operationContainer.outputMessage = parseMessage(doc, operationOutputMessageName);

                operationContainers.add(operationContainer);
            }

            return operationContainers;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return new ArrayList<>();
        }
    }

    private MessageContainer parseMessage(Document doc, String messageName)
    {
        try
        {
            String[] messageNameNamespaceSplit = messageName.split(":");
            if (messageNameNamespaceSplit.length == 2)
                messageName = messageNameNamespaceSplit[1];

            MessageContainer messageContainer = new MessageContainer(messageName);

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            // Search for the message element somewhere in the doc
            XPathExpression messageExpr = xpath.compile("//wsdl:message[@name='" + messageName + "']");
            Node messageNode = (Node) messageExpr.evaluate(doc, XPathConstants.NODE);

            XPathExpression messagePartExpr = xpath.compile("wsdl:part");
            NodeList messageParts = (NodeList) messagePartExpr.evaluate(messageNode, XPathConstants.NODESET);

            for (int i = 0; i < messageParts.getLength(); i++)
            {
                Node partNode = messageParts.item(i);
                String partElementName = partNode.getAttributes().getNamedItem("element").getNodeValue();
                messageContainer.elements.add(parseElement(doc, partElementName));
            }

            return messageContainer;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return null;
        }
    }

    private ElementContainer parseElement(Document doc, String elementName)
    {
        try
        {
            String[] elementNameNamespaceSplit = elementName.split(":");
            if (elementNameNamespaceSplit.length == 2)
                elementName = elementNameNamespaceSplit[1];

            ElementContainer elementContainer = new ElementContainer(elementName);

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            // Search for the element somewhere in the doc
            XPathExpression elementExpr = xpath.compile("//wsdl:types/s:schema/s:element[@name='" + elementName + "']");
            Node elementNode = (Node) elementExpr.evaluate(doc, XPathConstants.NODE);

            // Hierarchy should be elementNode -> complexType -> sequence -> elements that we are looking for
            XPathExpression elementSequenceExpr = xpath.compile("s:complexType/s:sequence/s:element");
            NodeList sequenceElements = (NodeList) elementSequenceExpr.evaluate(elementNode, XPathConstants.NODESET);

            for (int i = 0; i < sequenceElements.getLength(); i++)
            {
                Node sequenceElement = sequenceElements.item(i);
                String sequenceElementName = sequenceElement.getAttributes().getNamedItem("name").getNodeValue();
                String sequenceElementType = sequenceElement.getAttributes().getNamedItem("type").getNodeValue();
                if (shouldMatchElementType(sequenceElementType))
                    elementContainer.subelements.add(sequenceElementName);
            }

            return elementContainer;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return null;
        }
    }

    */
    //endregion

    //region Predic8 parsers

    private List<ServiceContainer> parseServices(File wsdl)
    {
        LOG.info("Parsing services for: " + wsdl);

        WSDLParser parser = new WSDLParser();
        Definitions defs = parser.parse(wsdl.getAbsolutePath());

        List<ServiceContainer> serviceContainers = new ArrayList<>();

        for (Service service : defs.getServices())
        {
            ServiceContainer serviceContainer = new ServiceContainer(service.getName());
            serviceContainer.portContainers.addAll(parsePorts(service.getPorts()));
            serviceContainers.add(serviceContainer);
        }

        return serviceContainers;
    }

    private List<PortContainer> parsePorts(List<Port> ports)
    {
        /*
        https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_Fuse/6.0/html/Using_the_Web_Services_Bindings_and_Transports/files/FUSECXFBindingIntro.html
        To ensure that an endpoint defines only a single service, WSDL requires that a binding can only represent a single port type.
        For example, if you had a contract with two port types, you could not write a single binding that mapped both of them
        into a concrete data format. You would need two bindings.
        */

        List<PortContainer> portContainers = new ArrayList<>();

        for (Port port : ports)
        {
            PortContainer portContainer = new PortContainer(port.getName());

            PortType portType = port.getBinding().getPortType();
            PortTypeContainer portTypeContainer = new PortTypeContainer(portType.getName());

            for (Operation operation : portType.getOperations())
            {
                OperationContainer operationContainer = new OperationContainer(operation.getName());
                operationContainer.inputMessage = parseMessage(operation.getInput().getMessage());
                operationContainer.outputMessage = parseMessage(operation.getOutput().getMessage());

                portTypeContainer.operations.add(operationContainer);
            }

            portContainer.portTypeContainer = portTypeContainer;
            portContainers.add(portContainer);
        }

        return portContainers;
    }

    private MessageContainer parseMessage(Message message)
    {
        MessageContainer messageContainer = new MessageContainer(message.getName());

        for (Part part : message.getParts())
        {
            // Part can have an element that we need to dig further down to find, or just a type (e.g. string)
            Element partElement = part.getElement();

            if (partElement != null)
            {
                ElementContainer elementContainer = new ElementContainer(partElement.getName());
                if (partElement.getEmbeddedType() instanceof ComplexType)
                {
                    ComplexType partElementComplexType = (ComplexType) partElement.getEmbeddedType();

                    // Flatten all complex types below here to their basic types
                    elementContainer.subelements.addAll(flattenComplexTypeRecursive(partElementComplexType));
                }

                messageContainer.elements.add(elementContainer);
            }
            else
            {
                // Check if we have a type here instead
                // Let's just put the part element into an element container, with the part itself as a subelement

                ElementContainer elementContainer = new ElementContainer(part.getName());
                if (part.getType() != null)
                {
                    String partTypeName = (part.getType().getName() != null ? part.getType().getName() : part.getTypePN().toString());
                    elementContainer.subelements.add(new TypeNameTuple(partTypeName, part.getName()));
                }

                messageContainer.elements.add(elementContainer);
            }
        }

        return messageContainer;
    }

    private List<TypeNameTuple> flattenComplexTypeRecursive(ComplexType complexType)
    {
        List<TypeNameTuple> typeNameTuples = new ArrayList<>();

        // TODO
        // Complex type may have a "sequence", or "all" or some others.
        // It doesn't look like the predic8 parser can handle anything other than sequence.
        // Just ignoring the type then (for now at least)
        // (e.g. in RegalTechnologiesDevConnectAPIProfile.wsdl)
        if (complexType.getSequence() == null)
            return typeNameTuples;

        for (Element sequenceElement : complexType.getSequence().getElements())
        {
            if (isElementComplexType(sequenceElement))
            {
                // This is a complex type
                ComplexType sequenceElementComplex = getComplexTypeByTypeName(sequenceElement.getSchema(),
                        sequenceElement.getTypeString(sequenceElement.getType()));

                typeNameTuples.addAll(flattenComplexTypeRecursive(sequenceElementComplex));
            }
            else
            {
                // Some elements in the given WSDLs do not have a type (e.g. TripAuthorityAPIProfile.xml)
                String typeName;
                if (sequenceElement.getType() != null)
                    typeName = sequenceElement.getTypeString(sequenceElement.getType());
                else
                    typeName = null;

                TypeNameTuple typeNameTuple = new TypeNameTuple(typeName, sequenceElement.getName());
                typeNameTuples.add(typeNameTuple);
            }
        }

        return typeNameTuples;
    }

    private ComplexType getComplexTypeByTypeName(Schema schema, String typeName)
    {
        // Complex type names in schema do not contain namespaces. Remove it if it is there.
        typeName = removeNamespace(typeName);
        ComplexType foundComplexType = schema.getComplexType(typeName);
        return foundComplexType;
    }

    private boolean isElementComplexType(Element element)
    {
        // Let's compare the name of the element with the complex types in the schema.
        // complexTypes.contains() does not work here

        // Some elements in the given WSDLs do not have a type (e.g. TripAuthorityAPIProfile.xml)
        if (element.getType() == null)
            return false;

        List<String> complexTypeNames = new ArrayList<>();
        for (ComplexType complexType : element.getSchema().getComplexTypes())
            complexTypeNames.add(complexType.getName());

        String elementTypeName = element.getTypeString(element.getType());
        // This element type name may contain a namespace, but the complex type names do not. Remove namespace
        elementTypeName = removeNamespace(elementTypeName);

        if (complexTypeNames.contains(elementTypeName))
            return true;

        return false;
    }

    private String removeNamespace(String name)
    {
        String[] split = name.split(":");
        if (split.length == 2)
            return split[1];
        else
            return name;
    }

    //endregion

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

    /*
    private NamespaceContext getWsdlNamespaceContext()
    {
        return new NamespaceContext()
        {
            @Override
            public String getNamespaceURI(String prefix)
            {
                if (prefix.equals("wsdl"))
                    return "http://schemas.xmlsoap.org/wsdl/";
                else if (prefix.equals("s"))
                    return "http://www.w3.org/2001/XMLSchema";
                else
                    return null;
            }

            @Override
            public String getPrefix(String namespaceURI)
            {
                return null;
            }

            @Override
            public Iterator getPrefixes(String namespaceURI)
            {
                return null;
            }
        };
    }

    // Only consider basic elements (those with built-in types such as int, double, string, date, ...) for matching
    private boolean shouldMatchElementType(String type)
    {
        if (type.equals("s:string"))
            return true;
        else if (type.equals("s:double"))
            return true;
        else if (type.equals("s:dateTime"))
            return true;
        else if (type.equals("s:int"))
            return true;
        else
            return false;
    }
    */

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
