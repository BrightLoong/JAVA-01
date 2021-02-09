package io.github.brightloong.spring.bytebuddy;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

/**
 * @author BrightLoong
 * @date 2021/2/5 15:57
 * @description
 */
public class Logger {
    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        System.out.println("before method:" + method.getName());
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments, @Advice.Return Object ret) {
        System.out.println("after method:" + method.getName());
    }
}
