package OnlineShop.online_shop.model;


import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Orders {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer orderId;

    private double price;

    private String status;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private String address;

    @ManyToOne
    private Users user;


    @ManyToMany
    @JoinTable(
            name = "bought_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
