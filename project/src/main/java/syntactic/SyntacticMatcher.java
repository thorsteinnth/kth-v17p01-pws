package syntactic;

import com.predic8.schema.ComplexType;
import com.predic8.schema.Element;
import com.predic8.schema.Schema;
import com.predic8.schema.Sequence;
import com.predic8.wsdl.*;
import common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SyntacticMatcher
{
    private final static Logger LOG = LoggerFactory.getLogger(SyntacticMatcher.class);

    private WSMatching wsMatching;

    private DocumentBuilderFactory documentBuilderFactory;
    private XPathFactory xPathfactory;

    public static void main(String[] args)
    {
        LOG.debug("Starting Syntactic Matcher...");
        new SyntacticMatcher();
    }

    public SyntacticMatcher()
    {
        this.wsMatching = new WSMatching();

        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilderFactory.setNamespaceAware(true);
        this.xPathfactory = XPathFactory.newInstance();

        File[] WSDLs = getWSDLs();

        List<PortTypeContainer> portTypeContainers = parsePortTypes(WSDLs[0]);
        LOG.debug("OutputService - parsed service for: " + WSDLs[0] + " - " + portTypeContainers);

        /*
        for (int i=0; i<WSDLs.length; i++)
        {
            ServiceContainer outputService;
            ServiceContainer inputService;

            try
            {
                outputService = parseService(WSDLs[i]);
                LOG.debug("OutputService - parsed service for: " + WSDLs[i] + " - " + outputService);
            }
            catch (SAXException ex)
            {
                LOG.error("Unable to parse output service " + WSDLs[i] + " - " + ex.toString());
                continue;
            }

            for (int y=0; y<WSDLs.length; y++)
            {
                if (i == y)
                {
                    // Don't want to compare a file to itself
                    continue;
                }

                try
                {
                    inputService = parseService(WSDLs[y]);
                    LOG.debug("InputService - parsed service for: " + WSDLs[y] + " - " + inputService);
                }
                catch (SAXException ex)
                {
                    LOG.error("Unable to parse input service " + WSDLs[y] + " - " + ex.toString());
                    continue;
                }

                if (outputService != null && outputService.portTypeContainers != null
                        && inputService != null && inputService.portTypeContainers != null)
                    compare(outputService, inputService);
            }

            break;
        }*/

        //generateOutputXML(this.wsMatching);
    }

    /**
     * Compare outputs of operations of wsdl1 with inputs of operations of wsdl2
     * @param outputService
     * @param inputService
     */
    private void compare(ServiceContainer outputService, ServiceContainer inputService)
    {
        Matching matching = null;
        List<MatchedOperation> matchedOperations = new ArrayList<>();

        for (PortTypeContainer outputPTC : outputService.portTypeContainers)
        {
            for (OperationContainer outputOC : outputPTC.operations)
            {
                // For each operation compare it to every operation for the input service,
                // and if there are matches add the Matched operation to the list of matched operations
                // in the Matched object for the two services

                matchedOperations = findMatchedOperations(outputOC, inputService.portTypeContainers);

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
            List<PortTypeContainer> inputServicePortTypes)
    {
        List<MatchedOperation> matchedOperations = new ArrayList<>();
        List<MatchedElement> matchedElements = new ArrayList<>();

        for (PortTypeContainer inputPTC : inputServicePortTypes)
        {
            for (OperationContainer inputOC : inputPTC.operations)
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
        List<MatchedElement> matchedElements = new ArrayList<>();

        //TODO

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

    private List<PortTypeContainer> parsePortTypes(File wsdl)
    {
        WSDLParser parser = new WSDLParser();
        Definitions defs = parser.parse(wsdl.getAbsolutePath());

        List<PortTypeContainer> portTypeContainers = new ArrayList<>();

        List<PortType> portTypes = defs.getPortTypes();

        for (PortType portType : portTypes)
        {
            PortTypeContainer portTypeContainer = new PortTypeContainer(portType.getName());

            for (Operation operation : portType.getOperations())
            {
                OperationContainer operationContainer = new OperationContainer(operation.getName());
                operationContainer.inputMessage = parseMessage(defs, operation.getInput().getMessage());
                operationContainer.outputMessage = parseMessage(defs, operation.getOutput().getMessage());

                portTypeContainer.operations.add(operationContainer);
            }

            portTypeContainers.add(portTypeContainer);
        }

        return portTypeContainers;
    }

    private MessageContainer parseMessage(Definitions defs, Message message)
    {
        MessageContainer messageContainer = new MessageContainer(message.getName());

        for (Part part : message.getParts())
        {
            Element partElement = part.getElement();
            ElementContainer elementContainer = new ElementContainer(partElement.getName());
            if (partElement.getEmbeddedType() instanceof ComplexType)
            {
                ComplexType partElementComplexType = (ComplexType)partElement.getEmbeddedType();

                // Flatten all complex types below here to their basic types
                elementContainer.subelements.addAll(flattenComplexTypeRecursive(partElementComplexType));
            }

            messageContainer.elements.add(elementContainer);
        }

        return messageContainer;
    }

    private List<TypeNameTuple> flattenComplexTypeRecursive(ComplexType complexType)
    {
        List<TypeNameTuple> typeNameTuples = new ArrayList<>();

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
                TypeNameTuple typeNameTuple = new TypeNameTuple(sequenceElement.getTypeString(sequenceElement.getType()), sequenceElement.getName());
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

    /**
     * Only consider basic elements (those with built-in types such as int, double, string, date, ...) for matching
     */
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

    private float calculateWsScore(List<MatchedOperation> matchedOperations)
    {
        float totalScore = 0;

        for (MatchedOperation mo : matchedOperations)
        {
            totalScore = totalScore + mo.getOpScore();
        }

        return totalScore / matchedOperations.size();
    }

    private float calculateOpScore(List<MatchedElement> matchedElements)
    {
        float totalScore = 0;

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
            jaxbMarshaller.marshal(wsMatching, syntacticOutputXML);
        }
        catch (JAXBException ex)
        {
            System.err.println(ex);
        }
    }
}
