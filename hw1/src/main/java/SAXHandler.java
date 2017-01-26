import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

    private String currentElement;
    public SAXHandler() {
        this.currentElement = "";
    }

    public void startDocument() {
        System.out.println("SAXHandler starting parsing");
    }

    public void endDocument() {
        System.out.println("SAXHandler ending parsing");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        this.currentElement = qName;
    }
    public void characters(char[] ch, int start, int length) {
        //System.out.println(new String(ch, start, length).trim());

        if(this.currentElement.equals("ssn")) {
            System.out.println("We got the ssn from employment records, it is: " + new String(ch, start, length).trim());
        }
    }
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        //System.out.println("</" + qName + ">");
        this.currentElement = "";
    }
}
