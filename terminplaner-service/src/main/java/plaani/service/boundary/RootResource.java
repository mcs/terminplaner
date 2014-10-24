package plaani.service.boundary;

import plaani.service.entity.User;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/")
public class RootResource {

    @Inject
    private Provider<User> user;

    @GET
    public Response getInitialLinks(@Context UriInfo uriInfo) {
        User currentUser = user.get();
        URI termine = uriInfo.getBaseUriBuilder().path("termine").build();
        URI login = uriInfo.getBaseUriBuilder().path(currentUser == null ? "login" : "logout").build();
        return Response.ok()
                .link(termine, "termine")
                .link(login, currentUser == null ? "login" : "logout")
                .build();
    }
}
