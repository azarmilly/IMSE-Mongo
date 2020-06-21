package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Orders;
import OnlineShop.online_shop.model.Product;
import OnlineShop.online_shop.model.ShoppingList;
import OnlineShop.online_shop.model.Users;
import OnlineShop.online_shop.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Orders getOrderById(int ordersId) {
        return ordersRepository.findById(ordersId).get();
    }

    @Override
    public List<Orders> getAllOrders() {
        return (List<Orders>) ordersRepository.findAll();
    }

    @Override
    public int addOrder(Orders order) {
        ordersRepository.save(order);
        return order.getOrderId();
    }

    @Override
    public int addOrders(Users user, ShoppingList shoppingList) {
        Orders order = new Orders();
        order.setUser(user);
        order.setProducts(shoppingList.getProducts());
        order.setDate(new Date());
        order.setPrice(shoppingList.getProducts()
                        .parallelStream()
                        .mapToDouble(Product::getPrice)
                        .sum());
        ordersRepository.save(order);
        return order.getOrderId();
    }

    @Override
    public List<Orders> getUserOrders(Users user, int daysBefore) {
        return ordersRepository.findByDateAfterAndUser(new Date(
                System.currentTimeMillis() - ((long) daysBefore * 24 * 60 * 60 * 1000)), user);
    }
}
