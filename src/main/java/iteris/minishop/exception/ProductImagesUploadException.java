package iteris.minishop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.MULTI_STATUS)
public class ProductImagesUploadException extends RuntimeException{
    public ProductImagesUploadException(String message){
        super(message);
    }
}
