package iteris.minishop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int idSupplier;

    @Column(length = 40, name = "CompanyName", nullable = false)
    private String companyName;

    @Column(length = 50, name = "ContactName")
    private String contactName;

    @Column(length = 40, name = "City")
    private String city;

    @Column(length = 40, name = "Country")
    private String country;

    @Column(length = 30, name = "Phone")
    private String phone;

    @Column(length = 30, name = "Fax")
    private String fax;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Product> product;
}
