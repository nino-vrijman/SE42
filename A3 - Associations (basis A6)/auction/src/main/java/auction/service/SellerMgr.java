package auction.service;

import auction.dao.*;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgr {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Auction");
    private ItemDAOJPAImpl itemDAOJPA;
    private BidDAO bidDAO;
    private UserDAO userDAO;
    private EntityManager em;

    public SellerMgr(){
        this.em = this.emf.createEntityManager();
        this.itemDAOJPA = new ItemDAOJPAImpl(em);
        this.bidDAO = new BidDAOJPAImpl(em);
        this.userDAO = new UserDAOJPAImpl(em);
    }
    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        Item item = new Item(seller,cat,description);
        em.getTransaction().begin();
        itemDAOJPA.create(item);
        em.getTransaction().commit();
        return item;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        boolean returnValue = false;
        em.getTransaction().begin();
        Item foundItem = itemDAOJPA.find(item.getId());
        if(foundItem.getHighestBid() == null){
            itemDAOJPA.remove(foundItem);
            returnValue = true;
        }
        em.getTransaction().commit();
        return returnValue;
    }
}
