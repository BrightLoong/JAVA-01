## 第十七节课作业
### 3、（必做）改造自定义RPC的程序，提交到github：
    1）尝试将服务端写死查找接口实现类变成泛型和反射
    2）尝试将客户端动态代理改成AOP，添加异常处理
    3）尝试使用Netty+HTTP作为client端传输方式

见项目[bright-rpc](./../bright-rpc)

## 第十八节课作业
### 3、(必做)结合dubbo+hmily，实现一个TCC外汇交易处理，代码提交到github:
    1)用户A的美元账户和人民币账户都在A库，使用1美元兑换7人民币;
    2)用户B的美元账户和人民币账户都在B库，使用7人民币兑换1美元;
    3)设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

SQL脚本
```mysql
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `account_type` tinyint(2) NOT NULL COMMENT '账户类型，1.人命币，2.美元',
  `account_balance` decimal(20,6) NOT NULL COMMENT '账户资金',
  `account_balance_locked` decimal(20,6) NOT NULL COMMENT '账户冻结资金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
```
见项目[controller](./hmily-dubbo-demo/hmily-dubbo-demo-account-a/src/main/java/io/github/brightloong/account/a/controller/AccountController.java)
