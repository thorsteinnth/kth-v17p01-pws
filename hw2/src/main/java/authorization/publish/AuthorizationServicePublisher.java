package authorization.publish;

import javax.xml.ws.Endpoint;

import authorization.service.AuthorizationService;

public class AuthorizationServicePublisher
{
    public static void main(String[] args)
    {
        final String WSUrl = "http://localhost:12501/AuthorizationService/authorization";
        Endpoint.publish(WSUrl, new AuthorizationService());
        System.out.println("The authorization service is published at: " + WSUrl);
    }
}
