
package ticket.testclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bookableItinerary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bookableItinerary">
 *   &lt;complexContent>
 *     &lt;extension base="{http://hw2.flightticketreservation/ticket.service/ticket}itinerary">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfAvailableTickets" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bookableItinerary", propOrder = {
    "date",
    "numberOfAvailableTickets",
    "totalPrice"
})
public class BookableItinerary
    extends Itinerary
{

    protected String date;
    protected int numberOfAvailableTickets;
    protected int totalPrice;

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the numberOfAvailableTickets property.
     * 
     */
    public int getNumberOfAvailableTickets() {
        return numberOfAvailableTickets;
    }

    /**
     * Sets the value of the numberOfAvailableTickets property.
     * 
     */
    public void setNumberOfAvailableTickets(int value) {
        this.numberOfAvailableTickets = value;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     */
    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     */
    public void setTotalPrice(int value) {
        this.totalPrice = value;
    }

}
