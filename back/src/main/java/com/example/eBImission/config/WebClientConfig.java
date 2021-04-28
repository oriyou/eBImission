package com.example.eBImission.config;

import com.example.eBImission.handler.HttpLoggingHandler;
import com.example.eBImission.utils.ThrowingConsumer;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        // in-memmory buffer 값은 default로 256KB이다. 256KB보다 큰 HTTP 메시지를 처리하기 위해
        // ExchangeStrategies.builder() 를 통해 값을 늘러줘야 한다.
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                                                .codecs(configurer -> configurer.defaultCodecs()
                                                        .maxInMemorySize(1024*1024*50))
                                                .build();
        // Logging
        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));

        //
        return WebClient.builder()
                .clientConnector(
                        // HttpClient를 변경하거나 ConnectionTimeOut과 같은 설정값을 변경하려면
                        // WebClient.builder().clientConnector()를 통해 Netty의 HttpClient를 직접 설정해줘야 한다.
                        new ReactorClientHttpConnector(
                                HttpClient
                                    .create()
                                    // HTTPS 인증서를 검증하고 않고 바로 접속하는 설정과
                                    // (ThrowingConsumer : Stream 처리에서 Exception 처리를 위한 Util)
                                    .secure(
                                            ThrowingConsumer.unchecked(
                                                    sslContextSpec -> sslContextSpec.sslContext(
                                                                SslContextBuilder.forClient().
                                                                        trustManager(InsecureTrustManagerFactory.INSTANCE).build()
                                                            )
                                                    )
                                            )
                                    // TCP 연결시 ConnectionTimeOut, ReadTimeOut, WriteTimeOut을 적용하는 설정을 추가하였다.
                                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 8000)
                                    .doOnConnected(conn -> conn.addHandler(new ReadTimeoutHandler(180))
                                                                .addHandler(new WriteTimeoutHandler(180))
                                    )

                                     // HttpLoggingHandler
//                                    .doOnRequest((request, connection) -> {
//                                        connection.addHandler(new HttpLoggingHandler());
//                                    })
                        )
                )
                .exchangeStrategies(exchangeStrategies)
                // Client Filters
                // Request 또는 Response 데이터에 대한 조작을 하거나 추가 작업을 하기 위해서는
                // WebClient.builder().filter() 메소드를 이용해야 한다.
                // ExchangeFilterFunction.ofRequestProcessor() 와 ExchangeFilterFunction.ofRequestProcessor() 를 통해
                // clientRequest와 clientResponse를 변경하거나 출력할 수 있다.
                // Request / Response header 를 출력하는 설정
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                            return Mono.just(clientRequest);
                        }
                ))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                        clientResponse -> {
                            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                            return Mono.just(clientResponse);
                        }
                ))
                .defaultHeader("uesr-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
                .build();
    }
}
