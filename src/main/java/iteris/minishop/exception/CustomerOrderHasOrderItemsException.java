package iteris.minishop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomerOrderHasOrderItemsException extends RuntimeException{
    public CustomerOrderHasOrderItemsException(String message){
        super(message);
    }
}