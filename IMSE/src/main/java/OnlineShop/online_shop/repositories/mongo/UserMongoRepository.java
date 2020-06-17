package OnlineShop.online_shop.repositories.mongo;

import OnlineShop.online_shop.model.mongo.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("userMongoRepository")
public interface UserMongoRepository extends MongoRepository<Users, Integer> {

    Users findById(int userId);

    //User findOrders(Orders orders);

    Users findOneByUsername(String username);
}
