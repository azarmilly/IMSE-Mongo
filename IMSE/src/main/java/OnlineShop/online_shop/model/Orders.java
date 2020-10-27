package OnlineShop.online_shop.model;


import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Orders {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer orderId;

    private double price;

    private String status;

    @Basic
    @Indexed(expireAfterSeconds = 30 * 24 * 60 * 60)
    @Temporal(TemporalType.DATE)
    private Date date;

    private String address;

    @ManyToOne
    private Users user;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
