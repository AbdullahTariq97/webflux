package uk.sky.assignment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<InputValidationResponse> handleRuntimeException(RuntimeException runtimeException){
        InputValidationResponse inputValidationResponse = new InputValidationResponse();
        inputValidationResponse.setMessage(runtimeException.getMessage());
        inputValidationResponse.setInput(runtimeException.getClass().getSimpleName());
        inputValidationResponse.setErrorCode(1001);
        return ResponseEntity.badRequest().body(inputValidationResponse);
    }
}
