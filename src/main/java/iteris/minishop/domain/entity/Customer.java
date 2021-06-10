package iteris.minishop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int idCustomer;

    @Column(name = "FirstName", length = 40, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 40, nullable = false)
    private String lastName;

    @Column(name = "City", length = 40)
    private String city;

    @Column(name = "Country", length = 40)
    private String country;

    @Column(name = "Phone", length = 20)
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<CustomerOrder> customerOrders;
}
