package sia.laptopcloud.laptops.controller;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import sia.laptopcloud.laptops.model.Cart;
import sia.laptopcloud.laptops.model.SecurityUser;
import sia.laptopcloud.laptops.model.User;
import sia.laptopcloud.laptops.model.UserOrder;
import sia.laptopcloud.laptops.repository.CartRepository;
import sia.laptopcloud.laptops.repository.UserOrderRepository;
import sia.laptopcloud.laptops.repository.UserRepository;

import java.security.Principal;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private UserRepository userRepo;
    private UserOrderRepository orderRepo;
    private CartRepository cartRepo;

    public OrderController(UserRepository userRepo, UserOrderRepository orderRepo, CartRepository cartRepo) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
    }

    @ModelAttribute("user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userRepo.findByUsername(username).get();
        return user;
    }
    @ModelAttribute("userOrder")
    public UserOrder order() {
        return new UserOrder();
    }

    @GetMapping
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid UserOrder order, Errors errors) {

        if (errors.hasErrors()) {
            return "orderForm";
        }
        Authentication auth = getContext().getAuthentication();
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        User principal = user.getUser();
        order.setUser(principal);
        for (Cart cart : cartRepo.findAll()) {
            order.addCart(cart);
        }
        orderRepo.save(order);
        return "redirect:/";
    }
}
