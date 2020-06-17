package OnlineShop.online_shop.repositories.mongo;

import OnlineShop.online_shop.model.mongo.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("OrdersMongoRepository")
public interface OrdersMongoRepository extends MongoRepository<Orders, Integer> {
    Orders findByOrderId (int ordersId) ;
}
