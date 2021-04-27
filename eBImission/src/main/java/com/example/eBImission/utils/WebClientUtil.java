package com.example.eBImission.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private final WebClient webClient;

    public <T> WebClient.ResponseSpec postRequest(String uri, Publisher<T> publisher, Class<T> bodyType) {

        Flux.from(publisher).subscribe(e -> log.debug("RequestBody: {}", e.toString()));

        return webClient.mutate()
                .build()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(publisher, bodyType)
                .retrieve();
    }
}
