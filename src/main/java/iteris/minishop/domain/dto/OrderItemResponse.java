package iteris.minishop.domain.dto;

import iteris.minishop.domain.entity.CustomerOrder;
import iteris.minishop.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private int idOrderItem;
    private Double unitPrice;
    private int quantity;
    private CustomerOrder order;
    private Product product;
}
