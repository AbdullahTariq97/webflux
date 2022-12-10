package uk.sky.functional_endpoints.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uk.sky.functional_endpoints.exception.InputValidationResponse;
import uk.sky.functional_endpoints.handlers.RequestHandler;

import java.util.function.BiFunction;


@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    // make sure you import from the reactive package
    // Router (maps url to handler) -> Handler -> Services
    // single route method can provide one-to-one mappings for url and handlers
    // accepts server request must return server response

    @Bean
    public RouterFunction<ServerResponse> highLevelRouterFunction(){
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction).build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
                .GET("square/{input}", request -> requestHandler.squareHandler(request)) //both pattern and predicate need to match
                .GET("table/{input}", request -> requestHandler.tableHandler(request))
                .GET("table/{input}/stream", request -> requestHandler.tableStreamHandler(request))
                .POST("multiply", request -> requestHandler.multiplyHandler(request))
                .GET("square/{input}/validation", request -> requestHandler.squareHandlerWithValidation(request))
                .GET("calculator/{input1}/{input2}", request -> requestHandler.operationHandler(request))
                .onError(RuntimeException.class, getExceptionHandler()) // if there is an error signal from downstream this lambda expression is executed
                .build();
    }

    public BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> getExceptionHandler(){
        return (throwable, serverRequest) -> {
            int input = Integer.parseInt(serverRequest.pathVariable("input"));

            InputValidationResponse inputValidationResponse = InputValidationResponse.builder()
                    .message(throwable.getMessage())
                    .input(input)
                    .build();
            return ServerResponse.badRequest().bodyValue(inputValidationResponse);
        };
    }
}
