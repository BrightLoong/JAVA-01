package io.github.brightloong.hmily.dubbo.common.inventory.api;

import io.github.brightloong.hmily.dubbo.common.inventory.dto.InventoryDTO;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author BrightLoong
 * @date 2021/3/11 22:34
 * @description
 */
public interface InventoryService {
    @Hmily
    Boolean decrease(InventoryDTO inventoryDTO);

    @Hmily
    String mockWithTryException(InventoryDTO inventoryDTO);

    @Hmily
    Boolean mockWithTryTimeout(InventoryDTO inventoryDTO);

    @Hmily
    Boolean mockWithConfirmException(InventoryDTO inventoryDTO);

    @Hmily
    Boolean mockWithConfirmTimeout(InventoryDTO inventoryDTO);
}
