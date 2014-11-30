package plaani.service.boundary;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import plaani.service.cdi.UriHelper;
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
import java.net.URI;
import java.util.Map;

@Path("logout")
public class LogoutResource {

    @Inject
    private UserService userService;

    @Inject
    private RepresentationFactory representationFactory;

    @Inject
    private UriHelper uriHelper;

    @POST
    @Produces(RepresentationFactory.HAL_JSON)
    public Response logout(User user,
                           @Context UriInfo uriInfo) {
        userService.logout(user);
        URI rootUri = uriHelper.build(uriInfo, RootResource.class);
        Representation rep = representationFactory.newRepresentation(rootUri);
        return Response
                .ok(rep)
                .build();
    }

}
