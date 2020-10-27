package OnlineShop.online_shop.model;

import org.hibernate.annotations.Type;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class

public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Integer productId;

    private double price;

    @Type(type = "text")
    private String description;

    private boolean availability;

    private double rating;

    //@ManyToMany(fetch = FetchType.LAZY, mappedBy = "manufacturer")
    //private List<Manufacturer> posts = new ArrayList<>();



    @ManyToOne
    private Manufacturer manufacturer;

    public Integer getId() {
        return productId;
    }

    public void setId(Integer id) {
        this.productId = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
