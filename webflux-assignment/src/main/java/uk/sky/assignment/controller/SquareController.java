package uk.sky.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uk.sky.assignment.service.SquareService;

@RestController
public class SquareController {

    @Autowired
    private SquareService squareService;

    @PostMapping("/{number}/square")
    public Mono<ResponseEntity> getSquare(@PathVariable("number") Integer number){

//        The BiConsumer implementation will be used in such a way that the first parameter represents x and the second parameter represents y
        return Mono.just(number).handle((value, sink) -> {
            if(value >= 10 && value <=20) {
                sink.next(value);
            } else {
                sink.error(new RuntimeException("Input number outside required range"));
            }
        })
                .cast(Integer.class) // unwrapping value inside Mono coming .handle
                .flatMap((incomingValue) -> squareService.getSquare(incomingValue)); // the functional interface should return type R

    }
}