package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Orders;
import OnlineShop.online_shop.model.ShoppingList;
import OnlineShop.online_shop.model.Users;

import java.util.List;

public interface OrdersService {
    Orders getOrderById (int ordersId);

    List<Orders> getAllOrders ();

    int addOrders(Users user, ShoppingList orders);

    List<Orders> getUserOrders(Users user, int daysBefore);
}
