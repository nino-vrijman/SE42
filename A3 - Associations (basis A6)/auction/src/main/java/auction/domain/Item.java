package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.findById", query = "select i from Item as i where i.id = :itemId"),
        @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description"),
        @NamedQuery(name = "Item.count", query = "select count(i) from Item as i")
})
public class Item implements Comparable {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    private User seller;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="description", column = @Column(name="c_description"))
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
        return false;
    }

    public int hashCode() {
        //TODO
        return 0;
    }
}
