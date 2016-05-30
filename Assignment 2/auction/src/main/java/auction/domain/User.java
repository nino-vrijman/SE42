package auction.domain;

import javax.persistence.*;

@Entity @Table(name="USER")
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
