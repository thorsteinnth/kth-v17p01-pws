package resources;

import bean.User;
import storage.UserStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

@Path("/users")
public class UserResource
{
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    public UserResource()
    {}

    public UserResource(UriInfo uriInfo, Request request)
    {
        this.uriInfo = uriInfo;
        this.request = request;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public ArrayList<User> getUsers()
    {
        return UserStore.getUserStore().getAllUsers();
    }
}
