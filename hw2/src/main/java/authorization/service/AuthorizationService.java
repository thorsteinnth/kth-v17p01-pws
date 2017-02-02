package authorization.service;

import authorization.bean.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(serviceName = "Authorization",
        portName = "AuthorizationPort",
        targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class AuthorizationService {

    @WebMethod
    public User authorizeUser(User user) {

        // TODO : check if is valid user

        user.setToken("secure token");

        return user;
    }
}
