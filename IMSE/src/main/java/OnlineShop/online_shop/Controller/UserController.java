package OnlineShop.online_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import OnlineShop.online_shop.model.Users;
import OnlineShop.online_shop.services.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String homepage(Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        //userService.addUser(user);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Users user, Model model) {
        userService.addUser(user);
        model.addAttribute("user", user);
        return "login";
    }
}