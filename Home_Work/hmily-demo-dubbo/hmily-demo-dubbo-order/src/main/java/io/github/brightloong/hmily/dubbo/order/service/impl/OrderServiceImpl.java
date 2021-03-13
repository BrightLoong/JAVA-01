package io.github.brightloong.hmily.dubbo.order.service.impl;

import io.github.brightloong.hmily.dubbo.common.order.entity.Order;
import io.github.brightloong.hmily.dubbo.common.order.enums.OrderStatusEnum;
import io.github.brightloong.hmily.dubbo.common.order.mapper.OrderMapper;
import io.github.brightloong.hmily.dubbo.order.service.OrderService;
import io.github.brightloong.hmily.dubbo.order.service.PaymentService;
import org.dromara.hmily.common.utils.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BrightLoong
 * @date 2021/3/13 14:14
 * @description
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PaymentService paymentService;

    @Override
    public String orderPay(Integer count, BigDecimal amount) {
        Order order = saveOrder(count, amount);
        paymentService.makePayment(order);
        return "success";
    }

    @Override
    public String mockInventoryWithTryException(Integer count, BigDecimal amount) {
        Order order = saveOrder(count, amount);
        return paymentService.mockPaymentInventoryWithTryException(order);
    }

    @Override
    @Transactional
    public String mockInventoryWithTryTimeout(Integer count, BigDecimal amount) {
        Order order = saveOrder(count, amount);
        paymentService.mockPaymentInventoryWithTryTimeout(order);
        return "success";
    }

    @Override
    @Transactional
    public String mockInventoryWithConfirmTimeout(Integer count, BigDecimal amount) {
        Order order = saveOrder(count, amount);
        paymentService.mockPaymentInventoryWithConfirmTimeout(order);
        return "success";
    }

    @Override
    public String mockInventoryWithConfirmException(Integer count, BigDecimal amount) {
        Order order = saveOrder(count, amount);
        paymentService.mockPaymentInventoryWithConfirmException(order);
        return "success";
    }

    private Order saveOrder(Integer count, BigDecimal amount) {
        final Order order = buildOrder(count, amount);
        orderMapper.save(order);
        return order;
    }


    private Order buildOrder(Integer count, BigDecimal amount) {
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setNumber(String.valueOf(IdWorkerUtils.getInstance().createUUID()));
        //demo中的表里只有商品id为1的数据
        order.setProductId("1");
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        order.setTotalAmount(amount);
        order.setCount(count);
        //demo中 表里面存的用户id为10000
        order.setUserId("10000");
        return order;
    }
}
