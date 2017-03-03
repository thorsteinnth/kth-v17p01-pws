package syntactic;

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

        for (int i=0; i<WSDLs.length; i++)
        {
            List<PortTypeContainer> outputServicePortTypes =  parsePortTypes(WSDLs[i]);
            LOG.debug("OutputService parsed port type containers: " + outputServicePortTypes);

            for (int y=0; y<WSDLs.length; y++)
            {
                if (i == y)
                {
                    // Don't want to compare a file to itself
                    continue;
                }

                List<PortTypeContainer> inputServicePortTypes =  parsePortTypes(WSDLs[y]);
                LOG.debug("InputService parsed port type containers: " + inputServicePortTypes);
                compare(outputServicePortTypes, inputServicePortTypes);
                break;
            }

            break;
        }
    }

    /**
     * Compare outputs of operations of wsdl1 with inputs of operations of wsdl2
     * @param outputServicePortTypes
     * @param inputServicePortTypes
     */
    private void compare(List<PortTypeContainer> outputServicePortTypes, List<PortTypeContainer> inputServicePortTypes)
    {
        for (PortTypeContainer outputPTC : outputServicePortTypes)
        {
            for (OperationContainer outputOC : outputPTC.operations)
            {
                //TODO : For each operation compare it to every operation for the input service,
                //TODO : and if there are matches add the Matched operation to the list of matched operations
                //TODO : in the Matched object for the two services

                List<MatchedOperation> matchedOperations = findMatchedOperations(outputOC, inputServicePortTypes);
            }
        }
    }

    private List<MatchedOperation> findMatchedOperations(
            OperationContainer outputOC,
            List<PortTypeContainer> inputServicePortTypes)
    {
        List<MatchedOperation> matchedOperations = new ArrayList<>();

        for (PortTypeContainer inputPTC : inputServicePortTypes)
        {
            for (OperationContainer inputOC : inputPTC.operations)
            {
                //TODO
            }
        }

        return matchedOperations;
    }

    private List<PortTypeContainer> parsePortTypes(File wsdl)
    {
        try
        {
            List<PortTypeContainer> portTypeContainers = new ArrayList<>();

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

            XPathExpression expr = xpath.compile("//wsdl:portType");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                String portTypeName = node.getAttributes().getNamedItem("name").getNodeValue();
                PortTypeContainer portTypeContainer = new PortTypeContainer(serviceName, portTypeName);

                XPathExpression operationExpr = xpath.compile("wsdl:operation");
                NodeList operationNodeList = (NodeList) operationExpr.evaluate(node, XPathConstants.NODESET);

                portTypeContainer.operations = parseOperations(doc, operationNodeList);

                portTypeContainers.add(portTypeContainer);
            }

            return portTypeContainers;
        }
        catch (ParserConfigurationException|SAXException|XPathExpressionException|IOException ex)
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
