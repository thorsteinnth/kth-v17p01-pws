
package jaxwstutorial.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_02-b08-fcs
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "OrderProcess", targetNamespace = "http://jawxs.ibm.tutorial/jaxws/orderprocess", wsdlLocation = "http://localhost:8080/OrderProcessWeb/orderprocess?wsdl")
public class OrderProcess
    extends Service
{

    private final static URL ORDERPROCESS_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/OrderProcessWeb/orderprocess?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ORDERPROCESS_WSDL_LOCATION = url;
    }

    public OrderProcess(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OrderProcess() {
        super(ORDERPROCESS_WSDL_LOCATION, new QName("http://jawxs.ibm.tutorial/jaxws/orderprocess", "OrderProcess"));
    }

    /**
     * 
     * @return
     *     returns jaxwstutorial.OrderProcessService
     */
    @WebEndpoint(name = "OrderProcessPort")
    public OrderProcessService getOrderProcessPort() {
        return (OrderProcessService)super.getPort(new QName("http://jawxs.ibm.tutorial/jaxws/orderprocess", "OrderProcessPort"), OrderProcessService.class);
    }

}
