package auction.webservice;

import org.junit.After;
import org.junit.Test;
import webservicegen.*;

import java.util.List;

import static org.junit.Assert.*;

public class RegistrationWebTest {

    private static final RegistrationWSService registrationWSService = new RegistrationWSService();
    private static RegistrationWS registrationWS;

    private static User registerUser(String email) {
        registrationWS = registrationWSService.getRegistrationWSPort();
        return registrationWS.registerUser(email);
    }

    private static User getUser(String email) {
        registrationWS = registrationWSService.getRegistrationWSPort();
        return registrationWS.getUser(email);
    }

    private static List<User> getUsers() {
        registrationWS = registrationWSService.getRegistrationWSPort();
        return registrationWS.getUsers();
    }

    private static void clean() {
        registrationWS = registrationWSService.getRegistrationWSPort();
        registrationWS.clean();
    }

    @After
    public void cleanUp() {
        clean();
    }

    @Test
    public void registerUser() {
        User user1 = registerUser("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = registerUser("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = registerUser("xxx2@yyy2");

        //  Volledige objecten kunnen nooit same zijn omdat deze via de webservice aangemaakt worden en dus geen
        //  verwijzingen naar dezelfde plek in het gehuegen zijn.
        //assertSame(user2bis, user2);
        assertEquals(user2bis.getId(), user2.getId());

        //geen @ in het adres
        assertNull(registerUser("abc"));
    }

    @Test
    public void getUserTest() {
        User user1 = registerUser("xxx5@yyy5");
        User userGet = getUser("xxx5@yyy5");

        //  Volledige objecten kunnen nooit same zijn omdat deze via de webservice aangemaakt worden en dus geen
        //  verwijzingen naar dezelfde plek in het geheugen zijn.
        //assertSame(userGet, user1);
        assertEquals(userGet.getId(), user1.getId());

        assertNull(getUser("aaa4@bb5"));
        registerUser("abc");
        assertNull(getUser("abc"));
    }

    @Test
    public void getUsersTest() {
        List<User> users = getUsers();
        assertEquals(0, users.size());

        User user1 = registerUser("xxx8@yyy");
        users = getUsers();
        assertEquals(1, users.size());
        //  Volledige objecten kunnen nooit same zijn omdat deze via de webservice aangemaakt worden en dus geen
        //  verwijzingen naar dezelfde plek in het geheugen zijn.
        //assertSame(users.get(0), user1);
        assertEquals(users.get(0).getId(), user1.getId());


        User user2 = registerUser("xxx9@yyy");
        users = getUsers();
        assertEquals(2, users.size());

        registerUser("abc");
        //geen nieuwe user toegevoegd, dus gedrag hetzelfde als hiervoor
        users = getUsers();
        assertEquals(2, users.size());
    }
}
