package io.github.brightloong.bringt.rpc.proxy;

import com.alibaba.fastjson.JSON;
import io.github.brightloong.bringt.rpc.exception.RpcException;
import io.github.brightloong.bringt.rpc.remoting.dto.RpcRequest;
import io.github.brightloong.bringt.rpc.remoting.dto.RpcResponse;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author BrightLoong
 * @date 2021/3/20 14:58
 * @description
 */
public class HttpInvocationHandler implements InvocationHandler {

    private static final int CONNECTION_TIME_OUT = 2000;//连接超时时间

    private static final int SOCKET_TIME_OUT = 2000;//读写超时时间

    private static final int MAX_IDLE_CONNECTIONS = 30;// 空闲连接数

    private static final long KEEP_ALLIVE_TIME = 60000L;//保持连接时间

    private static final ConnectionPool connectionPool = new ConnectionPool(MAX_IDLE_CONNECTIONS,KEEP_ALLIVE_TIME, TimeUnit.MILLISECONDS);

    private static final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .readTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
            .connectionPool(connectionPool)
            .retryOnConnectionFailure(true)
            .connectTimeout(CONNECTION_TIME_OUT,TimeUnit.MILLISECONDS)
            .build();

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");


    private final Class<?> serviceClass;

    private final String url;

    public <T> HttpInvocationHandler(Class<T> serviceClass, String url) {
        this.serviceClass = serviceClass;
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setServiceClass(this.serviceClass.getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        RpcResponse rpcResponse = post(request, url);
        return rpcResponse.getResult();
    }
    
    public RpcResponse post(RpcRequest rpcRequest, String url) {
         Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, JSON.toJSONString(rpcRequest)))
                .build();
        String response;
        try {
             response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
           throw new RpcException(RpcException.NETWORK_EXCEPTION, "网络异常", e);
        }
        return JSON.parseObject(response, RpcResponse.class);
    }
}
