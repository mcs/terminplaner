package plaani.service.boundary;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import plaani.service.cdi.UriHelper;
import plaani.service.control.UserService;
import plaani.service.entity.User;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.Map;

@Path("login")
public class LoginResource {

    private static class LoginUser {
        public String username, password, auth;
    }

    @Inject
    private UserService userService;

    @Inject
    private RepresentationFactory representationFactory;

    @Inject
    private UriHelper uriHelper;

    @OPTIONS
    @Produces("application/hal+json")
    public Response info(@Context UriInfo uriInfo) {
        Representation rep = representationFactory.newRepresentation(uriInfo.getRequestUri());
        return Response.ok(rep).build();
    }

    @GET
    @Produces("application/hal+json")
    public Response infoGet(@Context UriInfo uriInfo) {
        return info(uriInfo);
    }

    @POST
    @Produces("application/hal+json")
    public Response login(@NotNull @Valid LoginUser loginUser,
                          @Context UriInfo uriInfo) {
        User user = null;
        if (loginUser.username != null && !loginUser.username.isEmpty() ) {
            // manueller Login
            user = userService.login(loginUser.username, loginUser.password);
        } else if (loginUser.auth != null) {
            // Login über E-Mail-Link
            user = userService.findByAuthKey(loginUser.auth);
        }

        if (user != null) {
            Representation rep = representationFactory.newRepresentation(
                    uriHelper.build(uriInfo, UserResource.class, "users", user.getId()))
                    .withBean(user);
            return Response
                    .ok(rep)
                    .build();
        } else {
            Representation rep = representationFactory.newRepresentation(uriInfo.getRequestUri())
                    .withProperty("error", "Das war wohl nix! Benutzer-Id unbekannt.");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(rep)
                    .build();
        }
    }

}
