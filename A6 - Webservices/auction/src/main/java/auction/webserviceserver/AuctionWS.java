package auction.webserviceserver;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import nl.fontys.util.Money;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nino on 11-6-2016.
 */
@WebService
public class AuctionWS {

    private AuctionMgr auctionMgr;
    private SellerMgr sellerMgr;

    public AuctionWS() {
        this.auctionMgr = new AuctionMgr();
        this.sellerMgr = new SellerMgr();
    }

    public Item getItem(Long id) {
        return this.auctionMgr.getItem(id);
    }

    public List<Item> findItemByDescription(String description) {
        return this.auctionMgr.findItemByDescription(description);
    }

    public Bid newBid(Item item, User buyer, Money amount) {
        return this.auctionMgr.newBid(item, buyer, amount);
    }

    public Item offerItem(User seller, Category cat, String description) {
        return this.sellerMgr.offerItem(seller, cat, description);
    }

    public boolean revokeItem(Item item) {
        return this.sellerMgr.revokeItem(item);
    }

    public Category newCategory(String description) {
        return this.sellerMgr.newCategory(description);
    }

    public Money newMoney(long cents, String currency) {
        return this.sellerMgr.newMoney(cents, currency);
    }
}
