package OnlineShop.online_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import OnlineShop.online_shop.model.Users;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("userRepository")
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findById(int userId);

    //User findOrders(Orders orders);

    Users findOneByUsername(String username);
}
