package auction.domain;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Category implements Serializable {

    private String desc;

    public Category() {
        desc = "undefined";
    }

    public Category(String description) {
        this.desc = description;
    }

    public String getDesc() {
        return desc;
    }
}
