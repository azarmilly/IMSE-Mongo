package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById (int productId);

    List<Product> getAllProducts ();

    void addProduct (Product product);
}
