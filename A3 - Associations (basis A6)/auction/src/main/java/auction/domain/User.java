package auction.domain;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :email"),
        @NamedQuery(name = "User.count", query = "select count(u) from User as u")
})
public class User {

    @Id @GeneratedValue
    private Long id;
    private String email;

    public User() {
    }

    public User(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }
}
