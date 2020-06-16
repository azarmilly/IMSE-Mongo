package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Orders;
import OnlineShop.online_shop.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Orders getOrderById(int ordersId) {
        return ordersRepository.findByOrderId(ordersId);
    }

    @Override
    public List<Orders> getAllOrders() {
        return (List<Orders>) ordersRepository.findAll();
    }

    @Override
    public void addOrders(Orders orders) {
        ordersRepository.save(orders);
    }
}
