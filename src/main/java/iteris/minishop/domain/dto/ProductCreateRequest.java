package iteris.minishop.domain.dto;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductCreateRequest {
    @NotBlank(message = "O nome do produto não pode estar em branco.")
    @Size(max = 50, message = "O tamanho do nome do produto deve ser no máximo 50 caracteres")
    private String productName;
    private Double unitPrice;
    @Size(max = 30, message = "O tamanho do nome do produto deve ser no máximo 30 caracteres")
    private String packageName;
    @NotNull(message = "O campo isDiscontinued deve ser informado.")
    private Boolean isDiscontinued = Boolean.FALSE;
    @NotNull
    private int idSupplier;
}
