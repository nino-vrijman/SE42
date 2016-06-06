package auction.dao;

import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by Nino on 1-6-2016.
 */
public class ItemDAOJPAImpl implements ItemDAO {

    private final EntityManager em;

    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        em.persist(item);
    }

    @Override
    public void edit(Item item) {
        em.merge(item);
    }

    @Override
    public Item find(Long id) {
        Query q = em.createNamedQuery("Item.findById", Item.class);
        q.setParameter("itemId", id);
        try {
            //  Try block omdat als er geen result gevonden wordt er een exception wordt opgegooid
            return (Item) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Item> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Item.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        Query q = em.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);
        try {
            //  Try block omdat als er geen result gevonden wordt er een exception wordt opgegooid
            return (List<Item>)q.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void remove(Item item) {
        em.remove(em.merge(item));
    }
}
