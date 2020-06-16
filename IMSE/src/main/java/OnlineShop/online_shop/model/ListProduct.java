package OnlineShop.online_shop.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class

public class ListProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer listProductId;

    private int listId;

    private int productId;

    public ListProduct() {}

    public Integer getListProductId() {
        return listProductId;
    }

    public void setListProductId(Integer listProductId) {
        this.listProductId = listProductId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
