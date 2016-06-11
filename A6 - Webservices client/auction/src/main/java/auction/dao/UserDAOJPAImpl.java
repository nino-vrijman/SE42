package auction.dao;

import auction.domain.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO {

    //  private HashMap<String, User> users;

    private final EntityManager em;

    /*
    public UserDAOJPAImpl() {
        users = new HashMap<String, User>();
    }
    */

    public UserDAOJPAImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    /*
    @Override
    public int count() {
        return this.users.size();
    }
     */
    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    /*
    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        users.put(user.getEmail(), user);
    }
    */
    @Override
    public void create(User user) {
        em.persist(user);
    }

    /*
    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        users.put(user.getEmail(), user);
    }
    */
    @Override
    public void edit(User user) {
        em.merge(user);
    }

    /*
    @Override
    public List<User> findAll() {
        return new ArrayList<User>(users.values());
    }
    */

    @Override
    public List<User> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    /*
    @Override
    public User findByEmail(String email) {
        return users.get(email);
    }
    */
    @Override
    public User findByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        try {
            //  Try block omdat als er geen result gevonden wordt er een exception wordt opgegooid
            return (User) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    /*
    @Override
    public void remove(User user) {
        users.remove(user.getEmail());
    }
    */
    @Override
    public void remove(User user) {
        em.remove(em.merge(user));
    }
}
