package iteris.minishop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int idOrder;

    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "OrderNumber", length = 10)
    private String orderNumber;

    @Column(name = "TotalAmount")
    private double totalAmount;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CustomerId", nullable = false)
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "customerOrder", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
