package io.github.brightloong.spring.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;

/**
 * @author BrightLoong
 * @date 2021/2/5 15:35
 * @description
 */
public class MainClass {

    public static void main(String[] args) {
        try {
            Hello hello = new ByteBuddy()
                    .subclass(Hello.class)
                    .method(ElementMatchers.any())
                    .intercept(Advice.to(Logger.class))
                    .make()
                    .load(Hello.class.getClassLoader())
                    .getLoaded()
                    .newInstance();
            hello.sayHello();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
