package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Orders;

import java.util.List;

public interface OrdersService {
    Orders getOrderById (int ordersId);

    List<Orders> getAllOrders ();

    void addOrders (Orders orders);

}
