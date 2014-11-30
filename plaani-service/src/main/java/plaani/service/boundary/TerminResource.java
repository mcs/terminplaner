package plaani.service.boundary;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import plaani.service.cdi.UriHelper;
import plaani.service.control.TerminService;
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

@Path("termin")
public class TerminResource {

    @EJB
    private TerminService terminService;

    @Inject
    private RepresentationFactory representationFactory;

    @Inject
    private UriHelper uriHelper;

    @Inject
    private Provider<User> user;

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Response getInfo(@Context UriInfo uriInfo) {
        Representation rep = representationFactory.newRepresentation(uriInfo.getRequestUri())
                .withLink("all", uriHelper.build(uriInfo, TerminResource.class, "termine"));

        return Response.ok(rep).build();
    }

    @OPTIONS
    @Produces(RepresentationFactory.HAL_JSON)
    public Response getOptions(@Context UriInfo uriInfo) {
        return getInfo(uriInfo);
    }


    @GET
    @Path("termine")
    @Produces(RepresentationFactory.HAL_JSON)
    public Response getTermine(
            @Context UriInfo uriInfo) {
        List<Terminplan> plaene = terminService.findForUser(user.get());
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder().path("{id}");
        Representation rep = representationFactory.newRepresentation(uriInfo.getRequestUri());
        for (Terminplan plan : plaene) {
            rep.withBeanBasedRepresentation("plan", uriBuilder.build(plan.getId()).toString(), plan);
        }

        return Response.ok(rep).build();
    }

    @POST
    @Path("termine")
    @Produces(RepresentationFactory.HAL_JSON)
    public Response createNewTerminplan(
            @Valid Terminplan termin,
            @Context UriInfo uriInfo) {

        if (termin == null) {
            termin = terminService.createNewTerminplan();
        } else {
            termin.setUser(user.get());
            terminService.save(termin);
        }
        URI location = uriInfo.getRequestUriBuilder().path(termin.getId()).build();
        Representation rep = representationFactory.newRepresentation(location)
                .withBean(termin);
        return Response
                .created(location)
                .entity(rep)
                .build();
    }

    @GET
    @Path("termine/{terminplanId}")
    @Produces(RepresentationFactory.HAL_JSON)
    public Response getTermine(
            @PathParam("terminplanId") String id,
            @Context UriInfo uriInfo) {
        Terminplan terminplan = terminService.findTerminplanById(id);
        Representation rep = representationFactory.newRepresentation(uriInfo.getRequestUri()).withBean(terminplan);
        return Response.ok(rep).build();
    }
}
