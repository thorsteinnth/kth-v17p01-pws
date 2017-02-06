
package authorization_topdown.testclient;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "InvalidCredentials", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/")
public class InvalidCredentialsException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private InvalidCredentials faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public InvalidCredentialsException(String message, InvalidCredentials faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public InvalidCredentialsException(String message, InvalidCredentials faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: authorization_topdown.testclient.InvalidCredentials
     */
    public InvalidCredentials getFaultInfo() {
        return faultInfo;
    }

}