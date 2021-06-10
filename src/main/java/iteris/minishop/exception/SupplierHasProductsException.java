package iteris.minishop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SupplierHasProductsException extends RuntimeException {
    public SupplierHasProductsException() {
        super("O Fornecedor informado não pode ser deletado, pois possui produtos relacionados a ele.");
    }
}