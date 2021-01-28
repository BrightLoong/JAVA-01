package io.github.brightloong.gateway;

import com.google.common.collect.Lists;
import io.github.brightloong.gateway.config.ServerConfiguration;
import io.github.brightloong.gateway.filter.impl.AddVersionRequestFilter;
import io.github.brightloong.gateway.filter.impl.CookeieResponseFilter;
import io.github.brightloong.gateway.filter.impl.HttpRequestFilters;
import io.github.brightloong.gateway.filter.impl.HttpResponseFilters;
import io.github.brightloong.gateway.router.impl.RandomHttpEndpointRouter;
import io.github.brightloong.gateway.server.HttpServer;

/**
 * @author BrightLoong
 * @date 2021/1/22 15:16
 * @description 启动程序
 */
public class NettyServerApplication {


    public static void main(String[] args) {
        ServerConfiguration serverConfiguration = new ServerConfiguration(8808, Lists.newArrayList("http://localhost:8801"), new RandomHttpEndpointRouter());
        serverConfiguration.setHttpRequestFilter(new HttpRequestFilters(Lists.newArrayList(new AddVersionRequestFilter())));
        serverConfiguration.setHttpResponseFilter(new HttpResponseFilters(Lists.newArrayList(new CookeieResponseFilter())));
        HttpServer httpServer = new HttpServer(serverConfiguration);
        try {
            httpServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
