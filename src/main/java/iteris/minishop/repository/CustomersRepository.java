package iteris.minishop.repository;

import iteris.minishop.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {

    //Optional<Customer> findByCountryContaining(String country);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM customer WHERE (:country IS NULL or country = :country)"
    )
    List<Customer> listarComFiltroNativo(@Param("country") String country);


    @Query("SELECT a FROM Customer a WHERE (:country IS NULL or country = :country)")
    List<Customer> listarComFiltro(@Param("country") String country);
}
