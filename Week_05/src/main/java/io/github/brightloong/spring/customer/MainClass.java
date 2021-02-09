package io.github.brightloong.spring.customer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author BrightLoong
 * @date 2021/2/5 22:46
 * @description
 */
public class MainClass {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        Student student = (Student) context.getBean("student1");
        System.out.println(student);
        Klass klass = context.getBean(Klass.class);
        System.out.println(klass);
        School school = context.getBean(School.class);
        System.out.println(school);
    }
}
