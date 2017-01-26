
import domain.Company;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DOMParser
{
    // Adapted from:
    // http://totheriver.com/learn/xml/code/DomParserExample.java

    private DocumentBuilder docBuilder;

    public DOMParser()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try
        {
            docBuilder = dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex)
        {
            System.err.println(ex);
        }
    }

    public ArrayList<Company> parseCompanyInfoXml(FileInputStream fileInputStream) throws IOException, SAXException
    {
        ArrayList<Company> companies = new ArrayList<>();

        Document doc = docBuilder.parse(fileInputStream);
        Element root = doc.getDocumentElement();

        NodeList nodeList = root.getElementsByTagName("companyinfo");
        if (nodeList != null && nodeList.getLength() > 0)
        {
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Element companyInfoElement = (Element)nodeList.item(i);
                companies.add(parseCompanyInfoElement(companyInfoElement));
            }
        }

        return companies;
    }

    private Company parseCompanyInfoElement(Element element)
    {
        String id = element.getAttribute("companyid");
        String name = getString(element, "name");
        String address = getString(element, "address");
        String phoneNumber = getString(element, "phonenumber");
        String email = getString(element, "email");

        return new Company(id, name, address, phoneNumber, email);
    }

    private String getString(Element element, String targetElementName)
    {
        NodeList nodeList = element.getElementsByTagName(targetElementName);

        // Just get the first element that matches the name we are looking for
        if (nodeList.getLength() > 0)
        {
            Node node = nodeList.item(0);
            return node.getFirstChild().getNodeValue();
        }
        else
        {
            System.err.println(element + " does not contain element with tag " + targetElementName);
            return null;
        }
    }
}
