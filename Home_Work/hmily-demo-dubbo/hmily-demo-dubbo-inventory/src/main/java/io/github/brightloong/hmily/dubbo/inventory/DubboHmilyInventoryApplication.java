
package io.github.brightloong.hmily.dubbo.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@MapperScan("io.github.brightloong.hmily.dubbo.common.inventory.mapper")
@ImportResource({"classpath:spring-dubbo.xml"})
public class DubboHmilyInventoryApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboHmilyInventoryApplication.class);

    public static void main(final String[] args) {
        SpringApplication springApplication = new SpringApplication(DubboHmilyInventoryApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}
