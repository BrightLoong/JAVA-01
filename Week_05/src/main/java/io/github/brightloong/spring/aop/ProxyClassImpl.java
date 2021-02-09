package io.github.brightloong.spring.aop;


public class ProxyClassImpl implements IProxyClass {
    @Override
    public int doSomething(int num) {
        System.out.println("方法执行中.....");
        return num;
    }

    @Override
    public int doSomething2(int num) {
        System.out.println("方法2执行中.....");
        return num;
    }
}
