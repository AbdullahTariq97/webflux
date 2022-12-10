package uk.sky.fodmap.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.service.FodmapService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class FodmapHandlerTest {

    private final Flux<FodmapItem> FLUX = Flux.just("carrot", "aubergine","potato","cabbage").map(FodmapItem::new);

    @Mock
    private FodmapService fodmapService;

    @InjectMocks
    private FodmapHandler fodmapHandler;

    @Test
    public void givenRequestPassedIn_whenGetVegetablesMethodInvoked_shouldReturnEntityResponse(){
        // Given
        ServerRequest mockRequest = mock(ServerRequest.class);
        when(fodmapService.getVegetables()).thenReturn(FLUX);

        // When
        Mono<EntityResponse<Flux<FodmapItem>>> vegetables = fodmapHandler.getVegetables(mockRequest);

        // Then
        StepVerifier.create(vegetables)
                .assertNext(entityResponse -> {
                    assertThat(entityResponse.statusCode()).isEqualTo(HttpStatus.OK);
                    Flux<FodmapItem> fluxResponse = entityResponse.entity();
                    StepVerifier.create(fluxResponse.map(FodmapItem::getVegetableName)).
                            expectNext("carrot", "aubergine","potato","cabbage").verifyComplete();
                }).verifyComplete();
    }
}
