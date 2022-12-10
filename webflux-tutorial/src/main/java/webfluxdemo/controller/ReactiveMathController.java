package webfluxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webfluxdemo.dto.MultiplyRequestDto;
import webfluxdemo.dto.Response;
import webfluxdemo.service.ReactiveMathService;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable("input") int input){
        return reactiveMathService.getSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable("input") int input){
        return reactiveMathService.getTable(input); // delivers the response after all items have been emitted and compiled into list
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // adds the value to the flux returned as they are emitted
    public Flux<Response> multiplicationTableStream(@PathVariable("input") int input){
        return reactiveMathService.getTable(input); // check this on browser not postman
    }

    @PostMapping("/multiply") // the reading of he request body needs to be done non blocking way. the type need to be encapsulated inside publisher type
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> dto){ // cannot make type Mono
        return reactiveMathService.multiply(dto);
    }
}
