package uk.sky.fodmap.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.service.FodmapService;

@Service
public class FodmapHandler {

    @Autowired
    private FodmapService fodmapService;

//    A handler must return Mono<T> where T extends server response
    public Mono<EntityResponse<Flux<FodmapItem>>> getVegetables(ServerRequest serverRequest){
        Flux<FodmapItem> vegetables = fodmapService.getVegetables();
        return EntityResponse.fromPublisher(vegetables, FodmapItem.class).contentType(MediaType.TEXT_EVENT_STREAM).build();
    }

    public Mono<ServerResponse> newContext(ServerRequest request) {
        Mono<String> downstreamResponse = fodmapService.getPublisher().contextWrite(context -> context.put("someKey","someVal"));
        return ServerResponse.ok().body(downstreamResponse, String.class);
    }

    public Mono<ServerResponse> oldContext(ServerRequest request) {
        Mono<String> downstreamResponse = fodmapService.getAnotherPublisher().subscriberContext(context -> context.put("key1","value1"));
        return ServerResponse.ok().body(downstreamResponse, String.class);
    }

    public Mono<ServerResponse> getError(ServerRequest request) {
        return Mono.error(new RuntimeException("some message"));
    }
}
