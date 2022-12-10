package webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import webfluxdemo.dto.Response;
import webfluxdemo.exception.InputValidationResponse;

public class Lec06ExchangeTest extends BaseTest{
    @Autowired
    private WebClient webClient;

    // exchange = retrieve + additional information

    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = webClient.get()
                .uri("reactive-math/square/{input}/throw", 5) // 5 outside acceptable range
                .exchangeToMono(clientResponse -> exchange(clientResponse))
                .doOnNext(System.out::println)
                .doOnError(System.out::println);

        StepVerifier.create(responseMono).expectNextCount(1).verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse){
        if(clientResponse.rawStatusCode() == 400){
            return clientResponse.bodyToMono(InputValidationResponse.class);
        }
        return clientResponse.bodyToMono(Response.class);
    }
}
