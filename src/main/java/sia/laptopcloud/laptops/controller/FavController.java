package sia.laptopcloud.laptops.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.laptopcloud.laptops.model.Laptop;
import sia.laptopcloud.laptops.repository.LaptopRepository;

import java.util.List;

@Controller
@RequestMapping("/favouritesList")
//@RequestMapping(path = "/api/tacos", produces = "application/json")
//@CrossOrigin(origins = "http://tacocloud:8080")
public class FavController {

    private LaptopRepository laptopRepo;

    public FavController(LaptopRepository laptopRepo) {
        this.laptopRepo = laptopRepo;
    }


    @GetMapping
    public String favLaptops(Model model) {
        List<Laptop> laptops = laptopRepo.findAllByIsFavorite(true);
        model.addAttribute("laptops", laptops);
        return "favorites";
    }

    @GetMapping("/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Laptop laptop = laptopRepo.findById(id).get();
        laptopRepo.updateIsFavoriteById(false, id);
        return "redirect:/favouritesList";
    }
}


