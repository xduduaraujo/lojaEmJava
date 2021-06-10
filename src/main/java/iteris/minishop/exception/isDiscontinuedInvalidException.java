package iteris.minishop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class isDiscontinuedInvalidException extends RuntimeException{
    public isDiscontinuedInvalidException(String message){
        super(message);
    }
}
