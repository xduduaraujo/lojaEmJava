package iteris.minishop.controller;

import iteris.minishop.domain.dto.CustomerFilterRequest;
import iteris.minishop.domain.dto.CustomerResponse;
import iteris.minishop.domain.dto.SupplierCreateRequest;
import iteris.minishop.domain.dto.SupplierResponse;
import iteris.minishop.domain.entity.Supplier;
import iteris.minishop.service.SuppliersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SuppliersController {
    private final SuppliersService service;

    public SuppliersController(SuppliersService service) {
        this.service = service;
    }

    @GetMapping(value = "api/suppliers")
    public ResponseEntity<List<SupplierResponse>> listar() {
        var listaDeFornecedores = service.listar();
        return ResponseEntity.ok(listaDeFornecedores);
    }


    @GetMapping(value = "api/suppliers/{id}")
    public ResponseEntity<SupplierResponse> buscarPorId(@PathVariable Integer id) {
        var supplierResponse = service.buscarPorId(id);
        return ResponseEntity.ok(supplierResponse);
    }


    @PostMapping(value = "api/suppliers")
    public ResponseEntity<SupplierResponse> criarFornecedor(@RequestBody @Valid SupplierCreateRequest supplier) {
        var supplierResponse = service.criarFornecedor(supplier);
        return ResponseEntity.ok(supplierResponse);
    }

    @DeleteMapping(value = "api/suppliers/{idSupplier}")
    public ResponseEntity<SupplierResponse> deletarFornecedor(@PathVariable Integer idSupplier) {
        var supplier = service.deletarFornecedor(idSupplier);
        return ResponseEntity.ok(supplier);
    }

}
