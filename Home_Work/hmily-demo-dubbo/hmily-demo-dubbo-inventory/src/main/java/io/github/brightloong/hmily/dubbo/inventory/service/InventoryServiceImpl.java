package io.github.brightloong.hmily.dubbo.inventory.service;

import io.github.brightloong.hmily.dubbo.common.inventory.api.InventoryService;
import io.github.brightloong.hmily.dubbo.common.inventory.dto.InventoryDTO;
import io.github.brightloong.hmily.dubbo.common.inventory.mapper.InventoryMapper;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author BrightLoong
 * @date 2021/3/12 22:13
 * @description
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public Boolean decrease(InventoryDTO inventoryDTO) {
        LOGGER.info("=====扣减库存=====");
        return inventoryMapper.decrease(inventoryDTO) > 0;
    }


    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public String mockWithTryException(InventoryDTO inventoryDTO) {
        LOGGER.info("=====扣减库存 with try exception=====");
        throw new HmilyRuntimeException("库存扣减异常！");
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    @Transactional(rollbackFor = Exception.class)
    public Boolean mockWithTryTimeout(InventoryDTO inventoryDTO) {

        LOGGER.info("=====扣减库存 with try timeout=====");
        try {
            //模拟延迟 当前线程暂停10秒
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final int decrease = inventoryMapper.decrease(inventoryDTO);
        if (decrease != 1) {
            throw new HmilyRuntimeException("库存不足");
        }
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmMethodException", cancelMethod = "cancelMethod")
    @Transactional(rollbackFor = Exception.class)
    public Boolean mockWithConfirmException(InventoryDTO inventoryDTO) {
        LOGGER.info("==========调用扣减库存try mockWithConfirmException===========");
        final int decrease = inventoryMapper.decrease(inventoryDTO);
        if (decrease != 1) {
            throw new HmilyRuntimeException("库存不足");
        }
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmMethodTimeout", cancelMethod = "cancelMethod")
    @Transactional(rollbackFor = Exception.class)
    public Boolean mockWithConfirmTimeout(InventoryDTO inventoryDTO) {
        LOGGER.info("==========调用扣减库存try mockWithConfirmTimeout===========");
        final int decrease = inventoryMapper.decrease(inventoryDTO);
        if (decrease != 1) {
            throw new HmilyRuntimeException("库存不足");
        }
        return true;
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmMethodTimeout(InventoryDTO inventoryDTO) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("==========调用扣减库存确认方法 confirmMethodTimeout===========");
        inventoryMapper.confirm(inventoryDTO);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmMethodException(InventoryDTO inventoryDTO) {
        LOGGER.info("==========调用扣减库存确认方法 confirmMethodException===========");
        final int decrease = inventoryMapper.confirm(inventoryDTO);
        if (decrease < 1) {
            throw new HmilyRuntimeException("库存不足");
        }
        return true;
    }

    public Boolean confirmMethod(InventoryDTO inventoryDTO) {
        LOGGER.info("==========调用扣减库存confirm方法===========");
        inventoryMapper.confirm(inventoryDTO);
        return true;
    }

    public Boolean cancelMethod(InventoryDTO inventoryDTO) {
        LOGGER.info("==========调用扣减库存cancel方法===========");
        inventoryMapper.cancel(inventoryDTO);
        return true;
    }
}
