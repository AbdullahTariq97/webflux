package uk.sky.fodmap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class WebfluxContextTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void stepVerifierTest(){
        Mono<String> response = webClient.get()
                .uri("/context")
                .retrieve()
                .bodyToMono(String.class);

        StepVerifier.create(response).expectNext("Happy case").verifyComplete();
    }
}
