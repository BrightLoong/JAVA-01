
## 第十三周作业

### 2.（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
详见[BatchInsertTest.java](./src/main/java/io/github/brightloong/mysql/BatchInsertTest.java)

#### 总结
    1. rewriteBatchedStatements参数会影响到批量插入，把rewriteBatchedStatements参数置为true, 驱动才会帮你批量执行SQL。
    2. 索引字段会影响插入的速度
    3. 一个表字段的多少和大小也会影响插入的速度

## 第十四周作业
### 2、(必做)读写分离-动态切换数据源版本1.0 3、(必做)读写分离-数据库框架版本2.0

动态切换包装了一个spring boot start:[bright-datasource-spring-boot-start](../Home_Work/bright-datasource-spring-boot-start)，
通过注解 `BrightDs` 来控制数据源的切换，和是否进行从库的负载均衡，详见[MyDataSourceAspect.java](../Home_Work/bright-datasource-spring-boot-start/src/main/java/io/github/brightloong/bright/datasource/aspect/MyDataSourceAspect.java)

修改[application.yml](./src/main/resources/application.yml),加载[application-datasource.yml](./src/main/resources/application-datasource.yml)文件，
通过[App.java](./src/main/java/io/github/brightloong/mysql/App.java)启动，即可看到效果。

### 3.（必做）读写分离 - 数据库框架版本 2.0

修改[application.yml](./src/main/resources/application.yml),加载[application-sharding.yml](./src/main/resources/application-sharding.yml)文件，
通过[App.java]()启动，即可看到效果。