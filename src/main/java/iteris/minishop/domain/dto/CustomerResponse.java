package iteris.minishop.domain.dto;

import iteris.minishop.domain.entity.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomerResponse {

    private int idCustomer;

    private String firstName;

    private String lastName;

    private String city;

    private String country;

    private String phone;

}
