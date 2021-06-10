package iteris.minishop.service;

import iteris.minishop.domain.dto.CustomerCreateRequest;
import iteris.minishop.domain.dto.CustomerFilterRequest;
import iteris.minishop.domain.dto.CustomerResponse;
import iteris.minishop.domain.entity.Customer;
import iteris.minishop.exception.CustomerHasOrderException;
import iteris.minishop.exception.CustomerNotFoundException;
import iteris.minishop.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersService {

    private final CustomersRepository repository;

    @Autowired
    public CustomersService(CustomersRepository repository) {

        this.repository = repository;
    }


    public CustomerResponse criarCliente(CustomerCreateRequest customerCreateRequest) {

        //tudo certo, só cadastrar
        var novoCliente = new Customer();
        novoCliente.setFirstName(customerCreateRequest.getFirstName());
        novoCliente.setLastName(customerCreateRequest.getLastName());
        novoCliente.setCity(customerCreateRequest.getCity());
        novoCliente.setCountry(customerCreateRequest.getCountry());
        novoCliente.setPhone(customerCreateRequest.getPhone());

        var clienteSalvo = repository.save(novoCliente);

        return new CustomerResponse(
                clienteSalvo.getIdCustomer(),
                clienteSalvo.getFirstName(),
                clienteSalvo.getLastName(),
                clienteSalvo.getCity(),
                clienteSalvo.getCountry(),
                clienteSalvo.getPhone()
        );

    }



    public List<CustomerResponse> listar(CustomerFilterRequest filter) {
        var resultado = repository.listarComFiltroNativo(filter.getCountry());
        var listaDeClientes = resultado.stream().map(cliente -> {
            return new CustomerResponse(
                    cliente.getIdCustomer(),
                    cliente.getFirstName(),
                    cliente.getLastName(),
                    cliente.getCity(),
                    cliente.getCountry(),
                    cliente.getPhone()
            );
        }
        ).collect(Collectors.toList());
        return listaDeClientes;
    }


    public CustomerResponse buscarPorId(Integer idCustomer) {
        var clienteEncontrado = repository.findById(idCustomer);

        if (clienteEncontrado.isEmpty()) {
            throw new CustomerNotFoundException("Cliente: " + idCustomer + " não encontrado.");
        }

        var clienteSalvo = clienteEncontrado.get();
        return new CustomerResponse(
                clienteSalvo.getIdCustomer(),
                clienteSalvo.getFirstName(),
                clienteSalvo.getLastName(),
                clienteSalvo.getCity(),
                clienteSalvo.getCountry(),
                clienteSalvo.getPhone()
        );
    }



    public CustomerResponse deletarCliente(Integer idCustomer) {
        var clienteEncontrado = repository.findById(idCustomer);

        if (clienteEncontrado.isEmpty()) {
            throw new CustomerNotFoundException("Cliente: " + idCustomer +" não encontrado.");
        }
        var clienteSalvo = clienteEncontrado.get();

        if (!clienteSalvo.getCustomerOrders().isEmpty()){
            throw new CustomerHasOrderException();
        }

        repository.delete(clienteSalvo);

        return new CustomerResponse(
                clienteSalvo.getIdCustomer(),
                clienteSalvo.getFirstName(),
                clienteSalvo.getLastName(),
                clienteSalvo.getCity(),
                clienteSalvo.getCountry(),
                clienteSalvo.getPhone()
        );
    }

}
