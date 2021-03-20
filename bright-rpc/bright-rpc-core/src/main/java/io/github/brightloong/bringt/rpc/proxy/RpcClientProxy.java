package io.github.brightloong.bringt.rpc.proxy;

import io.github.brightloong.bringt.rpc.common.entity.ServiceProviderDesc;
import io.github.brightloong.bringt.rpc.register.ServiceDiscovery;
import io.github.brightloong.bringt.rpc.register.zk.ZkServiceDiscovery;
import org.apache.curator.framework.CuratorFramework;

import java.lang.reflect.Proxy;

/**
 * @author BrightLoong
 * @date 2021/3/18 21:18
 * @description
 */
public class RpcClientProxy {

    private ServiceDiscovery serviceDiscovery;

    public RpcClientProxy() {
        serviceDiscovery = new ZkServiceDiscovery();
    }

    public  <T> T createFromRegistry(CuratorFramework client, Class<T> serviceClass) {
        ServiceProviderDesc serviceProviderDesc = serviceDiscovery.discoveryService(client, serviceClass.getName());
        return create(serviceClass, "http://" + serviceProviderDesc.getHost() + ":" + serviceProviderDesc.getPort());
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {
        return (T) Proxy.newProxyInstance(RpcClientProxy.class.getClassLoader(), new Class[]{serviceClass}, new HttpInvocationHandler(serviceClass, url));

    }
}
