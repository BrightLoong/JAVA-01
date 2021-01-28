package io.github.brightloong.gateway.filter.impl;

import io.github.brightloong.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author BrightLoong
 * @date 2021/1/28 18:06
 * @description
 */
public class AddVersionRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().add("GATEWARY_VERSION", "1.0");
    }
}
