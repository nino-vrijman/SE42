
import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Kevin on 25-4-2016.
 */
public class AssignmentOne {
    EntityManager em;
    DatabaseCleaner dbCleaner;
    EntityManagerFactory factory;
    @Before
    public void setup()  {
        factory = Persistence.createEntityManagerFactory("bankPU");
        em = factory.createEntityManager();
        dbCleaner = new DatabaseCleaner(em);
    }
    @After
    public void cleanUp() throws SQLException {
        dbCleaner.clean();
    }

    //Vraag 1 : Hoe werken persist en commit in samenhang met de database.
    @Test
    public void opgave1() throws SQLException {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //De transactie is not niet gecommit. Daarom is het account nog niet bekend voor de manager
       assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //De transactie is doorgevoerd dus het account is nu bekend
        assertTrue(account.getId() > 0L);
        dbCleaner.clean();
    }

    //Vraag 2 : Rollback
    @Test
    public void opgave2() throws SQLException {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
        // Dit is de code om te kijken of ook daadwerkelijk alle accounts zijn verwijdert.
        AccountDAOJPAImpl accountDAOJPA = new AccountDAOJPAImpl(em);
        assertEquals(accountDAOJPA.count(), 0);
        dbCleaner.clean();
    }

    //Vraag 3 : Flushen maar
    @Test
    public void opgave3() throws SQLException {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        System.out.println(account.getId());
        em.getTransaction().begin();
        em.persist(account);
        System.out.println(account.getId());
        //TODO: verklaar en pas eventueel aan
        assertNotEquals(expected, account.getId());
        em.flush();
        System.out.println(account.getId());
        //TODO: verklaar en pas eventueel aan
        assertEquals(expected, account.getId());
        em.getTransaction().commit();
    }

    //Opdracht 4 ; Veranderingen na de persist
    @Test
    public void opgave4() throws SQLException{
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        //De balance wordt geset binnen een transactie en de transactie is gecommit. De juiste waarde is opgeslagen en opgehaald.
        Long acId = account.getId();
        //account = null; Deze regel is overbodig.
        EntityManager em2 = factory.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);
        //Er wordt een tweede manager aangemaakt. Op het moment dat hij zoekt naar het account vind hij deze en haalt hij hier het juiste saldo van op.
        assertEquals(expectedBalance, found.getBalance());
        //todo vraag : Wat is de meerwaarde van de tweede transactie en hoeft hij niet gesloten te worden?
    }

    /**
     * In de vorige opdracht verwijzen de objecten account en found naar dezelfde rij in de database.
     * Pas een van de objecten aan, persisteer naar de database.
     * Refresh vervolgens het andere object om de veranderde state uit de database te halen.
     * Test met asserties dat dit gelukt is.
     */
    //todo deze opgave is not niet gemaakt
    @Test
    public void opgave5() throws  SQLException{
        //Opdracht 5 : Refresh

        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        Long acId = account.getId();
        EntityManager em2 = factory.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);
        assertEquals(expectedBalance, found.getBalance());

    }
}
