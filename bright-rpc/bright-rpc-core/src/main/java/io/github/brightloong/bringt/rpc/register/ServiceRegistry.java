package io.github.brightloong.bringt.rpc.register;

import io.github.brightloong.bringt.rpc.common.entity.ServiceProviderDesc;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author BrightLoong
 * @date 2021/3/19 22:15
 * @description
 */
public interface ServiceRegistry {

    @SneakyThrows
    void registerService(CuratorFramework client, String service);
}
