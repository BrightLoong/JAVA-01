package io.github.brightloong.bringt.rpc.register.zk;

import io.github.brightloong.bringt.rpc.common.entity.ServiceProviderDesc;
import io.github.brightloong.bringt.rpc.register.ServiceRegistry;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;

/**
 * @author BrightLoong
 * @date 2021/3/19 22:06
 * @description
 */
public class ZkServiceRegistry implements ServiceRegistry {


    @SneakyThrows
    @Override
    public void registerService(CuratorFramework client, String serviceName) {

        ServiceProviderDesc userServiceSesc = ServiceProviderDesc.builder()
                .host(InetAddress.getLocalHost().getHostAddress())
                .port(8081).serviceClass(serviceName).build();

        if (null == client.checkExists().forPath("/" + serviceName)) {
            client.create().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName, "service".getBytes());
        }
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/" + serviceName + "/" + userServiceSesc.getHost() + "_" + userServiceSesc.getPort(), "provider".getBytes());
    }
}
