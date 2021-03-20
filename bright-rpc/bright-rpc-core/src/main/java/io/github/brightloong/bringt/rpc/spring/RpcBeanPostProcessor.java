package io.github.brightloong.bringt.rpc.spring;

import io.github.brightloong.bringt.rpc.annotation.RpcService;
import io.github.brightloong.bringt.rpc.register.ServiceRegistry;
import io.github.brightloong.bringt.rpc.register.zk.ZkServiceRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author BrightLoong
 * @date 2021/3/19 21:55
 * @description
 */
public class RpcBeanPostProcessor implements BeanPostProcessor {

    private ServiceRegistry serviceRegistry;

    public RpcBeanPostProcessor() {
        serviceRegistry = new ZkServiceRegistry();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //存在RpcService标识
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            serviceRegistry.registerService(null, bean.getClass().getName());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
