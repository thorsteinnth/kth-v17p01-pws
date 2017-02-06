
package authorization_topdown.testclient;

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
@WebServiceClient(name = "AuthorizationServiceImplService", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/", wsdlLocation = "http://localhost:8080/AuthorizationService/authorization?wsdl")
public class AuthorizationServiceImplService
    extends Service
{

    private final static URL AUTHORIZATIONSERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException AUTHORIZATIONSERVICEIMPLSERVICE_EXCEPTION;
    private final static QName AUTHORIZATIONSERVICEIMPLSERVICE_QNAME = new QName("http://hw2.flightticketreservation/authorization.service/authorization/", "AuthorizationServiceImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/AuthorizationService/authorization?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUTHORIZATIONSERVICEIMPLSERVICE_WSDL_LOCATION = url;
        AUTHORIZATIONSERVICEIMPLSERVICE_EXCEPTION = e;
    }

    public AuthorizationServiceImplService() {
        super(__getWsdlLocation(), AUTHORIZATIONSERVICEIMPLSERVICE_QNAME);
    }

    public AuthorizationServiceImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTHORIZATIONSERVICEIMPLSERVICE_QNAME, features);
    }

    public AuthorizationServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, AUTHORIZATIONSERVICEIMPLSERVICE_QNAME);
    }

    public AuthorizationServiceImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUTHORIZATIONSERVICEIMPLSERVICE_QNAME, features);
    }

    public AuthorizationServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuthorizationServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AuthorizationService
     */
    @WebEndpoint(name = "AuthorizationServicePort")
    public AuthorizationService getAuthorizationServicePort() {
        return super.getPort(new QName("http://hw2.flightticketreservation/authorization.service/authorization/", "AuthorizationServicePort"), AuthorizationService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuthorizationService
     */
    @WebEndpoint(name = "AuthorizationServicePort")
    public AuthorizationService getAuthorizationServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://hw2.flightticketreservation/authorization.service/authorization/", "AuthorizationServicePort"), AuthorizationService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTHORIZATIONSERVICEIMPLSERVICE_EXCEPTION!= null) {
            throw AUTHORIZATIONSERVICEIMPLSERVICE_EXCEPTION;
        }
        return AUTHORIZATIONSERVICEIMPLSERVICE_WSDL_LOCATION;
    }

}
