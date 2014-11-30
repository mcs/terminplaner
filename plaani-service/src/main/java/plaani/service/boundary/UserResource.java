package plaani.service.boundary;

import plaani.service.control.UserService;
import plaani.service.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("user")
public class UserResource {

    @EJB
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmptyUser(@Context UriInfo uriInfo) {

        return Response
                .ok(new User())
                .link(uriInfo.getRequestUriBuilder().build(), "self")
                .build();
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(
            @Context UriInfo uriInfo) {

        List<User> users = userService.findAll();

        return Response
                .ok(users)
                .link(uriInfo.getRequestUriBuilder().build(), "self")
                .build();
    }

    @GET
    @Path("users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(
            @PathParam("userId") String userId,
            @Context UriInfo uriInfo) {
        User user = userService.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .link(uriInfo.getRequestUriBuilder().build(), "self")
                    .build();
        } else {
            return Response.ok(user)
                    .link(uriInfo.getRequestUriBuilder().path(userId).build(), "self")
                    .build();
        }
    }
}
