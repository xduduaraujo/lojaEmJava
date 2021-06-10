package iteris.minishop.controller;

import com.fasterxml.jackson.annotation.JsonView;
import iteris.minishop.domain.dto.ProductCreateRequest;
import iteris.minishop.domain.dto.ProductResponse;
import iteris.minishop.domain.entity.ImportRegistry;
import iteris.minishop.domain.entity.Product;
import iteris.minishop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class ProductsController {
    private final ProductsService service;

    @Autowired
    public ProductsController(ProductsService service){
        this.service = service;
    }

    @GetMapping("api/product")
    public ResponseEntity<List<ProductResponse>> listarProdutos(){
        var produtos = service.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("api/product/{id}")
    public ResponseEntity<ProductResponse> listarProdutoPorId(@PathVariable Integer id){
        var produto = service.procurarProduto(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping("api/product/xls")
    public ResponseEntity<ImportRegistry> uploadDeProdutos(@RequestParam("file") MultipartFile file) throws IOException {
        var produto = service.importarProdutos(file);
        return ResponseEntity.ok(produto);
    }

    @PostMapping("api/product")
    public ResponseEntity<ProductResponse> cadastrarProduto (@RequestBody @Valid ProductCreateRequest productCreateRequest){
        var novoProduto = service.cadastrarProduto(productCreateRequest);
        return ResponseEntity.ok(novoProduto);
    }

    @DeleteMapping("api/product/{id}")
    public ResponseEntity<ProductResponse> deletarProduto (@PathVariable Integer id){
        var produto = service.deletarProduto(id);
        return ResponseEntity.ok(produto);
    }
}
