package auction.domain;

import auction.domain.Item;
import auction.domain.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2016-06-11T18:33:52")
@StaticMetamodel(Bid.class)
public class Bid_ { 

    public static volatile SingularAttribute<Bid, Item> item;
    public static volatile SingularAttribute<Bid, Money> amount;
    public static volatile SingularAttribute<Bid, Long> id;
    public static volatile SingularAttribute<Bid, FontysTime> time;
    public static volatile SingularAttribute<Bid, User> buyer;

}