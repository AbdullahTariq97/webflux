package webfluxdemo.exception;

public class InputValidationResponse {

    private int errorCode;
    private String input;
    private String message;

    public InputValidationResponse() {
    }

    public InputValidationResponse(int errorCode, String input, String message) {
        this.errorCode = errorCode;
        this.input = input;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
