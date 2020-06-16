package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Manufacturer;
import OnlineShop.online_shop.model.Product;

import java.util.List;

public interface ManufacturerService {

    Manufacturer getManufacturerById (int userId);

    List<Manufacturer> getAllManufacturers ();

    void addManufacturer(Manufacturer manufacturer);

    void addProduct(int manId, Product product);

    //Manufacturer getProducts(Product product);
}
