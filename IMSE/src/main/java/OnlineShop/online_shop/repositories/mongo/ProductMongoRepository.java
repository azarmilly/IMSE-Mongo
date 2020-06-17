package OnlineShop.online_shop.repositories.mongo;

import OnlineShop.online_shop.model.mongo.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("ProductMongoRepository")
public interface ProductMongoRepository extends MongoRepository<Product, Integer> {
    Product findByProductId (int productId);
}
