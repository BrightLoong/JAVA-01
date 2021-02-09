package io.github.brightloong.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class DynamicProxyHandler implements InvocationHandler {

    private Object proxied;

    /**
     * @param proxied 被代理对象
     */
    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    /**
     * 返回代理对象
     *
     * @return
     */
    public Object newProxyInstance() {
        return Proxy.newProxyInstance(proxied.getClass().getClassLoader(), proxied.getClass().getInterfaces(), this);
    }

    /**
     * @param proxy  代理对象
     * @param method 代理方法
     * @param args   方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method:" + method.getName());
        System.out.println("args:" + args[0].getClass().getName());
        System.out.println("Before invoke method...");
        Object object = method.invoke(proxied, args);
        System.out.println("After invoke method...");
        return object;
    }

}
