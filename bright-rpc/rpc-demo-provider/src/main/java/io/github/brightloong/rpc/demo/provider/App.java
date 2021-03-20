package io.github.brightloong.rpc.demo.provider;

import io.github.brightloong.bringt.rpc.provider.ServiceProvider;
import io.github.brightloong.bringt.rpc.proxy.RpcClientProxy;
import io.github.brightloong.bringt.rpc.register.zk.ZkServiceDiscovery;
import io.github.brightloong.bringt.rpc.register.zk.ZkServiceRegistry;
import io.github.brightloong.bringt.rpc.remoting.transport.netty.http.server.HttpNettyServer;
import io.github.brightloong.rpc.demo.api.TestService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author BrightLoong
 * @date 2021/3/20 19:31
 * @description
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.addService(new TestServiceImpl(), TestService.class.getName());
        new ZkServiceRegistry().registerService(client, TestService.class.getName());
        new HttpNettyServer(serviceProvider).run();
    }
}
