package plaani.service.config;

import plaani.service.cdi.UserProducer;
import plaani.service.control.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
@PreMatching
public class PlaaniRESTRequestFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(PlaaniRESTRequestFilter.class.getName());

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {
        String authToken = requestCtx.getHeaderString(PlaaniHTTPHeaderNames.AUTH_TOKEN);
        UserProducer.set(userService.findByAuthKey(authToken));

        String path = requestCtx.getUriInfo().getPath();
        log.info("Filtering request path: " + path);

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if ("OPTIONS".equals(requestCtx.getRequest().getMethod())) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());

            return;
        }

        // For any pther methods besides login, the authToken must be verified
        if (!"/".equals(path) && !path.startsWith("/login")) {
            // if it isn't valid, just kick them out.
            if (!userService.isAuthTokenValid(authToken)) {
                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }

    }
}
