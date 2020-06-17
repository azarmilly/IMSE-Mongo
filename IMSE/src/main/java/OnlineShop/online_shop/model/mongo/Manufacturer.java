package OnlineShop.online_shop.model.mongo;

import javax.persistence.*;


public class Manufacturer {
    @Transient
    public static final String SEQUENCE_NAME = "manufacturer_sequence";

    @Id
    private Integer id;

    private String name;

    private String sector;

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
