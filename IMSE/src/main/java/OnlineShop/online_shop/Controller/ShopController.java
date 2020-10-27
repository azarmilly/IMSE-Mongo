package OnlineShop.online_shop.Controller;

import OnlineShop.online_shop.model.mongo.Orders;
import OnlineShop.online_shop.model.mongo.Product;
import OnlineShop.online_shop.model.mongo.ShoppingList;
import OnlineShop.online_shop.model.mongo.Users;
import OnlineShop.online_shop.services.OrdersService;
import OnlineShop.online_shop.services.ProductService;
import OnlineShop.online_shop.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ShopController {
    private final UserService userService;
    private final ProductService productService;
    private final OrdersService ordersService;

    public ShopController(UserService userService,
                          ProductService productService,
                          OrdersService ordersService) {
        this.userService = userService;
        this.productService = productService;
        this.ordersService = ordersService;
    }

    @GetMapping(value = "/")
    public String index(Model model, Principal principal){
        Users user = (Users) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @GetMapping(value = "/basket/add")
    public String addToBasket(Model model,
                              Principal principal,
                              int productId){
        Users user = (Users) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        ShoppingList shoppingList = userService.addToShoppingList(user, productId);

        model.addAttribute("user", user);
        model.addAttribute("products", shoppingList.getProducts());
        model.addAttribute("shoppingListId", shoppingList.getId());
        return "basket";
    }

    @GetMapping(value = "/basket")
    public String addToBasket(Model model,
                              Principal principal){
        Users user = (Users) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        ShoppingList activeShoppingList = userService.getActiveShoppingList(user);

        List<Product> listProducts = new ArrayList<>();
        if(activeShoppingList != null){
            listProducts = activeShoppingList.getProducts();
            model.addAttribute("shoppingListId", activeShoppingList.getId());
        }

        model.addAttribute("user", user);
        model.addAttribute("products", listProducts);

        return "basket";
    }

    @GetMapping(value = "/checkout")
    public String checkout(Model model,
                              Principal principal,
                              int shoppingListId){
        Users user = (Users) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        ShoppingList shoppingList = user.getShoppingList()
                .parallelStream()
                .filter(sp -> sp.getId().equals(shoppingListId))
                .findFirst().get();

        int orderId = ordersService.addOrders(user, shoppingList);
        userService.deactivateShoppingLists(user);

        model.addAttribute("user", user);
        model.addAttribute("orderId", orderId);
        return "order-complete";
    }

    @GetMapping(value = "/orders")
    public String orders(Model model,
                           Integer daysBefore,
                           Principal principal){
        Users user = (Users) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if(daysBefore == null){
            daysBefore = 1;
        }
        List<Orders> orders = ordersService.getUserOrders(user, daysBefore);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("daysBefore", daysBefore);
        return "orders";
    }
}
