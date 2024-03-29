package resources;

import bean.User;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import storage.UserStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;
import java.util.List;

@Path("/users")
public class UsersResource
{
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    public UsersResource()
    {}

    public UsersResource(UriInfo uriInfo, Request request)
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
        try
        {
            User newUser = UserStore.getUserStore().createUser(username, password);
            return Response.ok(newUser).build();
        }
        catch (UsernameAlreadyExistsException ex)
        {
            // TODO What HTTP code to return here?
            return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    public Response getUser(@Context SecurityContext securityContext, @PathParam("id") String id)
    {
        // Shouldn't have the ID as a parameter here ... should just get the user from the security context
        // The ID here makes sense though if we would have an admin that is able to manage other users

        try
        {
            User user = UserStore.getUserStore().getUserWithId(Integer.valueOf(id));

            // Only allow a user to get himself
            if (!securityContext.getUserPrincipal().getName().equals(user.getUsername()))
                return Response.status(Response.Status.UNAUTHORIZED).build();

            return Response.ok(user).build();
        }
        catch (UserNotFoundException ex)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateUser(@Context SecurityContext securityContext, JAXBElement<User> jaxbUser)
    {
        User userToUpdate = jaxbUser.getValue();

        try
        {
            // Only allow a user to update himself
            User existingUser = UserStore.getUserStore().getUserWithId(userToUpdate.getId());
            if (!securityContext.getUserPrincipal().getName().equals(existingUser.getUsername()))
                return Response.status(Response.Status.UNAUTHORIZED).build();

            User updatedUser = UserStore.getUserStore().updateUser(userToUpdate);
            return Response.ok(updatedUser).build();
        }
        catch (UserNotFoundException ex)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
        catch (UsernameAlreadyExistsException ex)
        {
            return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@Context SecurityContext securityContext, @PathParam("id") String id)
    {
        // Shouldn't have the ID as a parameter here ... should just get the user from the security context
        // The ID here makes sense though if we would have an admin that is able to manage other users

        try
        {
            // Only allow a user to delete himself
            User existingUser = UserStore.getUserStore().getUserWithId(Integer.valueOf(id));
            if (!securityContext.getUserPrincipal().getName().equals(existingUser.getUsername()))
                return Response.status(Response.Status.UNAUTHORIZED).build();

            boolean success = UserStore.getUserStore().deleteUser(Integer.valueOf(id));

            return Response.ok(success).build();
        }
        catch (UserNotFoundException ex)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }
}
