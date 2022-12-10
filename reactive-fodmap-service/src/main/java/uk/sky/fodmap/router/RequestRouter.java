package uk.sky.fodmap.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.handler.FodmapHandler;

@Configuration
public class RequestRouter {

    @Autowired
    private FodmapHandler fodmapHandler;

    @Bean
    public RouterFunction<EntityResponse<Flux<FodmapItem>>> getVegetables(){
        return RouterFunctions.route(RequestPredicates.GET("/entitlements"), request -> fodmapHandler.getVegetables(request));
    }

    @Bean
    public RouterFunction<ServerResponse> getError(){
        return RouterFunctions.route(RequestPredicates.GET("/error"), request -> fodmapHandler.getError(request));
    }

    // This mapper has classes which pass context down
    @Bean
    public RouterFunction<ServerResponse> newContext(){
        return RouterFunctions.route().GET("/new/context", request -> fodmapHandler.newContext(request)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> oldContext(){
        return RouterFunctions.route().GET("/old/context", request -> fodmapHandler.oldContext(request)).build();
    }


}
