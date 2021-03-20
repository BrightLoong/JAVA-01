package io.github.brightloong.bringt.rpc.register;

import io.github.brightloong.bringt.rpc.common.entity.ServiceProviderDesc;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author BrightLoong
 * @date 2021/3/19 22:19
 * @description
 */
public interface ServiceDiscovery {
    ServiceProviderDesc discoveryService(CuratorFramework client, String rpcServiceName);
}
