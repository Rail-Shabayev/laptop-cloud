package sia.laptopcloud.laptops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sia.laptopcloud.laptops.model.SecurityUser;
import sia.laptopcloud.laptops.model.User;
import sia.laptopcloud.laptops.model.UserOrder;
import sia.laptopcloud.laptops.repository.UserOrderRepository;
import sia.laptopcloud.laptops.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserOrderRepository orderRepo;

    @GetMapping
    public String showProfile(Model model) {
        Authentication auth = getContext().getAuthentication();
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        User principal = user.getUser();
        model.addAttribute("user", principal);

        Iterable<UserOrder> allOrders = orderRepo.findAll();
        List<UserOrder> orders = new ArrayList<>();
        for (UserOrder order : allOrders) {
            if (order.getUser().getId().equals(principal.getId())) {
                orders.add(order);
            }
        }
        model.addAttribute("orderList", allOrders);
        return "profile";
    }

    @PostMapping
    public String deleteProfile() {
        Authentication auth = getContext().getAuthentication();
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        User principal = user.getUser();
        userRepo.deleteById(principal.getId());
        return "redirect:/logout";
    }

    @Transactional
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        UserOrder order = orderRepo.getOrderById(id);
        model.addAttribute("order", order);
        return "updateOrder";
    }

    @Transactional
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable(value = "id") long id) {
        orderRepo.deleteOrderById(id);
        return "redirect:/profile";
    }

    @PostMapping("/saveOrder")
    public String saveEmployee(@ModelAttribute("order") UserOrder order) {
        UserOrder orders = orderRepo.findById(order.getId()).get();
        order.setUser(orders.getUser());
        order.setCart(orders.getCart());
        orderRepo.save(order);
        return "redirect:/profile";
    }
}
