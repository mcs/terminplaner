package plaani.service.boundary;

import plaani.service.control.UserService;
import plaani.service.entity.User;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

@Path("logout")
public class LogoutResource {

    @Inject
    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(User user,
                           @Context UriInfo uriInfo) {
        userService.logout(user);
        return Response
                .ok(new User())
                .link(uriInfo.getBaseUriBuilder().build(), "self")
                .build();
    }

}
