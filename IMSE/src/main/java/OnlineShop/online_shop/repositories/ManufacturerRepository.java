package OnlineShop.online_shop.repositories;

import OnlineShop.online_shop.model.Manufacturer;
import OnlineShop.online_shop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("manufacturerRepository")

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Integer> {
    Manufacturer findManufacturerById (int manId);

    //Manufacturer findProducts(Product product);
}
