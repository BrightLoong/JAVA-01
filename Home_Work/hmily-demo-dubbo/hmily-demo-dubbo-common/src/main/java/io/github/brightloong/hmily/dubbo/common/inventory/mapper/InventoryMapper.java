/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.brightloong.hmily.dubbo.common.inventory.mapper;

import io.github.brightloong.hmily.dubbo.common.inventory.dto.InventoryDTO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryMapper {

    @Update("update inventory set total_inventory = total_inventory - #{count}," +
            " lock_inventory= lock_inventory + #{count} " +
            " where product_id =#{productId} and total_inventory >= #{count} ")
    int decrease(InventoryDTO inventoryDTO);

    @Update("update inventory set " +
            " lock_inventory = lock_inventory - #{count} " +
            " where product_id =#{productId} and lock_inventory >= #{count} ")
    int confirm(InventoryDTO inventoryDTO);

    @Update("update inventory set total_inventory = total_inventory + #{count}," +
            " lock_inventory= lock_inventory - #{count} " +
            " where product_id =#{productId}  and lock_inventory >= #{count} ")
    int cancel(InventoryDTO inventoryDTO);
}
