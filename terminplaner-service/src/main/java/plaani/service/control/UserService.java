package plaani.service.control;

import plaani.service.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

@Stateless
public class UserService {

    @Inject
    private EntityManager em;


    public User findByUsernameAndPassword(String username, String password) {
        try {
            User user = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            if (user.isPasswordCorrect(password)) {
                return user;
            }
        } catch (NoResultException e) {
            // ignore and proceed usual workflow
        } catch (RuntimeException e) {
            throw e;
        }
        return null;

    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public boolean isAuthTokenValid(String token) {
        return findByAuthKey(token) != null;
    }

    public User login(String username, String password) {
        User user = findByUsernameAndPassword(username, password);
        if (user != null) {
            user.setAuthKey(UUID.randomUUID().toString());
        }
        return user;
    }

    public User findById(String userId) {
        return em.find(User.class, userId);
    }

    public User findByAuthKey(String token) {
        try {
            return em
                    .createNamedQuery("User.findByAuthToken", User.class)
                    .setParameter("authToken", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void logout(User user) {
        User persisted = em.find(User.class, user.getId());
        persisted.setAuthKey(null);
    }
}
