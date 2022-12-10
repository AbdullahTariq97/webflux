package uk.sky.fodmap.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;
import uk.sky.fodmap.dto.FodmapItem;

import static org.assertj.core.api.Assertions.assertThat;

public class FodmapServiceTest {

    @Test
    public void shouldReturnFluxOfVegetables(){
        FodmapService fodmapService = new FodmapService();

        // When
        Flux<FodmapItem> vegetables = fodmapService.getVegetables();

        // Then
        StepVerifier.create(vegetables.map(FodmapItem::getVegetableName)).expectNext("carrot", "aubergine","potato","cabbage").verifyComplete();
    }

    @Test
    public void givenKeyInContextPassedIn_shouldReturnSuccessMono(){
        FodmapService fodmapService = new FodmapService();
        StepVerifierOptions context = StepVerifierOptions.create().withInitialContext(Context.of("key", "initial value"));

        StepVerifier.create(fodmapService.emmitUsingContext(), context).expectNext("key present").verifyComplete();
    }

    @Test
    public void givenKeyNotInContext_shouldEmitErrorSignal() {
        FodmapService fodmapService = new FodmapService();
        StepVerifier.create(fodmapService.emmitUsingContext()).expectError().verify();
        StepVerifier.create(fodmapService.emmitUsingContext()).expectError(RuntimeException.class).verify();
        StepVerifier.create(fodmapService.emmitUsingContext()).expectErrorSatisfies(exception -> assertThat(exception.getMessage()).isEqualTo("no key")).verify();
    }
}
