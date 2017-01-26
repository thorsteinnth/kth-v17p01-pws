import domain.EmploymentRecord;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class SAXHandler extends DefaultHandler {

    private Object tempObject;
    private ArrayList<Object> objects;
    private String tempValue;
    private String currentElement;

    public SAXHandler() {
        this.currentElement = "";
    }

    // Events

    public void startDocument() {
        System.out.println("SAXHandler starting parsing");
    }

    public void endDocument() {
        System.out.println("SAXHandler ending parsing");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (qName.equals("employmentrecord")) {
            // Start of the employment record xml
            this.objects = new ArrayList<>();
        }
        else if (qName.equals("employment")) {
            // Start of a new employment record
            this.tempObject = new EmploymentRecord();
        }

        this.currentElement = qName;
    }
    public void characters(char[] ch, int start, int length) {
        this.tempValue = new String(ch, start, length).trim();
    }
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

        if (this.tempObject != null && this.tempObject.getClass().equals(EmploymentRecord.class)) {

            if (qName.equals("employment")) {
                // End of employment record, add it to the list of employment records
                this.objects.add(this.tempObject);
            }

            EmploymentRecord tempER = (EmploymentRecord)this.tempObject;

            if (this.currentElement.equals("companyname")) {
                tempER.setCompanyName(this.tempValue);
            }
            else if (this.currentElement.equals("roledescription")) {
                tempER.setRoleDescription(this.tempValue);
            }
            else if (this.currentElement.equals("startdate")) {
                tempER.setStartDate(this.tempValue);
            }
            else if (this.currentElement.equals("enddate")) {
                tempER.setEndDate(this.tempValue);
            }
            else if (this.currentElement.equals("contactemail")) {
                tempER.setContactEmail(this.tempValue);
            }
            else if (this.currentElement.equals("cancontact")) {
                tempER.setCanContact(this.tempValue);
            }
        }

        this.currentElement = "";
    }

    public ArrayList<Object> getObjects() {
        return this.objects;
    }
}
