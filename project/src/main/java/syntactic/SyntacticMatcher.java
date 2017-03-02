package syntactic;

import common.MatchedElement;
import common.OperationContainer;
import common.PortTypeContainer;
import common.WSMatching;
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
        for (PortTypeContainer ptc : outputServicePortTypes)
        {
            for (OperationContainer opc : ptc.operations)
            {
                //TODO : For each operation compare it to every operation for the input service,
                //TODO : and if there are matches add the Matched operation to the list of matched operations
                //TODO : in the Matched object for the two services
            }
        }
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

            XPathExpression expr = xpath.compile("//wsdl:portType");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                String portTypeName = node.getAttributes().getNamedItem("name").getNodeValue();
                PortTypeContainer portTypeContainer = new PortTypeContainer(portTypeName);

                XPathExpression operationExpr = xpath.compile("wsdl:operation");
                NodeList operationNodeList = (NodeList) operationExpr.evaluate(node, XPathConstants.NODESET);

                portTypeContainer.operations = parseOperations(operationNodeList);

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

    private List<OperationContainer> parseOperations(NodeList operationNodeList)
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

                String operationInputMessage = xpath.compile("wsdl:input/@message").evaluate(operationNode);
                operationContainer.inputMessage = operationInputMessage;

                //XPathExpression operationExpr = xpath.compile("//wsdl:message/[@name=" + operationInputMessage + "]");
                //NodeList operationNodeList = (NodeList) operationExpr.evaluate(node, XPathConstants.NODESET);

                String operationOutputMessage = xpath.compile("wsdl:output/@message").evaluate(operationNode);
                operationContainer.outputMessage = operationOutputMessage;

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
                return prefix.equals("wsdl") ? "http://schemas.xmlsoap.org/wsdl/" : null;
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
