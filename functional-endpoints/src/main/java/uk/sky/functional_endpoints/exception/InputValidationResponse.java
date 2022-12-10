package uk.sky.functional_endpoints.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InputValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
