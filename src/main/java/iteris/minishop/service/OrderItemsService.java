package iteris.minishop.service;

import iteris.minishop.domain.dto.OrderItemCreateRequest;
import iteris.minishop.domain.dto.OrderItemResponse;
import iteris.minishop.domain.entity.OrderItem;
import iteris.minishop.exception.CustomerOrderNotFoundException;
import iteris.minishop.exception.OrderItemNotFoundException;
import iteris.minishop.exception.ProductNotFoundException;
import iteris.minishop.repository.CustomerOrdersRepository;
import iteris.minishop.repository.OrderItemsRepository;
import iteris.minishop.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;
    private final CustomerOrdersRepository customerOrdersRepository;
    private final ProductsRepository productsRepository;

    public OrderItemsService(OrderItemsRepository orderItemsRepository, CustomerOrdersRepository customerOrdersRepository, ProductsRepository productsRepository){
        this.orderItemsRepository = orderItemsRepository;
        this.customerOrdersRepository = customerOrdersRepository;
        this.productsRepository = productsRepository;
    }

    public List<OrderItemResponse> listarTodos(){
        var resultado = orderItemsRepository.findAll();



        var listaDeOrderItems = resultado.stream().map(orderItem ->{
            return new OrderItemResponse(
                    orderItem.getIdOrderItem(),
                    orderItem.getUnitPrice(),
                    orderItem.getQuantity(),
                    orderItem.getCustomerOrder(),
                    orderItem.getProduct()
            );
        }).collect(Collectors.toList());

        return listaDeOrderItems;
    }

    public OrderItemResponse listarPorId(Integer id){
        var resultado = orderItemsRepository.findById(id);

        if(resultado.isEmpty()){
            throw new OrderItemNotFoundException("OrderItem: " + id +" não encontrada.");
        }

        var orderItem = resultado.get();

        return new OrderItemResponse(
                orderItem.getIdOrderItem(),
                orderItem.getUnitPrice(),
                orderItem.getQuantity(),
                orderItem.getCustomerOrder(),
                orderItem.getProduct()
        );
    }

    public OrderItemResponse criarNovo(OrderItemCreateRequest orderItem){
        var customerOrderId = orderItem.getOrderId();
        var customerOrder = customerOrdersRepository.findById(customerOrderId);


        if (customerOrder.isEmpty()) {
            throw new CustomerOrderNotFoundException("CustomerOrder: " + customerOrderId + " não foi encontrada.");
        }

        var productId = orderItem.getProductId();
        var product = productsRepository.findById(productId);

        if (product.isEmpty()){
            throw new ProductNotFoundException("Produto: " + productId + " não foi encontrado.");
        }

        var novoOrderItem = new OrderItem();
        novoOrderItem.setCustomerOrder(customerOrder.get());
        novoOrderItem.setProduct(product.get());
        novoOrderItem.setUnitPrice(orderItem.getUnitPrice());
        novoOrderItem.setQuantity(orderItem.getQuantity());

        var orderItemSalvo = orderItemsRepository.save(novoOrderItem);

        return new OrderItemResponse(
                orderItemSalvo.getIdOrderItem(),
                orderItemSalvo.getUnitPrice(),
                orderItemSalvo.getQuantity(),
                orderItemSalvo.getCustomerOrder(),
                orderItemSalvo.getProduct()
        );

    }
    public OrderItemResponse deletar(Integer id){
        var retorno = orderItemsRepository.findById(id);

        if(retorno.isEmpty()){
            throw new OrderItemNotFoundException("OrderItem: " + id +" não encontrada.");
        }

        var orderItemEncontrado = retorno.get();

        orderItemsRepository.delete(orderItemEncontrado);

        return new OrderItemResponse(
                orderItemEncontrado.getIdOrderItem(),
                orderItemEncontrado.getUnitPrice(),
                orderItemEncontrado.getQuantity(),
                orderItemEncontrado.getCustomerOrder(),
                orderItemEncontrado.getProduct()
        );
    }
}
