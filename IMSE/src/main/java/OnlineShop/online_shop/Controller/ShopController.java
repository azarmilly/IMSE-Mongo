package OnlineShop.online_shop.Controller;

import OnlineShop.online_shop.model.Product;
import OnlineShop.online_shop.model.ShoppingList;
import OnlineShop.online_shop.model.Users;
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
        model.addAttribute("shoppingListId", shoppingList.getShoppingListId());
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
            model.addAttribute("shoppingListId", activeShoppingList.getShoppingListId());
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

        ShoppingList shoppingList = userService.getActiveShoppingList(user);
        int orderId = ordersService.addOrders(user, shoppingList);
        userService.deactivateShoppingLists(user);

        model.addAttribute("user", user);
        model.addAttribute("orderId", orderId);
        return "order-complete";
    }
}
