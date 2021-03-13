
CREATE TABLE `t_order_0` (
    `id` int(11) NOT NULL COMMENT '自增主键',
    `order_no` varchar(64) NOT NULL COMMENT '订单号',
    `user_id` bigint(20) NOT NULL COMMENT '下单用户',
    `order_time` datetime NOT NULL COMMENT '下单时间',
    `order_price` decimal(10,2) NOT NULL COMMENT '订单总价',
    `user_address_id` int(11) NOT NULL COMMENT '订单收货地址id',
    `order_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '订单转态1.下单2.已支付，3已收货，4已退款，5.交易完成',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
    `last_updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
    `order_pay` decimal(10,2) NOT NULL COMMENT '订单实付',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单信息表';

CREATE TABLE t_order_1 like t_order_0;
CREATE TABLE t_order_2 like t_order_0;
CREATE TABLE t_order_3 like t_order_0;
CREATE TABLE t_order_4 like t_order_0;
CREATE TABLE t_order_5 like t_order_0;
CREATE TABLE t_order_6 like t_order_0;
CREATE TABLE t_order_7 like t_order_0;
CREATE TABLE t_order_8 like t_order_0;
CREATE TABLE t_order_9 like t_order_0;
CREATE TABLE t_order_10 like t_order_0;
CREATE TABLE t_order_11 like t_order_0;
CREATE TABLE t_order_12 like t_order_0;
CREATE TABLE t_order_13 like t_order_0;
CREATE TABLE t_order_14 like t_order_0;
CREATE TABLE t_order_15 like t_order_0;


CREATE TABLE t_order_1 like t_order_0;
CREATE TABLE t_order_2 like t_order_0;
CREATE TABLE t_order_3 like t_order_0;
CREATE TABLE t_order_4 like t_order_0;
CREATE TABLE t_order_5 like t_order_0;
CREATE TABLE t_order_6 like t_order_0;
CREATE TABLE t_order_7 like t_order_0;
CREATE TABLE t_order_8 like t_order_0;
CREATE TABLE t_order_9 like t_order_0;
CREATE TABLE t_order_10 like t_order_0;
CREATE TABLE t_order_11 like t_order_0;
CREATE TABLE t_order_12 like t_order_0;
CREATE TABLE t_order_13 like t_order_0;
CREATE TABLE t_order_14 like t_order_0;
CREATE TABLE t_order_15 like t_order_0;