package io.github.brightloong.sharding.domain.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_order")
public class TOrder {
    /**
     * 自增主键
     */
    @Id
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 下单用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 下单时间
     */
    @Column(name = "order_time")
    private Date orderTime;

    /**
     * 订单总价
     */
    @Column(name = "order_price")
    private BigDecimal orderPrice;

    /**
     * 订单收货地址id
     */
    @Column(name = "user_address_id")
    private Integer userAddressId;

    /**
     * 订单转态1.下单2.已支付，3已收货，4已退款，5.交易完成
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 数据创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 数据最后更新时间
     */
    @Column(name = "last_updated_time")
    private Date lastUpdatedTime;

    /**
     * 订单实付
     */
    @Column(name = "order_pay")
    private BigDecimal orderPay;

    /**
     * 获取自增主键
     *
     * @return id - 自增主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增主键
     *
     * @param id 自增主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取下单用户
     *
     * @return user_id - 下单用户
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置下单用户
     *
     * @param userId 下单用户
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取下单时间
     *
     * @return order_time - 下单时间
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * 设置下单时间
     *
     * @param orderTime 下单时间
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 获取订单总价
     *
     * @return order_price - 订单总价
     */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
     * 设置订单总价
     *
     * @param orderPrice 订单总价
     */
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * 获取订单收货地址id
     *
     * @return user_address_id - 订单收货地址id
     */
    public Integer getUserAddressId() {
        return userAddressId;
    }

    /**
     * 设置订单收货地址id
     *
     * @param userAddressId 订单收货地址id
     */
    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    /**
     * 获取订单转态1.下单2.已支付，3已收货，4已退款，5.交易完成
     *
     * @return order_status - 订单转态1.下单2.已支付，3已收货，4已退款，5.交易完成
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单转态1.下单2.已支付，3已收货，4已退款，5.交易完成
     *
     * @param orderStatus 订单转态1.下单2.已支付，3已收货，4已退款，5.交易完成
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取数据创建时间
     *
     * @return create_time - 数据创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置数据创建时间
     *
     * @param createTime 数据创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取数据最后更新时间
     *
     * @return last_updated_time - 数据最后更新时间
     */
    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * 设置数据最后更新时间
     *
     * @param lastUpdatedTime 数据最后更新时间
     */
    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /**
     * 获取订单实付
     *
     * @return order_pay - 订单实付
     */
    public BigDecimal getOrderPay() {
        return orderPay;
    }

    /**
     * 设置订单实付
     *
     * @param orderPay 订单实付
     */
    public void setOrderPay(BigDecimal orderPay) {
        this.orderPay = orderPay;
    }
}