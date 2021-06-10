package iteris.minishop.domain.dto;

import iteris.minishop.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductImageUpdatedResponse {
    public Integer idProductImage;
    public String path;
    public Integer seqExibicao;
    public Product product;

    public Integer idProductImageDestino;
    public String pathDestino;
    public Integer seqExibicaoDestino;
    public Product productDestino;
}
