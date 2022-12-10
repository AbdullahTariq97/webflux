package uk.sky.functional_endpoints.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.sky.functional_endpoints.dto.MultiplyRequestDto;
import uk.sky.functional_endpoints.dto.Response;
import uk.sky.functional_endpoints.services.ReactiveMathService;

@Service
public class RequestHandler {

    @Autowired
    private ReactiveMathService reactiveMathService;

    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<Response> response = reactiveMathService.getSquare(input);
        return ServerResponse.ok().body(response,Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> response = reactiveMathService.getTable(input);
        return ServerResponse.ok().body(response,Response.class);
    }

    // postman does not support streaming response. chrome must be used to make call to this endpoint
    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> response = reactiveMathService.getTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM) // this sends the response as it is emitted
                .body(response,Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        Mono<MultiplyRequestDto> requestBody = request.bodyToMono(MultiplyRequestDto.class); // the result after the request body has finish arriving
        Mono<Response> responseMono = reactiveMathService.multiply(requestBody);
        return ServerResponse.ok()
                .body(responseMono,Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request) {
        Integer input = Integer.parseInt(request.pathVariable("input"));
        if(input < 10 || input > 20){
            return Mono.error(() -> new RuntimeException("The input " + input + " is outside acceptable range of values"));
        }

        Mono<Response> response = reactiveMathService.getSquare(input);
        return ServerResponse.ok().body(response,Response.class);
    }

    public Mono<ServerResponse> operationHandler(ServerRequest request) {
        String operationType = request.headers().firstHeader("OP");
        Integer input1 = Integer.parseInt(request.pathVariable("input1"));
        Integer input2 = Integer.parseInt(request.pathVariable("input2"));
        Mono<Response> responseMono = reactiveMathService.operate(operationType, input1, input2);
        return ServerResponse.ok().body(responseMono,Response.class);
    }
}
