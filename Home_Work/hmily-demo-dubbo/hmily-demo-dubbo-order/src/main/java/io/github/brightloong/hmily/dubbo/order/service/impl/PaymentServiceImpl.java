package io.github.brightloong.hmily.dubbo.order.service.impl;

import io.github.brightloong.hmily.dubbo.common.inventory.api.InventoryService;
import io.github.brightloong.hmily.dubbo.common.inventory.dto.InventoryDTO;
import io.github.brightloong.hmily.dubbo.common.order.entity.Order;
import io.github.brightloong.hmily.dubbo.common.order.enums.OrderStatusEnum;
import io.github.brightloong.hmily.dubbo.common.order.mapper.OrderMapper;
import io.github.brightloong.hmily.dubbo.order.service.PaymentService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author BrightLoong
 * @date 2021/3/13 14:17
 * @description
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private InventoryService inventoryService;

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void makePayment(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        //进入扣减库存操作
        inventoryService.decrease(buildInventoryDTO(order));
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public String mockPaymentInventoryWithTryException(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        inventoryService.mockWithTryException(buildInventoryDTO(order));
        return "success";
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public String mockPaymentInventoryWithTryTimeout(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        inventoryService.mockWithTryTimeout(buildInventoryDTO(order));
        return "success";
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public String mockPaymentInventoryWithConfirmTimeout(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        inventoryService.mockWithConfirmTimeout(buildInventoryDTO(order));
        return "success";
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public String mockPaymentInventoryWithConfirmException(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        inventoryService.mockWithConfirmException(buildInventoryDTO(order));
        return "success";
    }

    private InventoryDTO buildInventoryDTO(Order order) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setCount(order.getCount());
        inventoryDTO.setProductId(order.getProductId());
        return inventoryDTO;
    }

    private void updateOrderStatus(Order order, OrderStatusEnum orderStatus) {
        order.setStatus(orderStatus.getCode());
        orderMapper.update(order);
    }


    public void confirmOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAY_SUCCESS);
        LOGGER.info("=========进行订单confirm操作完成================");
    }

    public void cancelOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAY_FAIL);
        LOGGER.info("=========进行订单cancel操作完成================");
    }

}
