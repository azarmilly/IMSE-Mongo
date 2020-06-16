package OnlineShop.online_shop.repositories;

import OnlineShop.online_shop.model.ShoppingList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository("shoppingListRepository")

public interface ShoppingListRepository extends CrudRepository<ShoppingList, Integer> {

}
