
package client.generated.ticket;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getPriceAndAvailabilityOfItinerariesForDateResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getPriceAndAvailabilityOfItinerariesForDateResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://hw2.flightticketreservation/ticket.service/ticket}bookableItinerary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPriceAndAvailabilityOfItinerariesForDateResponse", propOrder = {
    "_return"
})
public class GetPriceAndAvailabilityOfItinerariesForDateResponse {

    @XmlElement(name = "return")
    protected List<BookableItinerary> _return;

    /**
     * Gets the value of the return property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the return property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookableItinerary }
     * 
     * 
     */
    public List<BookableItinerary> getReturn() {
        if (_return == null) {
            _return = new ArrayList<BookableItinerary>();
        }
        return this._return;
    }

}