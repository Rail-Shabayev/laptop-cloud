package sia.laptopcloud.laptops.repository;


import org.springframework.data.repository.CrudRepository;
import sia.laptopcloud.laptops.model.UserOrder;

public interface UserOrderRepository extends CrudRepository<UserOrder, Long> {
    UserOrder getOrderById(long id);

    void deleteOrderById(long id);
}