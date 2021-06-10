package iteris.minishop.domain.dto;

import iteris.minishop.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomerOrderResponse {
    private int idOrder;
    private LocalDateTime orderDate;
    private String orderNumber;
    private double totalAmount;
    public Customer customer;
}
