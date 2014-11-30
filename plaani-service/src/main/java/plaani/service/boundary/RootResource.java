package plaani.service.boundary;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import plaani.service.cdi.UriHelper;
import plaani.service.entity.User;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class RootResource {

    @Inject
    private UriHelper uriHelper;

    @Inject
    private RepresentationFactory representationFactory;

    @Inject
    private Provider<User> user;

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Response getInitialLinks(@Context UriInfo uriInfo) {
        Representation rep = representationFactory.newRepresentation(uriInfo.getRequestUri());
        if (user.get() == null) {
            rep = rep.withLink("login", uriHelper.build(uriInfo, LoginResource.class));
        } else {
            rep = rep.withLink("termine", uriHelper.build(uriInfo, TerminResource.class));
            rep = rep.withLink("logout", uriHelper.build(uriInfo, LogoutResource.class));
        }

        return Response.ok(rep).build();
    }
}
