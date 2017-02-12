package filters;

import bean.User;
import exceptions.UsernameOrPasswordIncorrectException;
import storage.UserStore;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Principal;

@Provider
public class AuthFilter implements ContainerRequestFilter
{
    // Based on:
    // https://simplapi.wordpress.com/2015/09/17/jersey-jax-rs-implements-a-http-basic-auth-decoder-for-2-x-branch/
    // https://simplapi.wordpress.com/2013/01/24/jersey-jax-rs-implements-a-http-basic-auth-decoder/

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath(true);

        // Allow creating users without authentication
        if (method.equals("POST") && (path.equals("users/")))
        {
            return;
        }

        // Get auth string from HTTP header
        String authHeader = requestContext.getHeaderString("authorization");

        if (authHeader == null)
        {
            // No authentication header, don't allow through
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        String[] usernameAndPassword = decodeAuthHeader(authHeader);

        if (usernameAndPassword == null || usernameAndPassword.length != 2)
        {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        User foundUser;

        try
        {
            foundUser = UserStore.getUserStore().login(usernameAndPassword[0], usernameAndPassword[1]);
            // User has made it through, is authorized
            // Add the username to the request context to identify the user in the REST service
            requestContext.setSecurityContext(getSecurityContextForUser(foundUser));
        }
        catch (UsernameOrPasswordIncorrectException ex)
        {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }

    /**
     * Returns a string array of the form [username, password]
     * */
    private static String[] decodeAuthHeader(String authHeader)
    {
        // Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
        authHeader = authHeader.replaceFirst("[B|b]asic ", "");

        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(authHeader);

        if (decodedBytes == null || decodedBytes.length == 0)
        {
            return null;
        }

        // Return string array of the form [username, password]
        return new String(decodedBytes).split(":", 2);
    }

    private SecurityContext getSecurityContextForUser(User user)
    {
        SecurityContext securityContext = new SecurityContext()
        {
            @Override
            public Principal getUserPrincipal()
            {
                return new Principal()
                {
                    @Override
                    public String getName()
                    {
                        return user.getUsername();
                    }
                };
            }

            @Override
            public boolean isUserInRole(String role)
            {
                return false;
            }

            @Override
            public boolean isSecure()
            {
                return false;
            }

            @Override
            public String getAuthenticationScheme()
            {
                return null;
            }
        };

        return securityContext;
    }
}
