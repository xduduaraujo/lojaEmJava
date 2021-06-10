package iteris.minishop.controller;

import iteris.minishop.domain.dto.CustomerOrderCreateRequest;
import iteris.minishop.domain.dto.CustomerOrderResponse;
import iteris.minishop.domain.entity.CustomerOrder;
import iteris.minishop.service.CustomerOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerOrdersController {
    private final CustomerOrdersService service;

    @Autowired
    public CustomerOrdersController(CustomerOrdersService service) {
        this.service = service;
    }

    @GetMapping(value = "api/customerOrders")
    public ResponseEntity<List<CustomerOrderResponse>> listar() {
        var listaDePedidos = service.listar();
        return ResponseEntity.ok(listaDePedidos);
    }

    @GetMapping(value = "api/customerOrders/{id}")
    public ResponseEntity<CustomerOrderResponse> buscarPorId(@PathVariable Integer id) {
        var customerOrderResponse = service.buscarPorId(id);
        return ResponseEntity.ok(customerOrderResponse);
    }


    @PostMapping(value = "api/customerOrders")
    public ResponseEntity<CustomerOrderResponse> criarPedido(@RequestBody @Valid CustomerOrderCreateRequest customerOrder) {
        var customerOrderResponse = service.criarPedido(customerOrder);
        return ResponseEntity.ok(customerOrderResponse);
    }

    @DeleteMapping(value = "api/customerOrders/{idOrder}")
    public ResponseEntity<CustomerOrderResponse> deletarPedido(@PathVariable Integer idOrder) {
        var customerOrder = service.deletarPedido(idOrder);
        return ResponseEntity.ok(customerOrder);
    }
}
