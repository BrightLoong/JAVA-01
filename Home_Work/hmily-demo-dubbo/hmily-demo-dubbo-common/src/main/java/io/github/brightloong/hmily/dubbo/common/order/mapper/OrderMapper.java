
package io.github.brightloong.hmily.dubbo.common.order.mapper;

import io.github.brightloong.hmily.dubbo.common.order.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    /**
     * 保存订单.
     *
     * @param order 订单对象
     * @return rows int
     */
    @Insert(" insert into `order` (create_time,number,status,product_id,total_amount,count,user_id) " +
            " values ( #{createTime},#{number},#{status},#{productId},#{totalAmount},#{count},#{userId})")
    int save(Order order);

    /**
     * 更新订单.
     *
     * @param order 订单对象
     * @return rows int
     */
    @Update("update `order` set status = #{status}  where number = #{number}")
    int update(Order order);

    /**
     * 获取所有的订单
     *
     * @return List<Order> list
     */
    @Select("select * from  order")
    List<Order> listAll();
}