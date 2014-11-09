package plaani.service.boundary;

import plaani.service.control.InvitationService;
import plaani.service.entity.Invitation;
import plaani.service.entity.Terminplan;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("invitations")
public class InvitationResource {

    @EJB
    InvitationService invitationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmptyInvitation(
            @QueryParam("termin") String terminId,
            @Context UriInfo uriInfo) {
        Response.ResponseBuilder ok;
        if (terminId != null && !terminId.trim().isEmpty()) {
            Terminplan termin = new Terminplan();
            termin.setId(terminId);
            ok = Response.ok(invitationService.findForTermin(termin));
        } else {
            ok = Response.ok(new Invitation());
        }
        return ok
                .link(uriInfo.getRequestUri(), "self")
                .build();
    }

}
