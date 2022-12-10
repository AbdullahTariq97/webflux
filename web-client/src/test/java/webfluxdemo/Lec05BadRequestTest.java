package webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import webfluxdemo.dto.Response;

public class Lec05BadRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest(){
        Mono<Response> responseMono = webClient.get()
                .uri("reactive-math/square/{input}/throw", 5) // 5 outside acceptable range
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(System.out::println);

        StepVerifier.create(responseMono).verifyError(WebClientResponseException.BadRequest.class);
    }
}
