
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

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

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
    }

    //Vraag 3 : Flushen maar
    //todo wtf deze shit is gaar en werkt niet
    @Test
    public void opgave3() throws SQLException {
        Long expected = -100L;
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        account.setId(expected);
        assertNotEquals(expected, account.getId());
        em.flush();
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
        //Opdracht 5 : Refresh
     * In de vorige opdracht verwijzen de objecten account en found naar dezelfde rij in de database.
     * Pas een van de objecten aan, persisteer naar de database.
     * Refresh vervolgens het andere object om de veranderde state uit de database te halen.
     * Test met asserties dat dit gelukt is.
     */
    //todo comments toevoegen
    @Test
    public void opgave5() throws  SQLException{
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
        Long newExpected = 350L;
        found.setBalance(newExpected);
        em2.getTransaction().commit();
        em.refresh(account);
        assertEquals(newExpected, account.getBalance());
    }

    //Opdracht 6 : Merge
    //todo deze opdracht is nog niet gedaan
    @Test
    public void opgave6() throws SQLException{
        Account acc = new Account(1L);
        Account acc2;
        Account acc9;

// scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.


// scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a+balance2a);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        // HINT: gebruik acccountDAO.findByAccountNr


// scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        acc2 = em.merge(acc);
        assertTrue(em.contains(acc)); // todo verklaar
        assertTrue(em.contains(acc2)); // todo verklaar
        assertEquals(acc,acc2);  //todo verklaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        // scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650l);
        assertEquals((Long)650L,account2.getBalance());  //todo verklaar
        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);
        assertSame(account,account2);  //todo verklaar
        assertTrue(em.contains(account2));  //todo verklaar
        assertFalse(em.contains(tweedeAccountObject));  //todo verklaar
        tweedeAccountObject.setBalance(850l);
        assertEquals((Long)650L,account.getBalance());  //todo verklaar
        assertEquals((Long)650L,account2.getBalance());  //todo verklaar
        em.getTransaction().commit();
        em.close();
    }

    //Opdracht 7
    @Test
    public void opdracht7() throws SQLException{
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account.

        // scenario 1
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        // scenario 2
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2); //Dit verandert naar NotSame
        //Bij dit scenario wordt de entitymanager gecleared. Alle gemanagde entities worden losgelaten.
        //Als hij nu weer opzoek gaat naar de objecten in de database zal hij hier nieuwe objecten van maken ipv een referentie naar de oude hebben.
    }

    //Opdracht 8
    @Test
    public void opdracht8() throws  SQLException{
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.
        em.remove(acc1);
        assertEquals(id, acc1.getId()); //Hij returned hier nog true omdat de entiteit van account nog niet ververst is
        Account accFound = em.find(Account.class, id); // Hij zoekt nu naar het account en kan deze niet vinden omdat hij verwijdert is
        assertNull(accFound);
    }

    //Opdracht 9
    /**
     * Opgave 1 heb je uitgevoerd met @GeneratedValue(strategy = GenerationType.IDENTITY)
     Voer dezelfde opdracht nu uit met GenerationType SEQUENCE en TABLE.
     Verklaar zowel de verschillen in testresultaat als verschillen van de database structuur.s
     */
    //todo deze opdracht is nog niet gemaakt
    @Test
    public void opdracht9() throws SQLException{

    }
}
