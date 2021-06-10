package iteris.minishop.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iteris.minishop.domain.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductResponse {
    public int idProduct;
    public String productName;
    public Double unitPrice;
    public String packageName;
    public boolean isDiscontinued;
    public Supplier supplier;
}
