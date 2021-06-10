package iteris.minishop.controller;

import iteris.minishop.domain.dto.OrderItemCreateRequest;
import iteris.minishop.domain.dto.OrderItemResponse;
import iteris.minishop.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderItemsController {
    private final OrderItemsService ordersService;

    @Autowired
    public OrderItemsController(OrderItemsService ordersService){
        this.ordersService = ordersService;
    }

    @GetMapping("api/orderItems")
    public ResponseEntity<List<OrderItemResponse>> listarOrderItems(){
        var orderItems = ordersService.listarTodos();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("api/orderItems/{id}")
    public ResponseEntity<OrderItemResponse> listarPorId(@PathVariable Integer id){
        var orderItem = ordersService.listarPorId(id);
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping("api/orderItems")
    public ResponseEntity<OrderItemResponse> cadastrarNovo(@RequestBody @Valid OrderItemCreateRequest orderItemCreateRequest){
        var orderItem = ordersService.criarNovo(orderItemCreateRequest);
        return ResponseEntity.ok(orderItem);
    }

    @DeleteMapping("api/orderItems/{id}")
    public ResponseEntity<OrderItemResponse> deletarOrderItem(@PathVariable Integer id){
        var orderItem = ordersService.deletar(id);
        return ResponseEntity.ok(orderItem);
    }
}
