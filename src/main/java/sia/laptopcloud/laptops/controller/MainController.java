package sia.laptopcloud.laptops.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.laptopcloud.laptops.model.Cart;
import sia.laptopcloud.laptops.model.Laptop;
import sia.laptopcloud.laptops.repository.LaptopRepository;
import sia.laptopcloud.laptops.repository.CartRepository;
import sia.laptopcloud.laptops.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private CartRepository cartRepository;
    private LaptopRepository laptopRepository;
    private UserRepository userRepo;

    public MainController(CartRepository cartRepository, LaptopRepository laptopRepository, UserRepository userRepo) {
        this.cartRepository = cartRepository;
        this.userRepo = userRepo;
        this.laptopRepository = laptopRepository;
    }

    @ModelAttribute
    public void addLaptopsToModel(Model model) {
        List<Laptop> laptops = new ArrayList<>();
        laptopRepository.findAll().forEach(laptops::add);
        Collections.sort(laptops, Comparator.comparing(Laptop::getId));
        model.addAttribute("laptops", laptops);
    }


    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @ModelAttribute(name = "cart")
    public Cart cart() {
        return new Cart();
    }

    @PostMapping
    public String processLaptops(@Valid Cart cart, Errors errors) {
        if (errors.hasErrors()) {
            return "design";
        }
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    @GetMapping("/favorites/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Laptop laptop = laptopRepository.findById(id).get();
        if (laptop.getIsFavorite() == false)
            laptopRepository.updateIsFavoriteById(true, id);
        else
            laptopRepository.updateIsFavoriteById(false, id);
        return "redirect:/";
    }
}
