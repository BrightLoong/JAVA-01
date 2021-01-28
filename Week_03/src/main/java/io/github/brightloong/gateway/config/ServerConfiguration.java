package io.github.brightloong.gateway.config;

import io.github.brightloong.gateway.filter.HttpRequestFilter;
import io.github.brightloong.gateway.filter.HttpResponseFilter;
import io.github.brightloong.gateway.router.HttpEndpointRouter;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/1/22 17:37
 * @description
 */
public class ServerConfiguration {

    private HttpResponseFilter httpResponseFilter;

    private HttpRequestFilter httpRequestFilter;

    private HttpEndpointRouter router;

    private List<String> endpoints;

    private Integer serverPort;

    public ServerConfiguration(Integer serverPort, List<String> endpoints, HttpEndpointRouter router) {
        this.serverPort = serverPort;
        this.endpoints = endpoints;
        this.router = router;
    }

    public HttpResponseFilter getHttpResponseFilter() {
        return httpResponseFilter;
    }

    public void setHttpResponseFilter(HttpResponseFilter httpResponseFilter) {
        this.httpResponseFilter = httpResponseFilter;
    }

    public HttpRequestFilter getHttpRequestFilter() {
        return httpRequestFilter;
    }

    public void setHttpRequestFilter(HttpRequestFilter httpRequestFilter) {
        this.httpRequestFilter = httpRequestFilter;
    }

    public HttpEndpointRouter getRouter() {
        return router;
    }

    public void setRouter(HttpEndpointRouter router) {
        this.router = router;
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }
}
