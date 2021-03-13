## 第十五节课作业
### 2、（必做）设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。并在新结构在演示常见的增删改查操作。代码、sql和配置文件，上传到Github。
使用sharding-jdbc实现

配置文件详见[application.yml](./src/main/resources/application.yml)

SQL脚本[table.sql](./src/main/resources/sql/table.sql)

启动[App.java](./src/main/java/io/github/brightloong/sharding/App.java)

## 第十六节课作业

### 2、(必做)基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布 式事务应用 demo(二选一)，提交到 Github。

使用 hmily TCC + dubbo，参考hmily里面关于dubbo的demo实现。

这里只使用了order和inventory这两个来进行TCC相关的实践，详见[hmily-demo-dubbo](../Home_Work/hmily-demo-dubbo)，启动项目通过
[http://localhost:8087/swagger-ui.html `(点击查看Controller实现)`](../Home_Work/hmily-demo-dubbo/hmily-demo-dubbo-order/src/main/java/io/github/brightloong/hmily/dubbo/order/controller/OrderController.java)进行访问，下面是对不同的场景最后的情况做一个记录。

|             场景              |                             描述                             |
| :---------------------------: | :----------------------------------------------------------: |
|           orderPay            |    正常流程，成功生成订单，订单支付状态完成，库存扣减成功    |
|   InventoryWithTryException   | 模拟下单操作在try阶段时候，库存服务异常，订单状态会回滚（生成订单，订单状态支付失败），达到数据的一致性 |
|    InventoryWithTryTimeout    | 模拟下单付款操作在try阶段时候，库存超时异常（但是自身最后又成功了），此时订单状态会回滚（订单未生成），（库存依赖事务日志进行恢复），达到数据的一致性（异常指的是超时异常） |
|  InventoryWithConfirmTimeout  | 模拟下单付款操作中，try操作完成，但是库存模块在confirm阶段超时异常，此时订单已经执行confirm方法，库存的confirm方法依赖自身日志，成功生成订单，订单状态支付完成，库存正确扣减，进行调度执行达到数据的一致性 |
| InventoryWithConfirmException | 模拟下单付款操作中，try操作完成，但是库存模块在confirm阶段异常，此时订单会执行confirm方法，库存的confirm方法依赖自身日志，进行调度执行达到数据的一致性，因为订单已经confirm，最终会confirm库存。体现了try成功锁住资源就能确保confirm的成功。 |