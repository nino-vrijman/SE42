package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

public class Bid {

    private FontysTime time;
    private User buyer;
    private Money amount;

    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
        this.amount = amount;
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
