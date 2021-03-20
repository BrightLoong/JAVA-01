package io.github.brightloong.rpc.demo.customer;

import io.github.brightloong.bringt.rpc.proxy.RpcClientProxy;
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
    public static void main(String[] args) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();

        TestService testService = new RpcClientProxy().createFromRegistry(client, TestService.class);
        System.out.println(testService.say("hello"));
    }
}
