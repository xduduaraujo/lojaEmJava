package iteris.minishop.domain.dto;

import iteris.minishop.domain.entity.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CustomerOrderCreateRequest {

    @NotNull(message = "É obrigatório informar a data.")
    private LocalDateTime orderDate;

    @Size(max = 10, message = "O nº do pedido deve ter no máximo 10 caracteres")
    private String orderNumber;

    private double totalAmount;

    @NotNull(message = "É obrigatório informar o cliente relacionado a este pedido.")
    private int CustomerId;
}
