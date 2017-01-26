
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DOMParser
{
    protected DocumentBuilder docBuilder;

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

    public static void parse(String filepath)
    {

    }
}
