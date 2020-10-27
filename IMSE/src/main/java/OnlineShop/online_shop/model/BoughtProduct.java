package OnlineShop.online_shop.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class

public class BoughtProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer boughtProductId;

    private int productId;

    private int orderId;

    public BoughtProduct() {}

    public Integer getBoughtProductId() {
        return boughtProductId;
    }

    public void setBoughtProductId(Integer boughtProductId) {
        this.boughtProductId = boughtProductId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}