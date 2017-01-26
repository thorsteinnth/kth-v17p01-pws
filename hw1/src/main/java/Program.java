import domain.Company;
import domain.EmploymentRecord;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Program
{
    public static void main(String[] args)
    {
        Program program = new Program();
        program.parseAll();
    }

    public void parseAll()
    {
        String companyInfoFileName = "xml/CompanyInfo.xml";
        String employmentRecordFileName = "xml/EmploymentRecord.xml";
        String resumeFileName = "xml/Resume.xml";
        String transcriptFileName = "xml/Transcript.xml";

        FileInputStream companyInfoFileInputStream;
        File employmentRecordFile;

        try
        {
            companyInfoFileInputStream = new FileInputStream(
                    new File(getClass().getClassLoader().getResource(companyInfoFileName).getFile())
            );

            employmentRecordFile = new File(
                    getClass().getClassLoader().getResource(employmentRecordFileName).getFile());
        }
        catch (FileNotFoundException ex)
        {
            System.err.println(ex);
            return;
        }

        try
        {
            // DOM parser - company info
            DOMParser domParser = new DOMParser();
            ArrayList<Company> companies = domParser.parseCompanyInfoXml(companyInfoFileInputStream);
            System.out.println("Parsed companies: " + companies.toString());

            // SAX Parser - employment record
            SAXHandler saxHandler = new SAXHandler();
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            saxParser.parse(employmentRecordFile, saxHandler);

            ArrayList<Object> saxHandlerObjects = saxHandler.getObjects();

            List<EmploymentRecord> employmentRecords = saxHandlerObjects.stream()
                    .map(object -> (EmploymentRecord)object)
                    .collect(Collectors.toList());

            // XSLT
            // http://stackoverflow.com/questions/4604497/xslt-processing-with-java
            // http://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer

            File applicantProfileFromAllXslFile = new File(
                    getClass().getClassLoader().getResource("xml/ApplicantProfileFromAll.xsl").getFile()
            );
            StreamSource xsl = new StreamSource(applicantProfileFromAllXslFile);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xsl);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // Set indentation of output document
            // TODO Indentation not working
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(
                    new StreamSource(),
                    new StreamResult(
                            new File("output_xml/ApplicantProfileFromAll.xml")
                    )
            );
        }
        catch (IOException|SAXException|ParserConfigurationException|TransformerException ex)
        {
            System.err.println(ex);
        }
    }
}
