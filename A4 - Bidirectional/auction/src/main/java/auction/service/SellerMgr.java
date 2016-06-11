package auction.service;

import auction.dao.*;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SellerMgr {

    private ItemDAOJPAImpl itemDAO;
    private BidDAO bidDAO;
    private UserDAO userDAO;
    private EntityManager em;

    public SellerMgr(){
        this.em = Persistence.createEntityManagerFactory("Auction").createEntityManager();
        this.itemDAO = new ItemDAOJPAImpl(em);
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
        if (userDAO.findByEmail(seller.getEmail()) == null)
            userDAO.create(seller);
        itemDAO.create(item);
        //  Onderstaande regel toegevoegd in Assignment 4
        seller.addItem(item);
        em.getTransaction().commit();
        return item;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        if (item.getHighestBid() != null) {
            return false;
        } else {
            em.getTransaction().begin();
            System.out.println("RevokeItem: Transaction begun");
            this.itemDAO.remove(item);
            em.getTransaction().commit();
            return true;
        }
    }
}
