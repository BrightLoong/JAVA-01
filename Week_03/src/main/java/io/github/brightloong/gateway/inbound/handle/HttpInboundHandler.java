package io.github.brightloong.gateway.inbound.handle;

import io.github.brightloong.gateway.config.ServerConfiguration;
import io.github.brightloong.gateway.outbound.HttpOutboundHandle;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author BrightLoong
 * @date 2021/1/22 15:40
 * @description
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final ServerConfiguration serverConfiguration;

    private final HttpOutboundHandle httpOutboundHandle;


    public HttpInboundHandler(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
        this.httpOutboundHandle = new HttpOutboundHandle(serverConfiguration);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }
        FullHttpRequest request = (FullHttpRequest) msg;
        httpOutboundHandle.handle(request, ctx);
    }
}
