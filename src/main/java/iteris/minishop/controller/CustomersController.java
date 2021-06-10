package iteris.minishop.controller;

import iteris.minishop.domain.dto.CustomerCreateRequest;
import iteris.minishop.domain.dto.CustomerFilterRequest;
import iteris.minishop.domain.dto.CustomerResponse;
import iteris.minishop.domain.entity.Customer;
import iteris.minishop.service.CustomersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.util.List;

@RestController
public class CustomersController {

    private final CustomersService service;

    public CustomersController(CustomersService service) {

        this.service = service;
    }


    // Lista todos os Costumers
    @GetMapping(value = "api/customers")
    public ResponseEntity<List<CustomerResponse>> listar(CustomerFilterRequest filter) {
        var listaDeClientes = service.listar(filter);
        return ResponseEntity.ok(listaDeClientes);
    }

    // Consulta o Customer por id
    @GetMapping(value = "api/customers/{id}")
    public ResponseEntity<CustomerResponse> buscarPorId(@PathVariable Integer id) {
        var customerResponse = service.buscarPorId(id);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping(value = "api/customers")
    public ResponseEntity<CustomerResponse> criarCliente(@RequestBody @Valid CustomerCreateRequest customer) {
        var customerResponse = service.criarCliente(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping(value = "api/customers/{idCustomer}")
    public ResponseEntity<CustomerResponse> deletarCliente(@PathVariable Integer idCustomer) {
        var customer = service.deletarCliente(idCustomer);
        return ResponseEntity.ok(customer);
    }


}
