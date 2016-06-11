package auction.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :email"),
        @NamedQuery(name = "User.count", query = "select count(u) from User as u")
})
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @OneToMany (mappedBy = "seller")
    private Set<Item> offeredItems;

    public User() {
    }

    public User(String email) {
        this.email = email;
        this.offeredItems = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public Iterator<Item> getOfferedItems() {
        return offeredItems.iterator();
    }

    public boolean addItem(Item item) {
        if (this.offeredItems.contains(item)) {
            return false;
        }
        this.offeredItems.add(item);
        return true;
    }

    public int numberOfOfferedItems () {
        return this.offeredItems.size();
    }
}
