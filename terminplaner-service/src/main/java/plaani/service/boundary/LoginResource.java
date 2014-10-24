package plaani.service.boundary;

import plaani.service.control.UserService;
import plaani.service.entity.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

@Path("login")
public class LoginResource {

    @Inject
    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> usermap,
                          @Context UriInfo uriInfo) {
        User user = userService.login(usermap.get("username"), usermap.get("password"));
        if (user != null) {
            return Response
                    .ok(user)
                    .link(uriInfo.getBaseUriBuilder().path("user/{id}").build(user.getId()), "self")
                    .build();
        }   else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Das war wohl nix!")
                    .link(uriInfo.getRequestUriBuilder().build(), "self")
                    .build();
        }
    }
}
