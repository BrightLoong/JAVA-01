package io.github.brightloong.gateway.router;

import java.util.List;

public interface HttpEndpointRouter {
    /**
     * Load Balance
     * Random
     * RoundRibbon
     * Weight
     *   - server01,20
     *   - server02,30
     *   - server03,50
     * @param endpoints
     * @return
     */
    String route(List<String> endpoints);

}
