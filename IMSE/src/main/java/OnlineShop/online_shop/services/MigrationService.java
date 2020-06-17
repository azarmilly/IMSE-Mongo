package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.*;
import OnlineShop.online_shop.repositories.*;
import OnlineShop.online_shop.repositories.mongo.OrdersMongoRepository;
import OnlineShop.online_shop.repositories.mongo.ProductMongoRepository;
import OnlineShop.online_shop.repositories.mongo.UserMongoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MigrationService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final ListProductRepository listProductRepository;
    private final BoughtProductRepository boughtProductRepository;

    private final UserMongoRepository userMongoRepository;
    private final ProductMongoRepository productMongoRepository;
    private final OrdersMongoRepository ordersMongoRepository;
    private final MongoSequenceGenerator mongoSequenceGenerator;

    public MigrationService(UserRepository userRepository, ManufacturerRepository manufacturerRepository,
                            ProductRepository productRepository, OrdersRepository ordersRepository,
                            ShoppingListRepository shoppingListRepository, ListProductRepository listProductRepository,
                            BoughtProductRepository boughtProductRepository, UserMongoRepository userMongoRepository,
                            ProductMongoRepository productMongoRepository, OrdersMongoRepository ordersMongoRepository, MongoSequenceGenerator mongoSequenceGenerator) {
        this.userRepository = userRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
        this.ordersRepository = ordersRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.listProductRepository = listProductRepository;
        this.boughtProductRepository = boughtProductRepository;
        this.userMongoRepository = userMongoRepository;
        this.productMongoRepository = productMongoRepository;
        this.ordersMongoRepository = ordersMongoRepository;
        this.mongoSequenceGenerator = mongoSequenceGenerator;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userMongoRepository.findAll().size() == 0) {
            System.out.println("Migrating from MySQL to Mongo...");

            System.out.println("Migrating products...");
            migrateProducts();

            System.out.println("Migrating users...");
            migrateUsers();

            System.out.println("Migrating orders...");
            migrateOrders();

            System.out.println("Finished migrating from Mysql to Mongo!");
        }
        else {
            System.out.println("No need to do mongo migration!");
        }
    }

    private void migrateProducts(){
        List<Product> mysqlProducts = productRepository.findAll();
        List<Manufacturer> mysqlManufacturer = manufacturerRepository.findAll();

        for(Product product : mysqlProducts){
            OnlineShop.online_shop.model.mongo.Product mongoProduct = new OnlineShop.online_shop.model.mongo.Product();
            mongoProduct.setId(product.getId());
            mongoProduct.setAvailability(product.isAvailability());
            mongoProduct.setDescription(product.getDescription());
            mongoProduct.setPrice(product.getPrice());
            mongoProduct.setRating(product.getRating());

            // set manufacturer
            Manufacturer manufacturer = mysqlManufacturer.parallelStream()
                    .filter(p -> p.getId().equals(product.getManufacturer().getId()))
                    .findFirst().get();
            OnlineShop.online_shop.model.mongo.Manufacturer mongoManufacturer = new OnlineShop.online_shop.model.mongo.Manufacturer();
            mongoManufacturer.setId(manufacturer.getId());
            mongoManufacturer.setName(manufacturer.getName());
            mongoManufacturer.setSector(manufacturer.getSector());
            mongoProduct.setManufacturer(mongoManufacturer);

            productMongoRepository.save(mongoProduct);
        }


        // update mongo sequences
        mongoSequenceGenerator.updateSequence(OnlineShop.online_shop.model.mongo.Product.SEQUENCE_NAME,
                mysqlProducts.parallelStream().max(Comparator.comparingInt(Product::getId)).get().getId());
        mongoSequenceGenerator.updateSequence(OnlineShop.online_shop.model.mongo.Manufacturer.SEQUENCE_NAME,
                mysqlManufacturer.parallelStream().max(Comparator.comparingInt(Manufacturer::getId)).get().getId());
    }

    private void migrateUsers(){
        List<Users> users = userRepository.findAll();
        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        List<ListProduct> listProducts = listProductRepository.findAll();
        List<OnlineShop.online_shop.model.mongo.Product> mongoProducts = productMongoRepository.findAll();

        for(Users user : users){
            OnlineShop.online_shop.model.mongo.Users mongoUser = new OnlineShop.online_shop.model.mongo.Users();
            mongoUser.setId(user.getId());
            mongoUser.setFirstName(user.getFirstName());
            mongoUser.setSecondName(user.getSecondName());
            mongoUser.setUsername(user.getUsername());
            mongoUser.setPassword(user.getPassword());
            mongoUser.setPhoneNumber(user.getPhoneNumber());
            mongoUser.setEmail(user.getEmail());
            mongoUser.setAddress(user.getAddress());

            List<ShoppingList> userShoppingLists = shoppingLists.parallelStream()
                    .filter(sl -> sl.getUser().getId().equals(user.getId()))
                    .collect(Collectors.toList());

            List<OnlineShop.online_shop.model.mongo.ShoppingList> mongoShoppingLists = new ArrayList<>();
            for (ShoppingList shoppingList : userShoppingLists) {
                OnlineShop.online_shop.model.mongo.ShoppingList mongoShoppingList = new OnlineShop.online_shop.model.mongo.ShoppingList();
                mongoShoppingList.setShoppingListId(shoppingList.getShoppingListId());
                mongoShoppingList.setItemCount(shoppingList.getItemCount());
                mongoShoppingList.setName(shoppingList.getName());

                List<Integer> shoppingListProducts = listProducts.parallelStream()
                        .filter(lp -> lp.getListId() == shoppingList.getShoppingListId())
                        .map(ListProduct::getProductId)
                        .collect(Collectors.toList());
                List<OnlineShop.online_shop.model.mongo.Product> shoppingListMongoProducts = mongoProducts.parallelStream()
                        .filter(mp -> shoppingListProducts.contains(mp.getId()))
                        .collect(Collectors.toList());
                mongoShoppingList.setProducts(shoppingListMongoProducts);

                mongoShoppingLists.add(mongoShoppingList);
            }

            mongoUser.setShoppingList(mongoShoppingLists);

            userMongoRepository.save(mongoUser);
        }

        // update mongo sequences
        mongoSequenceGenerator.updateSequence(OnlineShop.online_shop.model.mongo.Users.SEQUENCE_NAME,
                users.parallelStream().max(Comparator.comparingInt(Users::getId)).get().getId());
        mongoSequenceGenerator.updateSequence(OnlineShop.online_shop.model.mongo.ShoppingList.SEQUENCE_NAME,
                shoppingLists.parallelStream().max(Comparator.comparingInt(ShoppingList::getShoppingListId)).get().getShoppingListId());
    }

    private void migrateOrders(){
        List<Orders> mysqlOrders = ordersRepository.findAll();
        List<BoughtProduct> boughtProducts = boughtProductRepository.findAll();

        List<OnlineShop.online_shop.model.mongo.Product> mongoProducts = productMongoRepository.findAll();
        List<OnlineShop.online_shop.model.mongo.Users> mongoUsers = userMongoRepository.findAll();

        for(Orders mysqlOrder : mysqlOrders){
            OnlineShop.online_shop.model.mongo.Orders mongoOrder = new OnlineShop.online_shop.model.mongo.Orders();
            mongoOrder.setAddress(mysqlOrder.getAddress());
            mongoOrder.setDate(mysqlOrder.getDate());
            mongoOrder.setOrderId(mysqlOrder.getOrderId());
            mongoOrder.setPrice(mysqlOrder.getPrice());

            List<Integer> mysqlOrderProducts = boughtProducts.parallelStream()
                    .filter(bp -> bp.getOrderId() == mysqlOrder.getOrderId())
                    .map(BoughtProduct::getProductId)
                    .collect(Collectors.toList());
            List<OnlineShop.online_shop.model.mongo.Product> mongoOrderProducts = mongoProducts.parallelStream()
                    .filter(mp -> mysqlOrderProducts.contains(mp.getId()))
                    .collect(Collectors.toList());
            mongoOrder.setProducts(mongoOrderProducts);

            OnlineShop.online_shop.model.mongo.Users orderUser = mongoUsers.parallelStream()
                    .filter(u -> u.getId().equals(mysqlOrder.getUser().getId()))
                    .findFirst().get();
            mongoOrder.setUser(orderUser);
            ordersMongoRepository.save(mongoOrder);
        }

        // update mongo sequences
        mongoSequenceGenerator.updateSequence(OnlineShop.online_shop.model.mongo.Orders.SEQUENCE_NAME,
                mysqlOrders.parallelStream().max(Comparator.comparingInt(Orders::getOrderId)).get().getOrderId());
    }
}
