
package itinerary.testclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for flight complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="departure" type="{http://hw2.flightticketreservation/itinerary.service/itinerary}node" minOccurs="0"/>
 *         &lt;element name="destination" type="{http://hw2.flightticketreservation/itinerary.service/itinerary}node" minOccurs="0"/>
 *         &lt;element name="flightNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flight", propOrder = {
    "departure",
    "destination",
    "flightNumber"
})
public class Flight {

    protected Node departure;
    protected Node destination;
    protected String flightNumber;

    /**
     * Gets the value of the departure property.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getDeparture() {
        return departure;
    }

    /**
     * Sets the value of the departure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setDeparture(Node value) {
        this.departure = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setDestination(Node value) {
        this.destination = value;
    }

    /**
     * Gets the value of the flightNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the value of the flightNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }

}
