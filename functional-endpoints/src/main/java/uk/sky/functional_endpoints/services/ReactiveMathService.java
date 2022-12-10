package uk.sky.functional_endpoints.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.sky.functional_endpoints.dto.MultiplyRequestDto;
import uk.sky.functional_endpoints.dto.Response;
import uk.sky.functional_endpoints.util.SleepUtil;

@Service
public class ReactiveMathService {

    public Mono<Response> getSquare(int number){
        return Mono.just(number * number).map((Response::new));
    }

    public Flux<Response> getTable(int number){
        return Flux.range(1, 10)
                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("math-service processing: " + i))
                .map((val) -> new Response(val * number));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono){
        return dtoMono
                .map((requestDto) -> requestDto.getFirst() * requestDto.getSecond())
                .map(Response::new);

    }

    public Mono<Response> operate(String operationType, Integer input1, Integer input2) {
        return Mono.fromSupplier(() -> {
            int response = 0;

            if(operationType.equals("+")){
                response = input1 + input2;
            } else if (operationType.equals("-")){
                response = input1 - input2;

            } else if (operationType.equals("*")){
                response = input1 * input2;
            } else if (operationType.equals("/")){
                response = input1 / input2;
            } else {
                throw new RuntimeException();
            }

            return new Response(response);
        });
    }
}
