
## 第一节课
### 1、(必做)配置redis的主从复制，sentinel高可用，Cluster集群。
> 各种配置文件和脚本详见 [配置文件和脚本](./cache-demo/src/main/resources/config)

> 测试代码详见[RedisDemo.java](./cache-demo/src/main/java/io/github/brightloong/cache/redis/RedisDemo.java)
#### 主从复制
```shell
//启动redis
> redis-server redis-6379.conf
> redis-server redis-6380.conf

//把6380配置为从库
> slaveof 127.0.0.1 6379
```
#### sentinel高可用
```shell
//按照上述两台机器启动redis,并配置主从
//启动redis
> redis-server redis-6379.conf
> redis-server redis-6380.conf

//把6380配置为从库
> slaveof 127.0.0.1 6379

//启动哨兵
> redis-sentinel sentinel01.conf
> redis-sentinel sentinel02.conf

```
#### Cluster集群
```shell
//启动集群模式下的redis
> redis-server redis-cluster-6379.conf
> redis-server redis-cluster-6380.conf
> redis-server redis-cluster-6381.conf

//组建集群，在6379上执行命令，也可以在另外两台执行，注意修改端口号
> cluster meet 127.0.0.1 6380
> cluster meet 127.0.0.1 6381

//进行槽分配,使用add_slots.sh脚本进行槽分配
> sh add_slots.sh 0 5461 6379
> sh add_slots.sh 5462 10922 6380
> sh add_slots.sh 10923 16383 6381
```

## 第二节课
### 1、(必做)搭建ActiveMQ服务，基于JMS，写代码分别实现对于queue和topic的消息 生产和消费，代码提交到github。

> 测试代码详见[App.java](./mq-demo/src/main/java/io/github/brightloong/mq/active/App.java)

