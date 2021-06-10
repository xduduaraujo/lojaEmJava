package iteris.minishop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int idProductImage;

    @Column(name = "Path", length = 250, nullable = false)
    private String path;

    @Column(name = "SeqExibicao", nullable = false)
    private Integer sequencia;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ProductId", nullable = false)
    private Product product;
}
