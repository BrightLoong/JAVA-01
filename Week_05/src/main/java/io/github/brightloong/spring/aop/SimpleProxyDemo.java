package io.github.brightloong.spring.aop;


public class SimpleProxyDemo {
    public static void main(String[] args) throws SecurityException {
        ProxyClassImpl c = new ProxyClassImpl();
        DynamicProxyHandler proxyHandler = new DynamicProxyHandler(c);
        IProxyClass proxyClass = (IProxyClass)proxyHandler.newProxyInstance();
        System.out.println(proxyClass.getClass().getName());
        System.out.println(proxyClass.doSomething2(5));
    }
}
