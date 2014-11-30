package plaani.service.boundary;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import plaani.service.control.InvitationService;
import plaani.service.entity.Invitation;
import plaani.service.entity.Terminplan;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("invitation")
public class InvitationResource {

    @EJB
    InvitationService invitationService;

    @Inject
    RepresentationFactory representationFactory;

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Response getInvitation(
            @QueryParam("termin") String terminId,
            @Context UriInfo uriInfo) {
        Representation rep = representationFactory.newRepresentation(uriInfo.getRequestUri());
        if (terminId != null && !terminId.trim().isEmpty()) {
            Terminplan termin = new Terminplan();
            termin.setId(terminId);
            List<Invitation> invitations = invitationService.findForTermin(termin);
            for (Invitation each : invitations) {
                rep.withBeanBasedRepresentation("self", uriInfo.getRequestUriBuilder().path(each.getId()).build().toString(), each);
            }
        } else {
            rep.withBean(new Invitation());
        }
        return Response.ok(rep).build();
    }

}
