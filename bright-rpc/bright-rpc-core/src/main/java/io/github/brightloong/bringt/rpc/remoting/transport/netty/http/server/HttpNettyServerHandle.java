package io.github.brightloong.bringt.rpc.remoting.transport.netty.http.server;

import com.alibaba.fastjson.JSON;
import io.github.brightloong.bringt.rpc.provider.ServiceProvider;
import io.github.brightloong.bringt.rpc.proxy.HttpInvocationHandler;
import io.github.brightloong.bringt.rpc.proxy.ServerInvoker;
import io.github.brightloong.bringt.rpc.remoting.dto.RpcRequest;
import io.github.brightloong.bringt.rpc.remoting.dto.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang.StringUtils;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author BrightLoong
 * @date 2021/3/19 22:43
 * @description
 */
public class HttpNettyServerHandle extends ChannelInboundHandlerAdapter {

    private ServerInvoker serverInvoker;



    public HttpNettyServerHandle(ServiceProvider serviceProvider) {
        this.serverInvoker = new ServerInvoker(serviceProvider);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }
        FullHttpResponse response = null;
        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            ByteBuf jsonBuf = request.content();
            String requestJson = jsonBuf.toString(CharsetUtil.UTF_8);
            RpcRequest rpcRequest = JSON.parseObject(requestJson, RpcRequest.class);
            RpcResponse rpcResponse = serverInvoker.invok(rpcRequest);
            byte[] bytes = JSON.toJSONString(rpcResponse).getBytes();
            response  = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytes));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                if (!HttpUtil.isKeepAlive(response)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }

}
