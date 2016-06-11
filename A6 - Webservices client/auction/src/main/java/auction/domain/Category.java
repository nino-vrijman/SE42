package auction.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
