package uk.sky.fodmap.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.util.Util;

import java.time.Duration;

@Service
public class FodmapService {

    public Flux<FodmapItem> getVegetables() {
        return Flux.just("carrot", "aubergine","potato","cabbage").map(FodmapItem::new).delayElements(Duration.ofSeconds(1));
    }

    public Mono<String> getPublisher() {
        return Mono.deferContextual(contextView -> { // emmit mono based on the context received
            if(contextView.get("someKey").toString().equalsIgnoreCase("thirdVal")){
                return Mono.just("happy case");
            } else {
                return Mono.error(new RuntimeException("sad case"));
            }
        })
        .contextWrite(context -> context.put("someKey","thirdVa")) // updates context received from upstream
        .transformDeferredContextual( (monos,contextView) -> { // once first mono has been emitted how to transform it based on context
            monos.subscribe(Util.subscriber()); // mono recieved from the top
            System.out.println(contextView.get("someKey").toString());
            if(contextView.get("someKey").toString().equalsIgnoreCase("anotherValue")){
                return Mono.just("middle happy case");
            }
            return Mono.error(new RuntimeException("middle sad case"));
        })
        .contextWrite(context -> {  // updates context received from upstream
            System.out.println("someKey: " + context.get("someKey").toString());
            Context newContext = context.put("someKey","anotherValue");
            System.out.println("someKey: " + newContext.get("someKey").toString());
            return newContext;
        });
    };

    public Mono<String> getAnotherPublisher() {
        return Mono.subscriberContext()
                .flatMap(context -> {
                    System.out.println(context.get("key1").toString());
                    return Mono.just(context.put("key1", "value2"));
                }).map(context -> {
                    System.out.println(context.get("key1").toString());
                    return context.put("key1", "value3");
                })
                .flatMap(context -> {
            if(context.hasKey("key1")){
                System.out.println(context.get("key1").toString());
                if(context.get("key1").toString().equalsIgnoreCase("value3")){
                    return Mono.just("correct context received");
                }
            }
            return Mono.error(new RuntimeException("wrong context received"));
        });
    }

    public Mono<String> emmitUsingContext(){
        return Mono.subscriberContext()
                .flatMap(context -> {
                    if(context.hasKey("key")){
                        return Mono.just("key present");
                    } else {
                        return Mono.error(new RuntimeException("no key"));
                    }
                });
    }



    public Mono<String> testMethod(String message){
        return Mono.just(message.toUpperCase());
    }
}
