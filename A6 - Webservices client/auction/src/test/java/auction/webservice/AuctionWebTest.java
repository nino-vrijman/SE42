package auction.webservice;

import org.junit.Test;
import webservicegen.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AuctionWebTest {

    private static final AuctionWSService auctionWSService = new AuctionWSService();
    private static final RegistrationWSService registrationWSService = new RegistrationWSService();
    private static RegistrationWS registrationWS;
    private static AuctionWS auctionWS;

    private static User registerUser(String email) {
        registrationWS = registrationWSService.getRegistrationWSPort();
        return registrationWS.registerUser(email);
    }

    private static Category newCategory(String description) {
        auctionWS = auctionWSService.getAuctionWSPort();
        return auctionWS.newCategory(description);
    }

    private static Item offerItem(User seller, Category cat, String description) {
        auctionWS = auctionWSService.getAuctionWSPort();
        return auctionWS.offerItem(seller, cat, description);
    }

    private static Item getItem(Long id) {
        auctionWS = auctionWSService.getAuctionWSPort();
        return auctionWS.getItem(id);
    }

    private static List<Item> findItemByDescription(String description) {
        auctionWS = auctionWSService.getAuctionWSPort();
        return auctionWS.findItemByDescription(description);
    }

    private static Bid newBid(Item item, User buyer, Money amount) {
        auctionWS = auctionWSService.getAuctionWSPort();
        return auctionWS.newBid(item, buyer, amount);
    }

    private Money newMoney(long cents, String currency) {
        auctionWS = auctionWSService.getAuctionWSPort();
        return auctionWS.newMoney(cents, currency);
    }

    @Test
    public void getItem() {

        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = registerUser(email);
        Category cat = newCategory("cat2");
        Item item1 = offerItem(seller1, cat, omsch);
        Item item2 = getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = registerUser(email3);
        User seller4 = registerUser(email4);
        Category cat = newCategory("cat3");
        Item item1 = offerItem(seller3, cat, omsch);
        Item item2 = offerItem(seller4, cat, omsch);

        //  Cast error van Vector to ArrayList
        //  Oude code: ArrayList<Item> res = (ArrayList<Item>) auctionMgr.findItemByDescription(omsch2);
        List<Item> res = findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = findItemByDescription(omsch);
        assertEquals(2, res.size());

    }

    @Test
    public void newBid() {

        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = registerUser(email);
        User buyer = registerUser(emailb);
        User buyer2 = registerUser(emailb2);

        // eerste bod
        Category cat = newCategory("cat9");
        Item item1 = offerItem(seller, cat, omsch);
        Bid new1 = newBid(item1, buyer, newMoney(10, "eur"));
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        Bid new2 = newBid(item1, buyer2, newMoney(9, "eur"));
        //  Omdat item1 niet wordt ververst via de webservice (en dus geen highest bid heeft) is deze assert niet null
        //  als bid new2 aangemaakt word
        //assertNull(new2);
        assertNotNull(new2);

        //  Met onderstaande code werkt het wel
        item1 = getItem(item1.getId());
        new2 = newBid(item1, buyer2, newMoney(9, "eur"));
        assertNull(new2);

        // hoger bod
        Bid new3 = newBid(item1, buyer2, newMoney(11, "eur"));
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
}
