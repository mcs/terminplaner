package terminfinder.service.boundary;

import terminfinder.service.control.TermineService;
import terminfinder.service.entity.Terminplan;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("termine")
public class TermineResource {

    @EJB
    private TermineService termineService;

    @GET
    public Response getInfo(
            @Context UriInfo uriInfo) {
        return Response
                .ok("Hier bist du schon nicht schlecht. Mit Plan-UUID angehaengt wird es noch besser ;-)")
                .link(uriInfo.getRequestUri(), "self")
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewTerminplan(
            @Context UriInfo uriInfo) {
        Terminplan plan = termineService.createNewTerminplan();
        uriInfo.getRequestUriBuilder().path("").build();
        URI location = uriInfo.getRequestUriBuilder().path(plan.getUuid()).build();
        return Response
                .ok(plan)
                .location(location)
                .link(location, "self")
                .build();
    }

    @GET
    @Path("{terminplanUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTermine(
            @PathParam("terminplanUuid") String uuid,
            @Context UriInfo uriInfo) {
        Terminplan terminplan = termineService.findTerminplanByUuid(uuid);
        return Response
                .ok(terminplan)
                .link(uriInfo.getRequestUri(), "self")
                .build();
    }
}
