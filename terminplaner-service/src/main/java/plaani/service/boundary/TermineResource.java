package plaani.service.boundary;

import plaani.service.control.TermineService;
import plaani.service.entity.Terminplan;
import plaani.service.entity.User;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@Path("termine")
public class TermineResource {

    @EJB
    private TermineService termineService;

    @Inject
    private Provider<User> user;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(
            @Context UriInfo uriInfo) {
        List<Terminplan> plaene = termineService.findForUser(user.get());
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder().path("{id}");
        for (Terminplan plan : plaene) {
            plan.addLink("self", uriBuilder.build(plan.getId()).toString());
        }

        return Response
                .ok(plaene)
                .link(uriInfo.getRequestUri(), "self")
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewTerminplan(
            @Valid Terminplan termin,
            @Context UriInfo uriInfo) {

        if (termin == null) {
            termin = termineService.createNewTerminplan();
        } else {
            termin.setUser(user.get());
            termineService.save(termin);
        }
        URI location = uriInfo.getRequestUriBuilder().path(termin.getId()).build();
        return Response
                .created(location)
                .location(location)
                .link(location, "self")
                .build();
    }

    @GET
    @Path("{terminplanId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTermine(
            @PathParam("terminplanId") String id,
            @Context UriInfo uriInfo) {
        Terminplan terminplan = termineService.findTerminplanById(id);
        return Response
                .ok(terminplan)
                .link(uriInfo.getRequestUri(), "self")
                .build();
    }
}
