package sia.laptopcloud.laptops.repository;

import org.springframework.data.repository.CrudRepository;
import sia.laptopcloud.laptops.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}
