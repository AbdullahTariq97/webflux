package uk.sky.assignment.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SquareService {

    public Mono<ResponseEntity<Integer>> getSquare(int value){
        int square = value * value;
        ResponseEntity<Integer> responseEntity= ResponseEntity.ok(square);
        return Mono.just(responseEntity);
    }
}
