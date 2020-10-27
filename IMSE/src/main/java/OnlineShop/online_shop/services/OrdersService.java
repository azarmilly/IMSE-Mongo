package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.mongo.Orders;
import OnlineShop.online_shop.model.mongo.ShoppingList;
import OnlineShop.online_shop.model.mongo.Users;

import java.util.List;

public interface OrdersService {
    Orders getOrderById (int ordersId);

    List<Orders> getAllOrders ();

    int addOrders(Users user, ShoppingList orders);

    List<Orders> getUserOrders(Users user, int daysBefore);
}
