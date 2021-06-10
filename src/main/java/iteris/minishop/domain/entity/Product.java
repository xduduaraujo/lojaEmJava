package iteris.minishop.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int idProduct;

    @Column(name = "ProductName", length = 50, nullable = false)
    private String productName;

    @Column(name = "UnitPrice")
    private Double unitPrice;

    @Column(name = "PackageName", length = 30)
    private String packageName;

    @Column(name = "isDiscontinued", nullable = false)
    private boolean discontinued;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="SupplierId", nullable = false)
    private Supplier supplier;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> productImages;
}
