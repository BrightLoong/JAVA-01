
package io.github.brightloong.account.b;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
@MapperScan("io.github.brightloong.account.common.mapper")
public class AccountBApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AccountBApplication.class, args);
    }

}
