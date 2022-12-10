package uk.sky.functional_endpoints.dto;

import reactor.core.publisher.Mono;

public class Runner {
    public static void main(String[] args) {
//      The code in the callback gets executed before
//        topClass().subscribe();
    }


    public static Mono<String> bottomClass(){
        return Mono.fromCallable(() -> {
            System.out.println("bottom class");
            return "abdullah";
        });
    }

    public static Mono<String> middleClass(){
        return bottomClass().map(name -> {
            System.out.println("middle class");
            return name  + " is a software engineer";
        });
    }

    public static Mono<String> topClass(){
        return middleClass().map(message -> {
            System.out.println("top class");
            return message + " and lives in london";
        });
    }


}
