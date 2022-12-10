package webfluxdemo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidValidationException.class)
    public ResponseEntity<InputValidationResponse> handleRuntimeException(InvalidValidationException runtimeException){
        InputValidationResponse validationResponse = new InputValidationResponse(
                1001,
                runtimeException.getMessage(),
                "The input was outside range 10<=x<=20");
        return ResponseEntity.badRequest().body(validationResponse);
    }
}
