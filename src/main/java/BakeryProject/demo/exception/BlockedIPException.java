package BakeryProject.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BlockedIPException extends RuntimeException{

    public BlockedIPException(String message) {
        super(message);
    }
}
