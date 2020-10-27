package OnlineShop.online_shop.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Manufacturer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String sector;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "products_manufacturers",
//            joinColumns = {@JoinColumn(name = "product_id", nullable = false, updatable = false)},
//            inverseJoinColumns = {@JoinColumn(name = "manufacturer_id", nullable = false, updatable = false)}
//    )
    //private Set<Product> products = new HashSet<>();

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

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
