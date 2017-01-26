
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;

public class DOMParser
{
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

    public void parse(FileInputStream fileInputStream) throws IOException, SAXException
    {
        Document doc = docBuilder.parse(fileInputStream);
        Element root = doc.getDocumentElement();
        System.out.println("Root element is " + root.getNodeName());
    }
}
