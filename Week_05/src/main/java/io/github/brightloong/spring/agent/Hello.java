package io.github.brightloong.spring.agent;

/**
 * @author BrightLoong
 * @date 2021/2/5 17:23
 * @description
 */
public class Hello {

    public String sayHello() {
        System.out.println("Hello Agent");
        return "";
    }

    public static void main(String[] args) {
        new Hello().sayHello();
    }
}
