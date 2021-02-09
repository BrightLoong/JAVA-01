package io.github.brightloong.spring.xbean;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * @author BrightLoong
 * @date 2021/2/5 11:34
 * @description
 */
public class Client {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:xbean.xml");
        Teacher teacher = (Teacher) context.getBean("teacher");
        System.out.println(teacher);
    }
}
