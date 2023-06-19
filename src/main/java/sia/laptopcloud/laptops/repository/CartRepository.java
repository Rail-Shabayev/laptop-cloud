package sia.laptopcloud.laptops.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import sia.laptopcloud.laptops.model.Cart;
import sia.laptopcloud.laptops.model.Laptop;

public interface CartRepository extends PagingAndSortingRepository<Cart, Long>, CrudRepository<Cart, Long> {
    @Transactional
    @Modifying
    @Query("delete from Cart c where c.laptops = ?1")
    int deleteByLaptops(Laptop laptops);

}