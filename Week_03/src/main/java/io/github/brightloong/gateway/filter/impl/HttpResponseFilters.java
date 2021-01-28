package io.github.brightloong.gateway.filter.impl;

import io.github.brightloong.gateway.filter.HttpResponseFilter;
import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/1/22 16:51
 * @description
 */
public class HttpResponseFilters implements HttpResponseFilter {

    private final List<HttpResponseFilter> filterList;

    public HttpResponseFilters(List<HttpResponseFilter> filterList) {
        this.filterList = filterList;
    }

    @Override
    public void filter(FullHttpResponse response) {
        if (CollectionUtils.isEmpty(filterList)) {
            return;
        }
        filterList.forEach(httpResponseFilter -> httpResponseFilter.filter(response));
    }
}
