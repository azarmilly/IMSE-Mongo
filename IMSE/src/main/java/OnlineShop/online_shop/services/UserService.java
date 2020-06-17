package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.mongo.Orders;
import OnlineShop.online_shop.model.mongo.ShoppingList;
import OnlineShop.online_shop.model.mongo.Users;

import java.util.List;

public interface UserService {

    Users getUserById (int userId);
    List<Users> getAllUsers ();
    void addUser(Users user);
    void addOrder(int userId, Orders orders);
    ShoppingList addToShoppingList(Users user, int productId);
    //User getUserByOrder (Orders orders);


}
