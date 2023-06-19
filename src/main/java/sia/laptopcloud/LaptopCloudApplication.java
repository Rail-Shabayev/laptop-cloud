package sia.laptopcloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import sia.laptopcloud.laptops.model.Laptop;
import sia.laptopcloud.laptops.model.User;
import sia.laptopcloud.laptops.repository.LaptopRepository;
import sia.laptopcloud.laptops.repository.UserRepository;


@SpringBootApplication
public class LaptopCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaptopCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            LaptopRepository repo,
            UserRepository userRepo,
            PasswordEncoder encoder) {
        return args -> {
            Laptop laptop1 = new Laptop(
                    1L, "Ноутбук HP Omen 17-ck0045ur, 17.3\", IPS,  NVIDIA GeForce RTX 3080 для ноутбуков - 16384 МБ, черный",
                    false, " Intel Core i7 11800H 2.3 ГГц (4.6 ГГц, в режиме Turbo)",
                    "1024 ГБ", "Windows 11 Home");
            Laptop laptop2 = new Laptop(
                    2L, "Ноутбук MSI Titan GT77 12UHS-208RU, 17.3\", IPS,  NVIDIA GeForce RTX 3080 Ti для ноутбуков - 16384 МБ, черный",
                    false, "Intel Core i9 12900HX 2.3 ГГц (5.0 ГГц, в режиме Turbo)",
                    "3072 ГБ", "Windows 11 Home");
            Laptop laptop3 = new Laptop(
                    3L, "Ноутбук MSI CreatorPro Z17 A12UMST-260RU, 17\", IPS,  NVIDIA GeForce RTX A5500 - 16384 МБ, серый",
                    false, "Intel Core i7 11800H 2.3 ГГц (4.6 ГГц, в режиме Turbo)",
                    "1024 ГБ", "Windows 11 Home");
            Laptop laptop4 = new Laptop(
                    4L, "Ноутбук MSI CreatorPro Z16P B12UMST-223RU, 16\", IPS, NVIDIA GeForce RTX A5500 - 16384 МБ, серый",
                    false, "Intel Core i9 12900H 2.5 ГГц (5.0 ГГц, в режиме Turbo)",
                    "2048 ГБ", "Windows 11 Home");
            Laptop laptop5 = new Laptop(
                    5L, "Ноутбук Acer ConceptD 7 CN715-73G-73ZX, 15.6\", IPS, , NVIDIA GeForce RTX 3080 для ноутбуков - 8192 МБ, белый",
                    false, " Intel Core i9 12900H 2.5 ГГц (5.0 ГГц, в режиме Turbo)",
                    "2048 ГБ", "Windows 11 Home");
            repo.save(laptop1);
            repo.save(laptop2);
            repo.save(laptop3);
            repo.save(laptop4);
            repo.save(laptop5);
            userRepo.save(new User("habuma", encoder.encode("password"),
                    "Craig Walls", "123 North Street", "Cross Roads", "ROLE_USER"));

        };
    }
}
