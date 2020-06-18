package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Orders;
import OnlineShop.online_shop.model.ShoppingList;
import OnlineShop.online_shop.model.Users;

import java.util.List;

public interface UserService {

    Users getUserById (int userId);
    List<Users> getAllUsers ();
    void addUser(Users user);
    void addOrder(int userId, Orders orders);
    ShoppingList addToShoppingList(Users user, int productId);
    ShoppingList getActiveShoppingList(Users user);
    void deactivateShoppingLists(Users user);
    //User getUserByOrder (Orders orders);


}
