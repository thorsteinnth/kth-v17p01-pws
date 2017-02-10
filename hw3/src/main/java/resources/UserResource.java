package resources;

import bean.User;
import exceptions.UserNotFoundException;
import storage.UserStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

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
    public Response getUsers()
    {
        // Can't return list in response, have to wrap in GenericEntity
        GenericEntity<List<User>> genericEntityUserList =
                new GenericEntity<List<User>>(UserStore.getUserStore().getAllUsers()){};
        return Response.ok(genericEntityUserList).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public Response createUser(@FormParam("username") String username, @FormParam("password") String password)
    {
        User newUser = UserStore.getUserStore().createUser(username, password);
        return Response.ok(newUser).build();
    }

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") String id)
    {
        try
        {
            User user = UserStore.getUserStore().getUserWithId(Integer.valueOf(id));
            return Response.ok(user).build();
        }
        catch (UserNotFoundException ex)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }
}
