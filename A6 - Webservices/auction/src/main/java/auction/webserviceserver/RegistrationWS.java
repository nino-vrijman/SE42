package auction.webserviceserver;

import auction.domain.User;
import auction.service.RegistrationMgr;
import auction.util.DatabaseCleaner;

import javax.jws.WebService;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nino on 11-6-2016.
 */
@WebService
public class RegistrationWS {

    private RegistrationMgr registrationMgr;

    public RegistrationWS() {
        this.registrationMgr = new RegistrationMgr();
    }

    public User registerUser(String email) {
        return this.registrationMgr.registerUser(email);
    }

    public User getUser(String email) {
        return this.registrationMgr.getUser(email);
    }

    public List<User> getUsers() {
        return this.registrationMgr.getUsers();
    }

    /**
     * Ter vervanging van de database cleaner zoals die in de andere unit tests ook worden gebruikt
     */
    public void clean() {
        DatabaseCleaner dbCleaner = new DatabaseCleaner(Persistence.createEntityManagerFactory("Auction").createEntityManager());
        try {
            dbCleaner.clean();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
