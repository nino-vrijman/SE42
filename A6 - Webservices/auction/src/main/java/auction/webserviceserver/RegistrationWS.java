package auction.webserviceserver;

import auction.domain.User;
import auction.service.RegistrationMgr;

import javax.jws.WebService;
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
}
