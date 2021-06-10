package iteris.minishop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomerHasOrderException extends RuntimeException {

    public CustomerHasOrderException() {

        super("Não é possível excluir o Cliente, existem pedidos atrelados ao mesmo.");
    }

}
