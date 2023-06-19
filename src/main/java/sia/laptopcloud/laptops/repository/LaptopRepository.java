package sia.laptopcloud.laptops.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import sia.laptopcloud.laptops.model.Laptop;

import java.util.List;

public interface LaptopRepository extends CrudRepository<Laptop, Long> {
    @Transactional
    @Modifying
    @Query("update Laptop l set l.isFavorite = ?1 where l.id = ?2")
    int updateIsFavoriteById(Boolean isFavorite, Object unknownAttr1);

    List<Laptop> findAllByIsFavorite(Boolean isFavorite);

    List<Laptop> findAllById(Iterable<Long> all);
}