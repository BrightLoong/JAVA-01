package io.github.brightloong.hmily.dubbo.order.controller;

import io.github.brightloong.hmily.dubbo.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author BrightLoong
 * @date 2021/3/13 14:22
 * @description
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/orderPay")
    @ApiOperation(value = "订单支付接口")
    public String orderPay(@RequestParam(value = "count") Integer count,
                           @RequestParam(value = "amount") BigDecimal amount) {
        orderService.orderPay(count, amount);
        return "";
    }

    @PostMapping(value = "/mockInventoryWithTryException")
    @ApiOperation(value = "模拟下单付款操作在try阶段时候，库存服务异常，订单状态会回滚，达到数据的一致性")
    public String mockInventoryWithTryException(@RequestParam(value = "count") Integer count,
                                                @RequestParam(value = "amount") BigDecimal amount) {
        return orderService.mockInventoryWithTryException(count, amount);
    }


    @PostMapping(value = "/mockInventoryWithTryTimeout")
    @ApiOperation(value = "模拟下单付款操作在try阶段时候，库存超时异常（但是自身最后又成功了），此时订单状态会回滚，（库存依赖事务日志进行恢复），达到数据的一致性（异常指的是超时异常）")
    public String mockInventoryWithTryTimeout(@RequestParam(value = "count") Integer count,
                                              @RequestParam(value = "amount") BigDecimal amount) {
        return orderService.mockInventoryWithTryTimeout(count, amount);
    }

    @PostMapping(value = "/mockInventoryWithConfirmException")
    @ApiOperation(value = "模拟下单付款操作中，try操作完成，但是库存模块在confirm阶段异常，此时订单会执行confirm方法，库存的confirm方法依赖自身日志，进行调度执行达到数据的一致性，因为订单已经confirm，最终会confirm库存")
    public String mockInventoryWithConfirmException(@RequestParam(value = "count") Integer count,
                                                  @RequestParam(value = "amount") BigDecimal amount) {
        return orderService.mockInventoryWithConfirmException(count,amount);
    }

    @PostMapping(value = "/mockInventoryWithConfirmTimeout")
    @ApiOperation(value = "模拟下单付款操作中，try操作完成，但是库存模块在confirm阶段超时异常，此时订单会执行confirm方法，库存的confirm方法依赖自身日志，进行调度执行达到数据的一致性")
    public String mockInventoryWithConfirmTimeout(@RequestParam(value = "count") Integer count,
                                                  @RequestParam(value = "amount") BigDecimal amount) {
        return orderService.mockInventoryWithConfirmTimeout(count,amount);
    }
}
