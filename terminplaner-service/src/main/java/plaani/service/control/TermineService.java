package plaani.service.control;

import plaani.service.entity.Terminplan;
import plaani.service.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class TermineService {

    @Inject
    private EntityManager em;

    @Inject
    private Provider<User> user;

    public Terminplan findTerminplanById(String id) {
        return em.find(Terminplan.class, id);
    }

    public Terminplan createNewTerminplan() {
        Terminplan plan = new Terminplan();
        plan.setUser(user.get());
        em.persist(plan);
        return plan;
    }

    public void save(Terminplan termin) {
        termin.setUser(this.user.get());
        if (termin.getId() == null) {
            em.persist(termin);
        } else {
            em.merge(termin);
        }
    }

    public List<Terminplan> findForUser(User user) {
        return em.createNamedQuery("Terminplan.findByUser", Terminplan.class)
                .setParameter("user", user)
                .getResultList();
    }
}
