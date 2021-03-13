package io.github.brightloong.hmily.dubbo.order.service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author BrightLoong
 * @date 2021/3/13 14:13
 * @description
 */
public interface OrderService {
    String orderPay(Integer count, BigDecimal amount);

    String mockInventoryWithTryException(Integer count, BigDecimal amount);

    @Transactional
    String mockInventoryWithTryTimeout(Integer count, BigDecimal amount);

    String mockInventoryWithConfirmTimeout(Integer count, BigDecimal amount);

    String mockInventoryWithConfirmException(Integer count, BigDecimal amount);
}
