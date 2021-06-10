package iteris.minishop.repository;

import iteris.minishop.domain.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrdersRepository extends JpaRepository<CustomerOrder, Integer> {

}
