package io.github.brightloong.bytebuddy.agent;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author BrightLoong
 * @date 2021/2/5 18:16
 * @description
 */
public class LogIntercept {
    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
       if (method.getName().contains("main")) {
           return callable.call();
       }
        // 原有函数执行;
        System.out.println("<<< ByteBuddyAgent ===> enter method:" + method.getName());
        try {
            return callable.call();
        } finally {
            System.out.println(">>> ByteBuddyAgent ===> exit method:" + method.getName());
        }
    }
}
