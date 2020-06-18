package OnlineShop.online_shop.services;

import OnlineShop.online_shop.model.*;
import OnlineShop.online_shop.repositories.ShoppingListRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service("generatorService")
public class GeneratorServiceImpl {

    @Autowired UserService userService;

    @Autowired ManufacturerService manufacturerService;

    @Autowired ProductService productService;
    @Autowired OrdersService ordersService;
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @PostConstruct
    private void init(){
        if(userService.getAllUsers().size() == 0) {
            System.out.println("Users size is 0, starting Mysql data generation...");
            generateUserData();
            generateManufacturerData();
            generateProductData();
            generateShoppingListData();
            generateOrdersData();
            System.out.println("Data generation is done!");
        }
        else {
            System.out.println("No need to generate data for Mysql");
        }
    }

    public void generateUserData() {
        Users user;
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            user = new Users();
            user.setFirstName(faker.name().firstName());
            user.setSecondName(faker.name().lastName());
            user.setUsername(faker.name().username());
            user.setPassword(faker.name().username());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setEmail(user.getFirstName() + "@gmail.com");
            user.setAddress(faker.address().streetAddress());
            userService.addUser(user);
        }
    }


    public void generateManufacturerData() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        Manufacturer manufacturer;
        for (int i = 0; i < 10; i++) {
            manufacturer = new Manufacturer();
            Faker faker = new Faker();
            manufacturer.setName(faker.name().name());
            manufacturer.setSector(faker.company().industry());
            manufacturerService.addManufacturer(manufacturer);
            manufacturers.add(manufacturer);
        }
    }

    public void generateProductData() {
        Product product;
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        DecimalFormat df2 = new DecimalFormat("#.##");
        for(int i=0; i<50; i++) {
            double price = Double.valueOf(df2.format(ThreadLocalRandom.current().nextDouble(0,300)));
            double rating = Double.valueOf(df2.format(ThreadLocalRandom.current().nextDouble(0,10)));
            product = new Product();
            product.setPrice(price);
            product.setRating(rating);
            product.setAvailability(ThreadLocalRandom.current().nextBoolean());

            //Sets random foreign key
            int idFrom = manufacturers.get(0).getId();
            int idTo = manufacturers.get(manufacturers.size()-1).getId();
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(ThreadLocalRandom.current().nextInt(idFrom,idTo));
            product.setManufacturer(manufacturer);
            productService.addProduct(product);
        }
    }

    public void generateShoppingListData() {
        List<Users> users = userService.getAllUsers();
        ShoppingList shoppingList;
        for(int i=0; i<50; i++) {
            shoppingList = new ShoppingList();
            Random rand = new Random();
            shoppingList.setItemCount(rand.nextInt(7));
            shoppingList.setName("MyShoppingList");

            //Sets random foreign key
            int idFrom = users.get(0).getId();
            int idTo = users.get(users.size()-1).getId();
            Users user = new Users();
            user.setId(ThreadLocalRandom.current().nextInt(idFrom,idTo));
            shoppingList.setUser(user);

            shoppingListRepository.save(shoppingList);
        }
    }

    public void generateOrdersData() {
        Orders orders;
        List<Users> users = userService.getAllUsers();

        List<String> statusList = new ArrayList<>();
        Date fromDate = new Date(115, 1, 1);
        Date toDate = new Date();
        statusList.add("Confirmed");
        statusList.add("Dispatched");
        statusList.add("Waiting for Payment");
        statusList.add("Received");
        DecimalFormat df2 = new DecimalFormat("#.##");


        for(int i=0; i<50; i++) {
            orders = new Orders();
            Faker faker = new Faker();
            Random rand = new Random();
            double price = Double.valueOf(df2.format(ThreadLocalRandom.current().nextDouble(0,500)));

            orders.setPrice(price);
            orders.setAddress(faker.address().streetAddress());
            orders.setDate(faker.date().between(fromDate, toDate));
            orders.setStatus(statusList.get(rand.nextInt(4)));

            //Sets random foreign key
            int idFrom = users.get(0).getId();
            int idTo = users.get(users.size()-1).getId();
            Users user = new Users();
            user.setId(ThreadLocalRandom.current().nextInt(idFrom,idTo));
            orders.setUser(user);
            ordersService.addOrder(orders);
        }
    }

}
