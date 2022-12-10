package uk.sky.assignment.exception;

import lombok.Data;

@Data
public class InputValidationResponse {

    private int errorCode;
    private String input;
    private String message;

}
