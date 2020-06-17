package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.mongo.Orders;
import OnlineShop.online_shop.model.mongo.Product;
import OnlineShop.online_shop.model.mongo.ShoppingList;
import OnlineShop.online_shop.model.mongo.Users;
import OnlineShop.online_shop.repositories.mongo.OrdersMongoRepository;
import OnlineShop.online_shop.repositories.mongo.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("userService2")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserMongoRepository userRepository;

    @Autowired
    private OrdersMongoRepository ordersRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MongoSequenceGenerator mongoSequenceGenerator;

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

    @Override
    public ShoppingList addToShoppingList(Users user, int productId) {
        Product product = productService.getProductById(productId);

        ShoppingList activeShoppingList = getActiveShoppingList(user);

        ShoppingList shoppingList;
        if(activeShoppingList != null) {
            shoppingList = activeShoppingList;
            List<Product> products = shoppingList.getProducts();
            products.add(product);
            shoppingList.setProducts(products);
            shoppingList.setItemCount(shoppingList.getItemCount() + 1);
        }
        else {
            shoppingList = new ShoppingList();
            ArrayList<Product> products = new ArrayList<>();
            products.add(product);
            shoppingList.setProducts(products);
            shoppingList.setItemCount(1);
            shoppingList.setActive(true);
            shoppingList.setId(mongoSequenceGenerator.generateSequence(ShoppingList.SEQUENCE_NAME));
        }

        user.getShoppingList()
                .add(shoppingList);
        userRepository.save(user);
        return shoppingList;
    }

    @Override
    public ShoppingList getActiveShoppingList(Users user) {
        return user.getShoppingList()
                .parallelStream()
                .filter(ShoppingList::isActive)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deactivateShoppingLists(Users user) {
        user.getShoppingList()
                .parallelStream()
                .forEach(sp -> sp.setActive(false));
        userRepository.save(user);
    }
}
