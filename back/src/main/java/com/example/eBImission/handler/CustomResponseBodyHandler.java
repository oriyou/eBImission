package com.example.eBImission.handler;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;

import java.util.List;

public class CustomResponseBodyHandler extends ResponseBodyResultHandler {

    public CustomResponseBodyHandler(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver) {
        super(writers, resolver);
    }
}
