package OnlineShop.online_shop.repositories;

import OnlineShop.online_shop.model.Orders;
import OnlineShop.online_shop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("ordersRepository")

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findByOrderId (int ordersId) ;

    List<Orders> findByDateAfterAndUser(Date after, Users user);
}
