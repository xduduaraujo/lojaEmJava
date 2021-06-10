package iteris.minishop.service;
import iteris.minishop.domain.dto.*;
import iteris.minishop.domain.entity.CustomerOrder;
import iteris.minishop.exception.CustomerNotFoundException;
import iteris.minishop.exception.CustomerOrderHasOrderItemsException;
import iteris.minishop.exception.CustomerOrderNotFoundException;
import iteris.minishop.repository.CustomerOrdersRepository;
import iteris.minishop.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerOrdersService {

    private final CustomerOrdersRepository repository;
    private final CustomersRepository customersRepository;

    @Autowired
    public CustomerOrdersService(CustomerOrdersRepository repository, CustomersRepository customersRepository)
    {   this.repository = repository;
        this.customersRepository = customersRepository;
    }

    public CustomerOrderResponse criarPedido(CustomerOrderCreateRequest customerOrderCreateRequest) {
        var clienteId = customerOrderCreateRequest.getCustomerId();
        var cliente = customersRepository.findById(clienteId);

        if(cliente.isEmpty()){
            throw new CustomerNotFoundException("Cliente: " + clienteId + " não encontrado.");
        }
        var novoPedido = new CustomerOrder();
        novoPedido.setOrderDate(customerOrderCreateRequest.getOrderDate());
        novoPedido.setOrderNumber(customerOrderCreateRequest.getOrderNumber());
        novoPedido.setTotalAmount(customerOrderCreateRequest.getTotalAmount());
        novoPedido.setCustomer(cliente.get());

        var pedidoSalvo = repository.save(novoPedido);

        return new CustomerOrderResponse(
                pedidoSalvo.getIdOrder(),
                pedidoSalvo.getOrderDate(),
                pedidoSalvo.getOrderNumber(),
                pedidoSalvo.getTotalAmount(),
                pedidoSalvo.getCustomer()
        );
    }

    public List<CustomerOrderResponse> listar() {
        var retorno= repository.findAll();
        var listaDePedidos = retorno.stream().map(
                pedido ->{
                    return new CustomerOrderResponse(
                            pedido.getIdOrder(),
                            pedido.getOrderDate(),
                            pedido.getOrderNumber(),
                            pedido.getTotalAmount(),
                            pedido.getCustomer());
                }
        ).collect(Collectors.toList());
        return listaDePedidos;
    }

    public CustomerOrderResponse buscarPorId(Integer idOrder) {
        var pedidoEncontrado = repository.findById(idOrder);
        if (pedidoEncontrado.isEmpty()) {
            throw new CustomerOrderNotFoundException("Pedido: " + idOrder + " não encontrado.");
        }
        var pedidoSalvo =  pedidoEncontrado.get();
        return new CustomerOrderResponse(
                pedidoSalvo.getIdOrder(),
                pedidoSalvo.getOrderDate(),
                pedidoSalvo.getOrderNumber(),
                pedidoSalvo.getTotalAmount(),
                pedidoSalvo.getCustomer()
        );

    }

    public CustomerOrderResponse deletarPedido(Integer idOrder) {
        var pedidoEncontrado = repository.findById(idOrder);

        if (pedidoEncontrado.isEmpty()) {
            throw new CustomerOrderNotFoundException("Pedido: " + idOrder + " não encontrado.");
        }

        var pedido = pedidoEncontrado.get();

        if (!pedido.getOrderItems().isEmpty()){
            throw new CustomerOrderHasOrderItemsException("Pedido: " + idOrder + " tem itens registrados, sendo assim não pode ser apagado.");
        }

        repository.delete(pedido);

        return new CustomerOrderResponse(
                pedido.getIdOrder(),
                pedido.getOrderDate(),
                pedido.getOrderNumber(),
                pedido.getTotalAmount(),
                pedido.getCustomer()
        );
    }
}
