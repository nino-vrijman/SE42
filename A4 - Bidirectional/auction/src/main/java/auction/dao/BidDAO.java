package auction.dao;

import auction.domain.Bid;

import java.util.List;

/**
 *
 * @author Nino Vrijman
 */
public interface BidDAO {
  
    
    /**
     *
     * @return number of bid instances
     */
    int count();

    /**
     * The bid is persisted. If a bid with the same id allready exists an EntityExistsException is thrown
     * @param bid
     */
    void create(Bid bid);

    /**
     * Merge the state of the given bid into persistant context. If the bid did not exist an IllegalArgumentException is thrown
     * @param item
     */
    void edit(Bid bid);

    /**
     *
     * @param id
     * @return the found entity instance or null if the entity does not exist
     */
    Bid find(Long id);

    /**
     *
     * @return bid of bid instances
     */
    List<Bid> findAll();

    /**
     * Remove the entity instance
     * @param bid - entity instance
     */
    void remove(Bid item);
    
}
