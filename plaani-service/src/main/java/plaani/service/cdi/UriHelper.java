package plaani.service.cdi;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Creates a new URI to the passed-in REST resource class.
 */
public class UriHelper {

    public URI build(UriInfo uriInfo, Class<?> resourceClass, String... pathParams) {
        UriBuilder builder = uriInfo.getBaseUriBuilder().path(resourceClass);
        if (pathParams != null) {
            for (String path : pathParams) {
                builder = builder.path(path);
            }
        }
        return builder.build();
    }

    public String buildHref(UriInfo uriInfo, Class<?> resourceClass, String... pathParams) {
        return build(uriInfo, resourceClass, pathParams).toString();
    }
}
