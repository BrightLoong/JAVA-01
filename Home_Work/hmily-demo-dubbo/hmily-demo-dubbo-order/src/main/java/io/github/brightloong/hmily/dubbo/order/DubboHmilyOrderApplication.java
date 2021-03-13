
package io.github.brightloong.hmily.dubbo.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
@MapperScan("io.github.brightloong.hmily.dubbo.common.order.mapper")
public class DubboHmilyOrderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DubboHmilyOrderApplication.class, args);
    }

}
