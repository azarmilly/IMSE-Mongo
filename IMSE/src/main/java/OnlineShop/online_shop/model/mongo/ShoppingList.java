package OnlineShop.online_shop.model.mongo;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.*;
import java.util.List;


public class ShoppingList {
    @Transient
    public static final String SEQUENCE_NAME = "shopping_list_sequence";

    @Id
    private Integer id;

    private String name;

    private int itemCount;

    @DBRef
    private List<Product> products;

    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
