package io.github.brightloong.gateway.inbound;

import io.github.brightloong.gateway.config.ServerConfiguration;
import io.github.brightloong.gateway.inbound.handle.HttpInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author BrightLoong
 * @date 2021/1/22 15:37
 * @description
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private final ServerConfiguration serverConfiguration;

    public HttpInboundInitializer(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        //http消息解码
        p.addLast(new HttpServerCodec());
        //将消息转化为单一的FullHttpRequest或FullHttpResponse
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        //具体的处理handle
        p.addLast(new HttpInboundHandler(serverConfiguration));
    }
}
