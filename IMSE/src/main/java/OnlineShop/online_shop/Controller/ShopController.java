package OnlineShop.online_shop.Controller;

import OnlineShop.online_shop.model.mongo.Users;
import OnlineShop.online_shop.services.ProductService;
import OnlineShop.online_shop.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.security.Principal;

@Controller
public class ShopController {
    private final UserService userService;
    private final ProductService productService;

    public ShopController(UserService userService,
                          ProductService productService) {
        this.userService = userService;
        this.productService = productService;
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
        model.addAttribute("user", user);
        model.addAttribute("products", userService.addToShoppingList(user, productId).getProducts());
        return "basket";
    }
}
