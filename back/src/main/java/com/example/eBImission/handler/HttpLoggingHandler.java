package com.example.eBImission.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.logging.ByteBufFormat;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpLoggingHandler extends LoggingHandler {

    @Override
    protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
        if(arg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) arg;
            return buf.toString(StandardCharsets.UTF_8);

        }
        return super.format(ctx, eventName, arg);
    }
}
