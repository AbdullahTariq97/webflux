package webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;

public class Lec07QueryParamsTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void queryParamsTest(){
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/jobs/search?count={count}&page={page)").build(2,3);
        Flux<Integer> integerFlux = webClient.get()
                .uri(uri) // or use overloaded method .path("job/search").query("count={count}&page={page}").build(2,3)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux).expectNext(2,3);
    }
}
