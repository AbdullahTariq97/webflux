package webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import webfluxdemo.dto.Response;

public class Lec02GetMultiResponseTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest(){
        Flux<Response> responseFlux = webClient.get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux).expectNextCount(10).verifyComplete();
    }

    @Test
    public void fluxStreamTest(){
        Flux<Response> responseFlux = webClient.get()
                .uri("reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux).expectNextCount(10).verifyComplete();
    }
}
