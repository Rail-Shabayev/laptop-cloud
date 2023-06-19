package sia.laptopcloud.laptops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.laptopcloud.laptops.model.Cart;
import sia.laptopcloud.laptops.model.Laptop;
import sia.laptopcloud.laptops.repository.CartRepository;
import sia.laptopcloud.laptops.repository.LaptopRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private LaptopRepository laptopRepo;
    private CartRepository cartRepo;

    public CartController(LaptopRepository laptopRepo, CartRepository cartRepo) {
        this.laptopRepo = laptopRepo;
        this.cartRepo = cartRepo;
    }

    Long count = 1L;

    @ModelAttribute
    public void addLaptopsToModel(Model model) {
        List<Laptop> laptops = new ArrayList<>();
        cartRepo.findAll().forEach(cart -> laptops.addAll(cart.getLaptops()));
        model.addAttribute("laptops", laptops);
    }

    @GetMapping
    public String showCart(Model model) {
        return "cart";
    }

    @GetMapping("/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Iterable<Cart> carts = cartRepo.findAll();
        List<Laptop> laptops = new ArrayList<>();
        carts.forEach(
                cart -> laptops.addAll(cart.getLaptops()));
        Laptop laptop2 = null;
        for (Laptop laptop : laptops) {
            if (laptop.getId().equals(id)) {
                laptop2 = laptop;
            }
        }
        if (laptop2 != null) {
            laptops.remove(laptop2);
        }
        Cart cart = new Cart(count++, laptops);
        cartRepo.deleteAll();
        cartRepo.save(cart);
        model.addAllAttributes(laptops);
        return "redirect:/cart";
    }
}
