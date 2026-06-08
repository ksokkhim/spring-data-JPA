package co.istad.ecommerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AppException{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleValidationException(
            ResponseStatusException e
    ) {

        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .status(false)
                .code(e.getStatusCode().value())
                .message("Service exception errored")
                .errors(e.getReason())
                .build();

        return ResponseEntity.status(e.getStatusCode())
                .body(errorResponse);

    }




}



