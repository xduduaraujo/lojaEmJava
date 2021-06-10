package iteris.minishop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierResponse {
        private int idSupplier;
        private String companyName;
        private String contactName;
        private String city;
        private String country;
        private String phone;
        private String fax;

    }
