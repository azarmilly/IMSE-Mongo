package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.Orders;
import OnlineShop.online_shop.model.Users;
import OnlineShop.online_shop.repositories.OrdersRepository;
import OnlineShop.online_shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService2")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public Users getUserById(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<Users> getAllUsers() {
        return (List<Users>) userRepository.findAll();
    }

    @Override
    public void addUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void addOrder(int userId, Orders orders) {
        Users user = getUserById(userId);
        ordersRepository.save(orders);
        userRepository.save(user);

    }

//    @Override
//    public User getUserByOrder(Orders orders) {
//        return userRepository.findOrders(orders);
//    }

}
