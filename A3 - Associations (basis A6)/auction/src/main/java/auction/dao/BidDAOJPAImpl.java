package auction.dao;

import auction.domain.Bid;
import auction.domain.Item;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author Nino Vrijman
 */
public class BidDAOJPAImpl implements BidDAO {

    private final EntityManager em;
    
    public BidDAOJPAImpl(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public int count() {
        Query q = em.createNamedQuery("Bid.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();    }

    @Override
    public void create(Bid bid) {
        em.persist(bid);
    }

    @Override
    public void edit(Bid bid) {
        em.merge(bid);
    }

    @Override
    public Bid find(Long id) {
        return (Bid) em.find(Bid.class, id);
    }

    @Override
    public List<Bid> findAll() {
        Query q = em.createNamedQuery("Bid.getAll", Bid.class);
        return (List<Bid>) q.getResultList();
    }

    @Override
    public void remove(Bid bid) {
        em.remove(bid);
    }
    
}
