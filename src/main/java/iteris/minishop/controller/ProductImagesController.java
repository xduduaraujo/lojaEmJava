package iteris.minishop.controller;

import iteris.minishop.domain.dto.ProductImageUpdatedResponse;
import iteris.minishop.domain.dto.ProductImagesResponse;
import iteris.minishop.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class ProductImagesController {
    private final ProductImagesService service;

    @Autowired
    public ProductImagesController(ProductImagesService service){
        this.service = service;
    }

    @PostMapping(value = "api/productImage/produto/{idProduto}/sequencia/{idSequencia}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductImagesResponse> uploadFile(@RequestParam("file")MultipartFile file, @PathVariable Integer idProduto, @PathVariable Integer idSequencia){
        var arquivo = service.uploadFile(file, idProduto, idSequencia);
        return ResponseEntity.ok(arquivo);
    }

    @GetMapping("api/productImage")
    public ResponseEntity<List<ProductImagesResponse>> listarArquivos(){
        var retorno = service.listar();
        return ResponseEntity.ok(retorno);
    }

    @PutMapping(value="api/productImage/{id}/sequencia/{idSequencia}")
    public ResponseEntity<ProductImageUpdatedResponse> alterarPosicao (@PathVariable Integer id, @PathVariable Integer idSequencia){
        var retorno = service.alterarPosicao(id, idSequencia);
        return ResponseEntity.ok(retorno);
    }
}
