package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.mongo.Orders;
import OnlineShop.online_shop.model.mongo.Product;
import OnlineShop.online_shop.model.mongo.ShoppingList;
import OnlineShop.online_shop.model.mongo.Users;
import OnlineShop.online_shop.repositories.mongo.OrdersMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersMongoRepository ordersRepository;
    @Autowired
    MongoSequenceGenerator mongoSequenceGenerator;

    @Override
    public Orders getOrderById(int ordersId) {
        return ordersRepository.findById(ordersId);
    }

    @Override
    public List<Orders> getAllOrders() {
        return (List<Orders>) ordersRepository.findAll();
    }

    @Override
    public int addOrders(Users user, ShoppingList shoppingList) {
        Orders order = new Orders();
        order.setUser(user);
        order.setProducts(shoppingList.getProducts());
        order.setDate(new Date());
        order.setId(mongoSequenceGenerator.generateSequence(Orders.SEQUENCE_NAME));
        order.setPrice(shoppingList.getProducts()
                        .parallelStream()
                        .mapToDouble(Product::getPrice)
                        .sum());
        ordersRepository.save(order);
        return order.getId();
    }

    @Override
    public List<Orders> getUserOrders(Users user, int daysBefore) {
        return ordersRepository.findByDateAfterAndUser(new Date(
                System.currentTimeMillis() - ((long) daysBefore * 24 * 60 * 60 * 1000)), user);
    }
}
