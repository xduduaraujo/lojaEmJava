package iteris.minishop.repository;

import iteris.minishop.domain.entity.Customer;
import iteris.minishop.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImage, Integer> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM ProductImage WHERE (:path IS NULL or path = :path) AND (:productId IS NULL or productId = :productId)"
    )
    Optional<List<ProductImage>> procurarImagem(@Param("path") String path, @Param("productId") Integer productId);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM ProductImage WHERE (:idProduto is NULL or ProductId = :idProduto)",
            countQuery = "SELECT count(*) FROM ProductImage WHERE (:idProduto is NULL or ProductId = :idProduto)"
    )
    Optional<List<ProductImage>> listarImagensPorProduto(@Param("idProduto") Integer idProduto);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM ProductImage WHERE (:idProduto IS NULL or ProductId = :idProduto) AND (:idSequencia IS NULL or SeqExibicao = :idSequencia)"
    )
    Optional<ProductImage> imagemPorProduto(@Param("idProduto") Integer idProduto, @Param("idSequencia") Integer idSequencia);

}