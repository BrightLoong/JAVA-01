package io.github.brightloong.bringt.rpc.register.zk;

import io.github.brightloong.bringt.rpc.common.entity.ServiceProviderDesc;
import io.github.brightloong.bringt.rpc.exception.RpcException;
import io.github.brightloong.bringt.rpc.register.ServiceDiscovery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/3/20 14:37
 * @description
 */
public class ZkServiceDiscovery implements ServiceDiscovery {

    @Override
    public ServiceProviderDesc discoveryService(CuratorFramework client, String serviceClass) {
        String servicePath =   "/" + serviceClass;
        List<String> result;
        try {
          result = client.getChildren().forPath(servicePath);
        } catch (Exception e) {
            throw new RpcException(RpcException.UNKNOWN_EXCEPTION, "获取服务提供者失败", e);
        }
        if (CollectionUtils.isEmpty(result)) {
            throw new RpcException(RpcException.SERVICE_NOT_FIND, "不存在可用服务");
        }
        //先暂时取第一个，之后做负载均衡
        String url = result.get(0);
        String[] urlSplit = url.split("_");
        return ServiceProviderDesc.builder().serviceClass(serviceClass).host(urlSplit[0]).port(Integer.valueOf(urlSplit[1])).build();
    }

}
