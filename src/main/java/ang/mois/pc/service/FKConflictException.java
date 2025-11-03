package ang.mois.pc.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FKConflictException extends RuntimeException {
    public FKConflictException(String message) {
        super(message);
    }
}
