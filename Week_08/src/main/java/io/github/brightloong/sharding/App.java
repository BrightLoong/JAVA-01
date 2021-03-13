package io.github.brightloong.sharding;

import io.github.brightloong.sharding.dao.TOrderDao;
import io.github.brightloong.sharding.domain.pojo.TOrder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BrightLoong
 * @date 2021/2/8 14:20
 * @description
 */

@SpringBootApplication
@ComponentScan(basePackages = {"io.github.brightloong"})
@MapperScan(basePackages = {"io.github.brightloong.sharding.dao"})
public class App {

    @Autowired
    private TOrderDao orderDao;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @PostConstruct
    public void test() {
        TOrder order = new TOrder();
        order.setUserId(1L);
        order.setOrderNo("12345");
        order.setOrderTime(new Date());
        order.setOrderPrice(new BigDecimal("10"));
        order.setUserAddressId(0);
        order.setOrderStatus(1);
        order.setOrderPay(new BigDecimal("10"));
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
        orderDao.insertSelective(order);
    }

}
