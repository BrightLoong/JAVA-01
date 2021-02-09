package io.github.brightloong.spring.boot;

import io.github.brightloong.spring.starter.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * @author BrightLoong
 * @date 2021/2/8 14:20
 * @description
 */

@SpringBootApplication
@ComponentScan(basePackages = {"io.github.brightloong"})
public class App {

    @Autowired
    private School school;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void testSchool() {
        System.out.println(school);
    }
}
