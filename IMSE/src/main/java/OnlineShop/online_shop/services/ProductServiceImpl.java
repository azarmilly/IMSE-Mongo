package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.mongo.Product;
import OnlineShop.online_shop.repositories.ProductRepository;
import OnlineShop.online_shop.repositories.mongo.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMongoRepository productRepository;

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
