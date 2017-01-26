import domain.Company;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
            ArrayList<Company> companies = domParser.parseCompanyInfoXml(fileInputStream);
            System.out.println("Parsed companies: " + companies.toString());
        }
        catch (IOException|SAXException ex)
        {
            System.err.println(ex);
            return;
        }
    }
}
