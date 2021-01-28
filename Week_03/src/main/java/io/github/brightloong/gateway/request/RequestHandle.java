package io.github.brightloong.gateway.request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author BrightLoong
 * @date 2021/1/28 18:00
 * @description
 */
public interface RequestHandle {
    void getRequest(FullHttpRequest inbound, ChannelHandlerContext ctx, String url);
}
