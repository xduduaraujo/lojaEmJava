package iteris.minishop.service;

import iteris.minishop.domain.dto.CustomerFilterRequest;
import iteris.minishop.domain.dto.CustomerResponse;
import iteris.minishop.domain.dto.SupplierCreateRequest;
import iteris.minishop.exception.SupplierHasProductsException;
import iteris.minishop.exception.SupplierNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import iteris.minishop.domain.dto.SupplierResponse;
import iteris.minishop.domain.entity.Supplier;
import iteris.minishop.repository.SuppliersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuppliersService {

    private final SuppliersRepository repository;

    @Autowired
    public SuppliersService(SuppliersRepository repository) {
        this.repository = repository;
    }

    public SupplierResponse criarFornecedor(SupplierCreateRequest supplierCreateRequest) {

        var novoSupplier = new Supplier();
        novoSupplier.setCompanyName(supplierCreateRequest.getCompanyName());
        novoSupplier.setContactName(supplierCreateRequest.getContactName());
        novoSupplier.setCity(supplierCreateRequest.getCity());
        novoSupplier.setCountry(supplierCreateRequest.getCountry());
        novoSupplier.setPhone(supplierCreateRequest.getPhone());
        novoSupplier.setFax(supplierCreateRequest.getFax());
        var supplierSalvo = repository.save(novoSupplier);

        return new SupplierResponse(
                supplierSalvo.getIdSupplier(),
                supplierSalvo.getCompanyName(),
                supplierSalvo.getContactName(),
                supplierSalvo.getCity(),
                supplierSalvo.getCountry(),
                supplierSalvo.getPhone(),
                supplierSalvo.getFax()
        );
    }


    public List<SupplierResponse> listar() {
        var resultado = repository.findAll();

        var listaDeFornecedores = resultado.stream().map(fornecedor -> {
                    return new SupplierResponse(
                            fornecedor.getIdSupplier(),
                            fornecedor.getCompanyName(),
                            fornecedor.getContactName(),
                            fornecedor.getCity(),
                            fornecedor.getCountry(),
                            fornecedor.getPhone(),
                            fornecedor.getFax()
                    );
                }
        ).collect(Collectors.toList());
        return listaDeFornecedores;
    }
    

    public SupplierResponse buscarPorId(Integer idSupplier) {
        var supplierEncontrado = repository.findById(idSupplier);
        if (supplierEncontrado.isEmpty()) {
            throw new SupplierNotFoundException("Supplier: " + idSupplier + " não encontrado.");
        }
        var fornecedorSalvo = supplierEncontrado.get();
        return new SupplierResponse(
                fornecedorSalvo.getIdSupplier(),
                fornecedorSalvo.getCompanyName(),
                fornecedorSalvo.getContactName(),
                fornecedorSalvo.getCity(),
                fornecedorSalvo.getCountry(),
                fornecedorSalvo.getPhone(),
                fornecedorSalvo.getFax()
        );
    }


    public SupplierResponse deletarFornecedor(Integer idSupplier) {
        var supplierEncontrado = repository.findById(idSupplier);

        if (supplierEncontrado.isEmpty()) {
            throw new SupplierNotFoundException("Supplier: " + idSupplier + " não encontrado.");
        }

        var supplier = supplierEncontrado.get();

       if (!supplier.getProduct().isEmpty()){
            throw new SupplierHasProductsException();
       }

        repository.delete(supplier);

        return new SupplierResponse(
                supplier.getIdSupplier(),
                supplier.getCompanyName(),
                supplier.getContactName(),
                supplier.getCity(),
                supplier.getCountry(),
                supplier.getPhone(),
                supplier.getFax()
        );
   }
}