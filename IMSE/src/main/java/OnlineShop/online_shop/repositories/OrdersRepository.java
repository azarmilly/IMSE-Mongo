package OnlineShop.online_shop.repositories;

import OnlineShop.online_shop.model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("ordersRepository")

public interface OrdersRepository extends CrudRepository<Orders, Integer> {
    Orders findByOrderId (int ordersId) ;
}
