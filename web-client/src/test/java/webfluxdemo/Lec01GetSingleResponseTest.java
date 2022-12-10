package webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import webfluxdemo.dto.Response;

public class Lec01GetSingleResponseTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest(){
        Response response = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block(); // blocks thread executing tests. waits for the response
        System.out.println(response);
    }

    @Test
    public void stepVerifierTest(){
        Mono<Response> response = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class);
        StepVerifier.create(response).expectNextMatches(item -> item.getOutput() == 25).verifyComplete();
    }
}
