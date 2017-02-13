package bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentInfo {

    private String cardHolder;
    private String creditCardNumber;

    @XmlElement
    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    @XmlElement
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public String toString()
    {
        return "PaymentInfo{" +
                "cardHolder='" + cardHolder + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                '}';
    }
}
