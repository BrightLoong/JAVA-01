package io.github.brightloong.gateway.filter.impl;

import io.github.brightloong.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/1/22 16:14
 * @description
 */
public class HttpRequestFilters implements HttpRequestFilter {

    private final List<HttpRequestFilter> filterList;

    public HttpRequestFilters(List<HttpRequestFilter> filterList) {
        this.filterList = filterList;
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        if (CollectionUtils.isEmpty(filterList)) {
            return;
        }
        filterList.forEach(httpRequestFilter -> httpRequestFilter.filter(fullRequest, ctx));
    }
}
