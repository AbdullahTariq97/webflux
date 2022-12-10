package webfluxdemo.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webfluxdemo.SleepUtil;
import webfluxdemo.dto.MultiplyRequestDto;
import webfluxdemo.dto.Response;

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

    }}
