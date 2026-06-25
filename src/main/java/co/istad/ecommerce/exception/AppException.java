package co.istad.ecommerce.exception;



import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class AppException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handlerServiceException(
            ResponseStatusException ex
    ){
        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .status(false)
                .code(Integer.valueOf(ex.getStatusCode().toString()))
                .message("Service exception errored")
                .errors(ex.getReason())
                .build();
        return ResponseEntity.status(ex.getStatusCode())
                .body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse<?> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        log.error("Validation Exception happened");

        List<co.istad.ecommerce.exception.FieldErrorResponse> fields = new ArrayList<>();

        e.getFieldErrors()
                .forEach(fieldError -> {
                    FieldErrorResponse field = FieldErrorResponse.builder()
                            .field(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                            .build();
                    fields.add(field);
                });

        return ErrorResponse.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation is errored")
                .errors(fields)
                .build();
    }
}

