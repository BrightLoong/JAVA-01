package io.github.brightloong.gateway.filter.impl;

import com.google.common.net.HttpHeaders;
import io.github.brightloong.gateway.filter.HttpResponseFilter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;

/**
 * @author BrightLoong
 * @date 2021/1/28 18:16
 * @description
 */
public class CookeieResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        Cookie cookie = new DefaultCookie("my_name", "BrightLoong");
        response.headers().set(HttpHeaders.SET_COOKIE, ClientCookieEncoder.LAX.encode(cookie));
    }
}
