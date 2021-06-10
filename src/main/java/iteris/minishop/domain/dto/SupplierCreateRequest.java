package iteris.minishop.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SupplierCreateRequest {
    @NotEmpty(message = "É obrigatório informar o nome da empresa.")
    @Size(max=40)
    private String companyName;
    @Size(max=50)
    private String contactName;
    @Size(max=40)
    private String city;
    @Size(max=40)
    private String country;
    @Size(max=30)
    private String phone;
    @Size(max=30)
    private String fax;
}
