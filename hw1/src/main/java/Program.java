import domain.Company;
import domain.EmploymentRecord;
import domain.Resume;
import domain.Transcript;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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
        File transcriptFile;
        File resumeFile;

        try
        {
            companyInfoFileInputStream = new FileInputStream(
                    new File(getClass().getClassLoader().getResource(companyInfoFileName).getFile())
            );

            employmentRecordFile = new File(
                    getClass().getClassLoader().getResource(employmentRecordFileName).getFile());

            transcriptFile = new File(
                    getClass().getClassLoader().getResource(transcriptFileName).getFile());

            resumeFile = new File(
                    getClass().getClassLoader().getResource(resumeFileName).getFile());
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

            //JAXB Parser - transcript
            JAXBContext jaxbTranscriptContext = JAXBContext.newInstance(Transcript.class);
            Unmarshaller jaxbTranscriptUnmarshaller = jaxbTranscriptContext.createUnmarshaller();
            Transcript transcript = (Transcript) jaxbTranscriptUnmarshaller.unmarshal(transcriptFile);

            //JAXB Parser - resume
            JAXBContext jaxbResumeContext = JAXBContext.newInstance(Resume.class);
            Unmarshaller jaxbResumeUnmarshaller = jaxbResumeContext.createUnmarshaller();
            Resume resume = (Resume) jaxbResumeUnmarshaller.unmarshal(resumeFile);
        }
        catch (IOException|SAXException|ParserConfigurationException|JAXBException ex)
        {
            System.err.println(ex);
            return;
        }
    }
}
