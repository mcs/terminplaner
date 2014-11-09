package plaani.service.control;

import plaani.service.entity.Invitation;
import plaani.service.entity.Terminplan;
import plaani.service.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class InvitationService {

    @Inject
    private EntityManager em;

    @Inject
    private Provider<User> user;

    public Invitation findInvitatonById(String id) {
        return em.find(Invitation.class, id);
    }

    public void save(Invitation invitation) {
        if (invitation.getId() == null) {
            em.persist(invitation);
        } else {
            em.merge(invitation);
        }
    }

    public List<Invitation> findForTermin(Terminplan termin) {
        return em.createNamedQuery("Invitation.findByTermin", Invitation.class)
                .setParameter("termin", termin)
                .getResultList();
    }
}
