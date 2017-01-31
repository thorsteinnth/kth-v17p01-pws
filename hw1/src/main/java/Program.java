import domain.*;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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

        InputStream companyInfoInputStream;
        InputStream employmentRecordInputStream;
        InputStream transcriptInputStream;
        InputStream resumeInputStream;

        companyInfoInputStream = getClass().getClassLoader().getResourceAsStream(companyInfoFileName);
        employmentRecordInputStream = getClass().getClassLoader().getResourceAsStream(employmentRecordFileName);
        transcriptInputStream = getClass().getClassLoader().getResourceAsStream(transcriptFileName);
        resumeInputStream = getClass().getClassLoader().getResourceAsStream(resumeFileName);

        try
        {
            //region DOM parser - company info

            DOMParser domParser = new DOMParser();
            Companies companies = new Companies();
            companies.setCompanyinfo(domParser.parseCompanyInfoXml(companyInfoInputStream));
            System.out.println("Parsed companies: " + companies.toString());

            //endregion

            //region SAX Parser - employment record

            SAXHandler saxHandler = new SAXHandler();
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            saxParser.parse(employmentRecordInputStream, saxHandler);

            String saxHandlerIdFromXml = saxHandler.getId();
            ArrayList<Object> saxHandlerObjects = saxHandler.getObjects();
            List<Employment> er = saxHandlerObjects.stream()
                    .map(object -> (Employment)object)
                    .collect(Collectors.toList());

            Employmentrecord employmentRecords = new Employmentrecord();
            employmentRecords.setSsn(saxHandlerIdFromXml);
            employmentRecords.setEmployment(er);
            System.out.println("Parsed employment records: " + employmentRecords.toString());

            //endregion

            //region JAXB Parser - transcript

            JAXBContext jaxbTranscriptContext = JAXBContext.newInstance(Transcript.class);
            Unmarshaller jaxbTranscriptUnmarshaller = jaxbTranscriptContext.createUnmarshaller();
            Transcript transcript = (Transcript) jaxbTranscriptUnmarshaller.unmarshal(transcriptInputStream);
            System.out.println("Parsed transcript: " + transcript.toString());

            //endregion

            //region JAXB Parser - resume

            JAXBContext jaxbResumeContext = JAXBContext.newInstance(Resume.class);
            Unmarshaller jaxbResumeUnmarshaller = jaxbResumeContext.createUnmarshaller();
            Resume resume = (Resume) jaxbResumeUnmarshaller.unmarshal(resumeInputStream);
            System.out.println("Parsed resume: " + resume.toString());

            //endregion

            //region JAXB - Convert all objects back to XML
            File outputCompanyInfoXML = new File("output_xml/OutputCompanyInfo.xml");
            File outputEmploymentRecordXML  = new File("output_xml/OutputEmploymentRecord.xml");
            File outputResumeXML  = new File("output_xml/OutputResume.xml");
            File outputTranscriptXML = new File("output_xml/OutputTranscript.xml");

            // - CompanyInfo
            JAXBContext jaxbCompanyContext = JAXBContext.newInstance(Companies.class);
            Marshaller jaxbCompanyMarshaller = jaxbCompanyContext.createMarshaller();
            jaxbCompanyMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbCompanyMarshaller.marshal(companies, outputCompanyInfoXML);

            // - EmploymentRecord
            JAXBContext jaxbEmploymentRecordsContext = JAXBContext.newInstance(Employmentrecord.class);
            Marshaller jaxbEmploymentRecordsMarshaller = jaxbEmploymentRecordsContext.createMarshaller();
            jaxbEmploymentRecordsMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbEmploymentRecordsMarshaller.marshal(employmentRecords, outputEmploymentRecordXML);

            // - Resume
            Marshaller jaxbResumeMarshaller = jaxbResumeContext.createMarshaller();
            jaxbResumeMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbResumeMarshaller.marshal(resume, outputResumeXML);

            // - Transcript
            Marshaller jaxbMarshaller = jaxbTranscriptContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(transcript, outputTranscriptXML);


            // XSLT
            // http://stackoverflow.com/questions/4604497/xslt-processing-with-java
            // http://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer

            // Generate applicant profile from original XML documents

            InputStream applicantProfileFromOriginalXmlXslInputStream
                    = getClass().getClassLoader().getResourceAsStream("xml/ApplicantProfileFromOriginalXml.xsl");
            StreamSource xsl = new StreamSource(applicantProfileFromOriginalXmlXslInputStream);

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
                            new File("output_xml/ApplicantProfileFromOriginalXml.xml")
                    )
            );

            // Generate applicant profile from output XML documents

            InputStream applicantProfileFromOutputXmlXslInputStream
                    = getClass().getClassLoader().getResourceAsStream("xml/ApplicantProfileFromOutputXml.xsl");
            xsl = new StreamSource(applicantProfileFromOutputXmlXslInputStream);

            factory = TransformerFactory.newInstance();
            transformer = factory.newTransformer(xsl);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // Set indentation of output document
            // TODO Indentation not working
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(
                    new StreamSource(),
                    new StreamResult(
                            new File("output_xml/ApplicantProfileFromOutputXml.xml")
                    )
            );

            //endregion
        }
        catch (IOException|SAXException|ParserConfigurationException|JAXBException|TransformerException ex)
        {
            System.err.println(ex);
        }
    }
}
