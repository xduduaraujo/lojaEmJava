package iteris.minishop.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class CustomerCreateRequest {

    @NotBlank(message = "O primeiro nome do cliente deve ser definido")
    @Size(max = 40)
    private String firstName;

    @NotBlank(message = "O sobrenome do cliente deve ser definido")
    @Size(max = 40)
    private String lastName;

    @Size(max = 40)
    private String city;

    @Size(max = 40)
    private String country;

    @Size(max = 20)
    private String phone;

}

