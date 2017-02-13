package bean;

public class BookingRequest
{
    // Have to have this in a special container class so we can pass it all as XML into our resource method

    private Itinerary itinerary;
    private PaymentInfo paymentInfo;

    public Itinerary getItinerary()
    {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary)
    {
        this.itinerary = itinerary;
    }

    public PaymentInfo getPaymentInfo()
    {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo)
    {
        this.paymentInfo = paymentInfo;
    }
}
