## 读书笔记（进行中，还未完成）

[极客《MySQL实战45讲》读书笔记](http://note.youdao.com/s/UV1aYz4F)
## 第十二周作业
### 2.（必做）基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交DDL的SQL文件到Github（后面2周的作业依然要是用到这个表结构）。
```sql


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_price` decimal(10,2) NOT NULL COMMENT '订单总价',
  `user_address_id` int(11) NOT NULL COMMENT '订单收货地址id',
  `order_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '订单转态1.下单2.已支付，3已收货，4已退款，5.交易完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `last_updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
  `order_pay` decimal(10,2) NOT NULL COMMENT '订单实付',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_address_id` (`user_address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单信息表';

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `shop_id` bigint(20) NOT NULL COMMENT '店铺id',
  `title` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '商品名称',
  `sub_title` varchar(64) NOT NULL COMMENT '商品子标题',
  `description` varchar(255) NOT NULL COMMENT '商品描述',
  `img_url` varchar(255) NOT NULL COMMENT '商品图片',
  `product_status` tinyint(2) NOT NULL COMMENT '商品状态1.上架，2.下架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `last_updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息表';

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `img_url` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商品图片',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `discount_price` decimal(10,2) NOT NULL COMMENT '折扣价',
  `title` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '商品标题',
  `sub_title` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '商品子标题',
  `sku_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT 'sku状态1上架2下架',
  `description` varchar(255) NOT NULL COMMENT '商品描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `last_updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sku信息库';

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner_id` bigint(20) NOT NULL COMMENT '店铺所有者',
  `register_time` datetime NOT NULL COMMENT '店铺注册时间',
  `shop_name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '店铺名称',
  `description` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '店铺描述',
  `shop_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '店铺状态：1.审核中，2.营业中，3.关闭',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `last_updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_owner_id` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺信息表';

-- ----------------------------
-- Table structure for sub_order
-- ----------------------------
DROP TABLE IF EXISTS `sub_order`;
CREATE TABLE `sub_order` (
  `id` bigint(20) NOT NULL COMMENT '自增主键',
  `order_no` varchar(64) NOT NULL COMMENT '订单编号',
  `sub_order_no` varchar(64) NOT NULL COMMENT '子订单号',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `sku_id` int(11) NOT NULL COMMENT 'sku_id',
  `user_id` int(11) NOT NULL COMMENT '冗余下单用户id',
  `quantity` varchar(255) NOT NULL COMMENT '商品数量',
  `sub_ordere_price` decimal(10,2) NOT NULL COMMENT '子订单价格',
  `sub_order_pay` decimal(10,2) NOT NULL COMMENT '子订单实付',
  `sub_order_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '子订单状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `last_updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_sub_order_no` (`sub_order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子订单';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_name` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `phone_num` int(11) NOT NULL COMMENT '用户手机号',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `avatar_url` varchar(255) NOT NULL COMMENT '用户头像',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `register_time` datetime NOT NULL COMMENT '用户注册时间',
  `gender` tinyint(2) NOT NULL DEFAULT '3' COMMENT '性别，1.男，2.女，3.未知',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `user_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '用户类型，1普通用户，2商家',
  `user_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '用户状态1.正常2.异常3.已注销',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `last_updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_phone_num` (`phone_num`),
  KEY `idx_password` (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户数据表';

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户uid',
  `country` varchar(64) NOT NULL COMMENT '国家',
  `province` varchar(64) NOT NULL COMMENT '省份',
  `city` varchar(64) NOT NULL COMMENT '市',
  `region` varchar(64) NOT NULL COMMENT '区/县',
  `detail` varchar(100) NOT NULL COMMENT '详细地址',
  `postcode` int(11) NOT NULL COMMENT '邮编',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `last_updated_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收货地址表';

SET FOREIGN_KEY_CHECKS = 1;

```