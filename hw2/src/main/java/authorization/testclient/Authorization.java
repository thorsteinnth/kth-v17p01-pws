
package authorization.testclient;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Authorization", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization", wsdlLocation = "http://localhost:8080/AuthorizationService/authorization?wsdl")
public class Authorization
    extends Service
{

    private final static URL AUTHORIZATION_WSDL_LOCATION;
    private final static WebServiceException AUTHORIZATION_EXCEPTION;
    private final static QName AUTHORIZATION_QNAME = new QName("http://hw2.flightticketreservation/authorization.service/authorization", "Authorization");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/AuthorizationService/authorization?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUTHORIZATION_WSDL_LOCATION = url;
        AUTHORIZATION_EXCEPTION = e;
    }

    public Authorization() {
        super(__getWsdlLocation(), AUTHORIZATION_QNAME);
    }

    public Authorization(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTHORIZATION_QNAME, features);
    }

    public Authorization(URL wsdlLocation) {
        super(wsdlLocation, AUTHORIZATION_QNAME);
    }

    public Authorization(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUTHORIZATION_QNAME, features);
    }

    public Authorization(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Authorization(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AuthorizationService
     */
    @WebEndpoint(name = "AuthorizationPort")
    public AuthorizationService getAuthorizationPort() {
        return super.getPort(new QName("http://hw2.flightticketreservation/authorization.service/authorization", "AuthorizationPort"), AuthorizationService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuthorizationService
     */
    @WebEndpoint(name = "AuthorizationPort")
    public AuthorizationService getAuthorizationPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://hw2.flightticketreservation/authorization.service/authorization", "AuthorizationPort"), AuthorizationService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTHORIZATION_EXCEPTION!= null) {
            throw AUTHORIZATION_EXCEPTION;
        }
        return AUTHORIZATION_WSDL_LOCATION;
    }

}