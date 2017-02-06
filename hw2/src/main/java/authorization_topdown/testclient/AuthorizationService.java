
package authorization_topdown.testclient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AuthorizationService", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AuthorizationService {


    /**
     * 
     * @param arg0
     * @return
     *     returns authorization_topdown.testclient.User
     * @throws InvalidCredentialsException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "authorizeUser", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/", className = "authorization_topdown.testclient.AuthorizeUser")
    @ResponseWrapper(localName = "authorizeUserResponse", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/", className = "authorization_topdown.testclient.AuthorizeUserResponse")
    @Action(input = "http://hw2.flightticketreservation/authorization.service/authorization/AuthorizationService/authorizeUserRequest", output = "http://hw2.flightticketreservation/authorization.service/authorization/AuthorizationService/authorizeUserResponse", fault = {
        @FaultAction(className = InvalidCredentialsException.class, value = "http://hw2.flightticketreservation/authorization.service/authorization/AuthorizationService/authorizeUser/Fault/InvalidCredentials_Exception")
    })
    public User authorizeUser(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0)
        throws InvalidCredentialsException
    ;

}