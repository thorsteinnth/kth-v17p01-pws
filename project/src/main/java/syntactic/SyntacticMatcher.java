package syntactic;

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
import java.util.Iterator;

public class SyntacticMatcher
{
    private final static Logger LOG = LoggerFactory.getLogger(SyntacticMatcher.class);
    private DocumentBuilderFactory documentBuilderFactory;
    private XPathFactory xPathfactory;

    public static void main(String[] args)
    {
        LOG.debug("Starting Syntactic Matcher...");
        new SyntacticMatcher();
    }

    public SyntacticMatcher()
    {
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilderFactory.setNamespaceAware(true);
        this.xPathfactory = XPathFactory.newInstance();

        File[] WSDLs = getWSDLs();

        for (int i=0; i<WSDLs.length; i++)
        {
            for (int y=0; y<WSDLs.length; y++)
            {
                if (i == y)
                {
                    // Don't want to compare a file to itself
                    continue;
                }

                compare(WSDLs[i], WSDLs[y]);
            }
        }
    }

    /**
     * Compare outputs of operations of wsdl1 with inputs of operations of wsdl2
     * @param wsdl1
     * @param wsdl2
     */
    private void compare(File wsdl1, File wsdl2)
    {
        //LOG.debug("Should compare WSDLs: " + wsdl1.toString() + " - " + wsdl2.toString());
        parseOutputOfOperations(wsdl1);
    }

    private void parseOutputOfOperations(File wsdl)
    {
        try
        {
            DocumentBuilder builder = this.documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.parse(wsdl);

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            XPathExpression expr = xpath.compile("//wsdl:portType");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i<nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                System.out.println("Found node: " + node.getLocalName());
            }
        }
        catch (ParserConfigurationException|SAXException|XPathExpressionException|IOException ex)
        {
            LOG.error(ex.toString());
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
