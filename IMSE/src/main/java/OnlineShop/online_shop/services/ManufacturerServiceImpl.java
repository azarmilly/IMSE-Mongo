package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Manufacturer;
import OnlineShop.online_shop.model.Product;
import OnlineShop.online_shop.repositories.ManufacturerRepository;
import OnlineShop.online_shop.repositories.OrdersRepository;
import OnlineShop.online_shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("manufacturerService")
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Manufacturer getManufacturerById(int manId) {
        return manufacturerRepository.findManufacturerById(manId);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return (List<Manufacturer>) manufacturerRepository.findAll();
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void addProduct(int manId, Product product) {
        Manufacturer manufacturer = getManufacturerById(manId);
        productRepository.save(product);
        manufacturerRepository.save(manufacturer);
    }

//    @Override
//    public Manufacturer getProducts(Product product) {
//        return manufacturerRepository.findProducts(product);
//    }


}
