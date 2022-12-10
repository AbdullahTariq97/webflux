package webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import webfluxdemo.dto.MultiplyRequestDto;
import webfluxdemo.dto.Response;

public class Lec03PostRequestTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void postTest(){
        Mono<Response> responseMono = webClient.post()
                .uri("reactive-math/multiply")
                .bodyValue(getMultiplyRequestBody(5,2))
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(responseMono).expectNextCount(1).verifyComplete();
    }


    public MultiplyRequestDto getMultiplyRequestBody(int first, int second){
       MultiplyRequestDto dto = new MultiplyRequestDto();
       dto.setFirst(first);
       dto.setSecond(second);
       return dto;
    }
}
