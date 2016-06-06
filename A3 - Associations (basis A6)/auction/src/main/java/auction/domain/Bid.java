package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;
import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Bid.getAll", query = "select b from Bid as b"),
        @NamedQuery(name = "Bid.count", query = "select count(b) from Bid as b")
})
public class Bid {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "seconds", column = @Column(name = "time_seconds"))
    })
    private FontysTime time;

    private User buyer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency",
                    column = @Column(name = "mon_currency")),
            @AttributeOverride(name = "cents",
                    column = @Column(name = "mon_cents"))
    })
    private Money amount;

    public Bid() {}

    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
        this.amount = amount;

        this.time = new FontysTime();
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
}
