import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Program
{
    public static void main(String[] args)
    {
        Program program = new Program();
        program.parseAll();
    }

    public void parseAll()
    {
        String fileName = "xml/CompanyInfo.xml";
        FileInputStream fileInputStream;

        try
        {
            fileInputStream = new FileInputStream(
                    new File(getClass().getClassLoader().getResource(fileName).getFile())
            );
        }
        catch (FileNotFoundException ex)
        {
            System.err.println(ex);
            return;
        }

        try
        {
            DOMParser domParser = new DOMParser();
            domParser.parse(fileInputStream);
        }
        catch (IOException|SAXException ex)
        {
            System.err.println(ex);
            return;
        }
    }
}
