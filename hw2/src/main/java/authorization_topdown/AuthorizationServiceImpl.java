package authorization_topdown;

import authorization_topdown.generated.*;
import shared.SharedData;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.ArrayList;

// NOTE: Just copied the annotations from the (generated) interface

@WebService(name = "AuthorizationService", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/")
@XmlSeeAlso({
        ObjectFactory.class
})
public class AuthorizationServiceImpl implements AuthorizationService
{
    private ArrayList<User> authorizedUsers;

    public AuthorizationServiceImpl()
    {
        generateAuthorizedUsers();
    }

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "authorizeUser", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/", className = "authorization_topdown.generated.AuthorizeUser")
    @ResponseWrapper(localName = "authorizeUserResponse", targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization/", className = "authorization_topdown.generated.AuthorizeUserResponse")
    @Action(input = "http://hw2.flightticketreservation/authorization.service/authorization/AuthorizationService/authorizeUserRequest", output = "http://hw2.flightticketreservation/authorization.service/authorization/AuthorizationService/authorizeUserResponse")
    @Override
    public User authorizeUser(User arg0) throws InvalidCredentials_Exception
    {
        boolean isAuthorized = false;

        for (User authorizedUser : this.authorizedUsers) {
            if (authorizedUser.getUsername().equals(arg0.getUsername())) {
                if (authorizedUser.getPassword().equals(arg0.getPassword())) {
                    isAuthorized = true;
                    break;
                }
            }
        }

        if (!isAuthorized)
        {
            InvalidCredentials exception = new InvalidCredentials();
            exception.setMessage("Invalid credentials: " + arg0.getUsername() + " " + arg0.getPassword());
            throw new InvalidCredentials_Exception("Invalid credentials", exception);
        }

        arg0.setToken(SharedData.getAuthToken());
        return arg0;
    }

    private void generateAuthorizedUsers() {

        this.authorizedUsers = new ArrayList<>();

        for (int counter = 1; counter < 11; counter++) {
            User user = new User();
            user.setUsername("user" + Integer.toString(counter));
            user.setPassword("pass" + Integer.toString(counter));
            this.authorizedUsers.add(user);
        }
    }
}
