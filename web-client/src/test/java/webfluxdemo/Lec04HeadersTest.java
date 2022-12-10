package webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import webfluxdemo.dto.MultiplyRequestDto;
import webfluxdemo.dto.Response;

public class Lec04HeadersTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest(){
        Mono<Response> responseMono = webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(getMultiplyRequestBody(5,2))
                .headers(h -> h.set("someKey", "someVal"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono).expectNextCount(1).verifyComplete();
    }

    public MultiplyRequestDto getMultiplyRequestBody(int first, int second){
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(first);
        dto.setSecond(second);
        return dto;
    }
}
