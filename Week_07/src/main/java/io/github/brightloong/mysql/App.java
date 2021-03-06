package io.github.brightloong.mysql;

import io.github.brightloong.mysql.domain.pojo.DataTest;
import io.github.brightloong.mysql.service.IReadService;
import io.github.brightloong.mysql.service.IWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.PostConstruct;

/**
 * @author BrightLoong
 * @date 2021/2/8 14:20
 * @description
 */

@SpringBootApplication
@ComponentScan(basePackages = {"io.github.brightloong"})
@MapperScan(basePackages = {"io.github.brightloong.mysql.dao"})
public class App {

    @Autowired
    private IReadService<DataTest> readService;

    @Autowired
    private IWriteService<DataTest> writeService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void testDataBase() {
        DataTest dataTest = new DataTest();
        dataTest.setName("ds1");
        dataTest.setDataSource("ds1");
        writeService.write(dataTest);

        DataTest dataTest2 = new DataTest();
        dataTest2.setName("ds2");
        dataTest2.setDataSource("ds2");
        writeService.writeDs2(dataTest2);

        DataTest dataTest3 = new DataTest();
        dataTest3.setName("ds3");
        dataTest3.setDataSource("ds3");
        writeService.writeDs3(dataTest3);

        System.out.println("Main====>" + readService.readFromMain());
        System.out.println("DS2====>" + readService.readFromDs2());
        System.out.println("Slaves====>" + readService.readFromSlaves());
        System.out.println("Slaves====>" + readService.readFromSlaves());
        System.out.println("Slaves====>" + readService.readFromSlaves());
    }

}
