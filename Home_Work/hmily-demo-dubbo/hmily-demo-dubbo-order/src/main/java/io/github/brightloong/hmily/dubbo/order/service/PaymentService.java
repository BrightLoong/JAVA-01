package io.github.brightloong.hmily.dubbo.order.service;

import io.github.brightloong.hmily.dubbo.common.order.entity.Order;
import org.dromara.hmily.annotation.HmilyTCC;

/**
 * @author BrightLoong
 * @date 2021/3/13 14:16
 * @description
 */
public interface PaymentService {
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    void makePayment(Order order);

    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    String mockPaymentInventoryWithTryException(Order order);

    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    String mockPaymentInventoryWithTryTimeout(Order order);

    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    String mockPaymentInventoryWithConfirmTimeout(Order order);

    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    String mockPaymentInventoryWithConfirmException(Order order);
}
