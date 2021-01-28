package io.github.brightloong.gateway.router.impl;

import io.github.brightloong.gateway.router.HttpEndpointRouter;

import java.util.List;
import java.util.Random;

/**
 * @author BrightLoong
 * @date 2021/1/22 16:22
 * @description
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    /**
     * Random router
     * @param endpoints
     * @return
     */
    @Override
    public String route(List<String> endpoints) {
        Random random = new Random(System.currentTimeMillis());
        return endpoints.get(random.nextInt(endpoints.size()));
    }
}
