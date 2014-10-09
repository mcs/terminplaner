package terminfinder.service.control;

import terminfinder.service.entity.Terminplan;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TermineService {

    @PersistenceContext
    private EntityManager em;

    public Terminplan findTerminplanByUuid(String uuid) {
        return em.createNamedQuery("Teminplan.findByUuid", Terminplan.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }

    public Terminplan createNewTerminplan() {
        Terminplan plan = new Terminplan();
        em.persist(plan);
        return plan;
    }
}
