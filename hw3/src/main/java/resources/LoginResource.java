package resources;

import bean.User;
import storage.UserStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("/login")
public class LoginResource
{
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    public LoginResource()
    {}

    public LoginResource(UriInfo uriInfo, Request request)
    {
        this.uriInfo = uriInfo;
        this.request = request;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response login(@Context SecurityContext securityContext)
    {
        // NOTE:
        // Authentication is handled in the request filter. If the user gets in here his credentials have already
        // been verified, and a security context has been created for him.

        User user = UserStore.getUserStore().getUserByUsername(securityContext.getUserPrincipal().getName());
        return Response.ok(user).build();
    }
}
