
package authorization_topdown.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the authorization_topdown.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AuthorizeUser_QNAME = new QName("http://hw2.flightticketreservation/authorization.service/authorization/", "authorizeUser");
    private final static QName _AuthorizeUserResponse_QNAME = new QName("http://hw2.flightticketreservation/authorization.service/authorization/", "authorizeUserResponse");
    private final static QName _InvalidCredentials_QNAME = new QName("http://hw2.flightticketreservation/authorization.service/authorization/", "InvalidCredentials");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: authorization_topdown.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AuthorizeUser }
     * 
     */
    public AuthorizeUser createAuthorizeUser() {
        return new AuthorizeUser();
    }

    /**
     * Create an instance of {@link InvalidCredentials }
     * 
     */
    public InvalidCredentials createInvalidCredentials() {
        return new InvalidCredentials();
    }

    /**
     * Create an instance of {@link AuthorizeUserResponse }
     * 
     */
    public AuthorizeUserResponse createAuthorizeUserResponse() {
        return new AuthorizeUserResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorizeUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/authorization.service/authorization/", name = "authorizeUser")
    public JAXBElement<AuthorizeUser> createAuthorizeUser(AuthorizeUser value) {
        return new JAXBElement<AuthorizeUser>(_AuthorizeUser_QNAME, AuthorizeUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorizeUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/authorization.service/authorization/", name = "authorizeUserResponse")
    public JAXBElement<AuthorizeUserResponse> createAuthorizeUserResponse(AuthorizeUserResponse value) {
        return new JAXBElement<AuthorizeUserResponse>(_AuthorizeUserResponse_QNAME, AuthorizeUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidCredentials }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/authorization.service/authorization/", name = "InvalidCredentials")
    public JAXBElement<InvalidCredentials> createInvalidCredentials(InvalidCredentials value) {
        return new JAXBElement<InvalidCredentials>(_InvalidCredentials_QNAME, InvalidCredentials.class, null, value);
    }

}
