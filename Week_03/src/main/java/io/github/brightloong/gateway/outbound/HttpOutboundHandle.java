package io.github.brightloong.gateway.outbound;

import io.github.brightloong.gateway.config.ServerConfiguration;
import io.github.brightloong.gateway.filter.HttpRequestFilter;
import io.github.brightloong.gateway.filter.HttpResponseFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author BrightLoong
 * @date 2021/1/22 16:54
 * @description
 */
public class HttpOutboundHandle {

    private static final int CONNECTION_TIME_OUT = 2000;//连接超时时间

    private static final int SOCKET_TIME_OUT = 2000;//读写超时时间

    private static final int MAX_IDLE_CONNECTIONS = 30;// 空闲连接数

    private static final long KEEP_ALLIVE_TIME = 60000L;//保持连接时间

    private static final ConnectionPool connectionPool = new ConnectionPool(MAX_IDLE_CONNECTIONS,KEEP_ALLIVE_TIME,TimeUnit.MILLISECONDS);

    private static final OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECTION_TIME_OUT,TimeUnit.MILLISECONDS)
                .build();


    private final ServerConfiguration serverConfiguration;

    private final Logger log = LoggerFactory.getLogger(HttpOutboundHandle.class);




    public HttpOutboundHandle(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;

    }

    public void handle(FullHttpRequest inbound, ChannelHandlerContext ctx) {
        String url = serverConfiguration.getRouter().route(this.serverConfiguration.getEndpoints()) + inbound.uri();
        log.info("real request url:{}", url);
        HttpRequestFilter httpRequestFilter = this.serverConfiguration.getHttpRequestFilter();
        if (Objects.nonNull(httpRequestFilter)) {
            httpRequestFilter.filter(inbound, ctx);
        }
        getRequest(inbound, ctx, url);
    }

    private void getRequest(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("failure while request:", e);
                exceptionCaught(ctx, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handleResponse(response, inbound, ctx);
            }
        });

    }

    private void handleResponse(Response endpointResponse, FullHttpRequest inbound, ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try {
            ResponseBody body = endpointResponse.body();
            if (Objects.isNull(body)) {
                response = new DefaultFullHttpResponse(HTTP_1_1, OK);
            } else {
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body.string().getBytes()));
            }
            response.headers().set("Content-Type", "application/json");
            String contentLength = endpointResponse.header("Content-Length");
            response.headers().setInt("Content-Length", StringUtils.isNotBlank(contentLength) ? Integer.parseInt(contentLength) : 0);
            //过滤
            HttpResponseFilter httpResponseFilter = serverConfiguration.getHttpResponseFilter();
            if (Objects.nonNull(httpResponseFilter)) {
                httpResponseFilter.filter(response);
            }
        } catch (Exception e) {
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
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

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
