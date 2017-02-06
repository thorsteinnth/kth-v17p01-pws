package authorization.service;

import authorization.bean.User;
import authorization.exception.InvalidCredentials;
import shared.SharedData;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService(serviceName = "Authorization",
        portName = "AuthorizationPort",
        targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class AuthorizationService {

    private ArrayList<User> authorizedUsers;

    public AuthorizationService() {
        generateAuthorizedUsers();
    }

    @WebMethod
    public User authorizeUser(User user) throws InvalidCredentials {

        boolean isAuthorized = false;

        for (User authorizedUser : this.authorizedUsers) {
            if (authorizedUser.getUsername().equals(user.getUsername())) {
                if (authorizedUser.getPassword().equals(user.getPassword())) {
                    isAuthorized = true;
                    break;
                }
            }
        }

        if (!isAuthorized) {
            throw new InvalidCredentials();
        }

        user.setToken(SharedData.getAuthToken());
        return user;
    }

    private void generateAuthorizedUsers() {

        this.authorizedUsers = new ArrayList<User>();

        for (int counter = 1; counter < 11; counter++) {
            User user = new User();
            user.setUsername("user" + Integer.toString(counter));
            user.setPassword("pass" + Integer.toString(counter));
            this.authorizedUsers.add(user);
        }
    }
}
