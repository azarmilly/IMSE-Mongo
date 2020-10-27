package OnlineShop.online_shop.repositories.mongo;

import OnlineShop.online_shop.model.mongo.Orders;
import OnlineShop.online_shop.model.mongo.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("OrdersMongoRepository")
public interface OrdersMongoRepository extends MongoRepository<Orders, Integer> {
    Orders findById (int ordersId) ;
    List<Orders> findByDateAfterAndUser(Date after, Users user);
}
