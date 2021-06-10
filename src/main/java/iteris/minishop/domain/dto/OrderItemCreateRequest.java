package iteris.minishop.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderItemCreateRequest {
    @NotNull(message = "O campo UnitPrice não pode ser nulo.")
    private Double unitPrice;
    @NotNull(message = "O campo Quantidade não pode ser nulo.")
    private Integer quantity;
    @NotNull(message = "O campo OrderId não pode ser nulo.")
    private Integer orderId;
    @NotNull(message = "O campo productId não pode ser nulo.")
    private Integer productId;
}
