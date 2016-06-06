package auction.service;

import auction.dao.BidDAOJPAImpl;
import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class AuctionMgr  {

    private ItemDAO itemDAO;
    private BidDAOJPAImpl bidDAO;
    private EntityManager em;

    public AuctionMgr(){
        this.em = Persistence.createEntityManagerFactory("Auction").createEntityManager();
        itemDAO = new ItemDAOJPAImpl(em);
        bidDAO = new BidDAOJPAImpl(em);
    }
   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        em.getTransaction().begin();
        Item item = itemDAO.find(id);
        em.getTransaction().commit();
        return item;
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        em.getTransaction().begin();
        List<Item> items = null;
        items = itemDAO.findByDescription(description);
        em.getTransaction().commit();
        return (ArrayList<Item>)items;
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        Bid bid = item.newBid(buyer, amount);
        if (bid != null) {
            em.getTransaction().begin();
            this.bidDAO.create(bid);
            this.itemDAO.edit(item);
            em.getTransaction().commit();
        }
        return bid;
    }
}
