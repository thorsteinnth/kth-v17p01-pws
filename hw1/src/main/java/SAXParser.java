import jdk.internal.org.xml.sax.Attributes;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;

public class SAXParser extends DefaultHandler {

    public SAXParser() {
    }

    public void startDocument() {
        System.out.println("***Start of Document***");
    }

    public void endDocument() {
        System.out.println("***End of Document***");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        System.out.print("<" + qName);
        int n = attributes.getLength();
        for (int i = 0; i < n; i += 1) {
            System.out.print(" " + attributes.getQName(i) + "='" + attributes.getValue(i) + "'");
        }
        System.out.println(">");
    }
    public void characters(char[] ch, int start, int length) {
        System.out.println(new String(ch, start, length).trim());
    }
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        System.out.println("</" + qName + ">");
    }
}
