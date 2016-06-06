package auction.service;

import java.util.*;

import auction.dao.UserDAOJPAImpl;
import auction.domain.User;
import auction.dao.UserDAOCollectionImpl;
import auction.dao.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistrationMgr {
    /*
    private UserDAO userDAO;

    public RegistrationMgr() {
        userDAO = new UserDAOCollectionImpl();
    }
    */

    private UserDAO userDAO;
    private EntityManager em;

    public RegistrationMgr() {
        this.em = Persistence.createEntityManagerFactory("Auction").createEntityManager();
        this.userDAO = new UserDAOJPAImpl(em);
    }

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        if (!email.contains("@")) {
            return null;
        }

        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        User user = userDAO.findByEmail(email);

        em.getTransaction().commit();

        if (user != null) {
            return user;
        }

        user = new User(email);

        userDAO.create(user);

        //  TODO dezelfde verplaatsen naar boven if user != null
        //em.getTransaction().commit();

        return user;
    }
    /*
    public User registerUser(String email) {
        if (!email.contains("@")) {
            return null;
        }
        User user = userDAO.findByEmail(email);
        if (user != null) {
            return user;
        }
        user = new User(email);
        userDAO.create(user);
        return user;
    }
    */

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        return userDAO.findByEmail(email);
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        return userDAO.findAll();
    }
}
