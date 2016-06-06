package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.findById", query = "select i from Item as i where i.id = :itemId"),
        @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description"),
        @NamedQuery(name = "Item.count", query = "select count(i) from Item as i")
})
public class Item implements Comparable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    private User seller;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "desc",
                    column = @Column(name = "c_category"))
    })
    private Category category;

    private String description;

    @OneToOne (cascade = {CascadeType.ALL})
    private Bid highest;

    public Item() {}

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    public int compareTo(Object arg0) {
        //TODO
        return -1;
    }

    public boolean equals(Object o) {
        //TODO
        Item other = (Item) o;
        if (other == null) {
            return false;
        }
        if (!Objects.equals(other.getId(), this.getId())) {
            return false;
        }
        if (!Objects.equals(other.getSeller(), this.getSeller())) {
            return false;
        }
        if (!Objects.equals(other.getHighestBid(), this.getHighestBid())) {
            return false;
        }
        if (!Objects.equals(other.getDescription(), this.getDescription())) {
            return false;
        }
        if (!Objects.equals(other.getCategory().getDesc(), this.getCategory().getDesc())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        //TODO
        return getClass().hashCode();
    }
}
