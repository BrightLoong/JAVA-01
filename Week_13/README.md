### 1、(必做)搭建一个3节点Kafka集群，测试功能和性能;实现spring kafka下对kafka集群的操作，将代码提交到github。
详细配置见[resources](./src/main/resources)

```shell
创建带有副本的topic:
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic test32 --partitions 3 - -replication-factor 2
bin/kafka-console-producer.sh --bootstrap-server localhost:9003 --topic test32
bin/kafka-console-consumer.sh --bootstrap-server localhost:9001 --topic test32 -- from-beginning
执行性能测试:
bin/kafka-producer-perf-test.sh --topic test32 --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9002
bin/kafka-consumer-perf-test.sh --bootstrap-server localhost:9002 --topic test32 -- fetch-size 1048576 --messages 100000 --threads 1
```

实现spring kafka下对kafka集群的操作

详见[App.java](./src/main/java/io/github/brightloong/kafka/App.java)


### 2.（必做）思考和设计自定义 MQ 第二个版本或第三个版本，写代码实现其中至少一个功能点，把设计思路和实现代码，提交到 GitHub。

实现MQ第二个版本

自定义Queue——[MessageQueue.java](./src/main/java/io/github/brightloong/mymq/MessageQueue.java)

详细见[Mq.java](./src/main/java/io/github/brightloong/mymq/Mq.java)，使用offset和comsumerOffsetRecord分别来记录生产者和消费者的offset，并简单实现了确认消费commit功能。

启动Demo详见[MqDemo.java](./src/main/java/io/github/brightloong/mymq/MqDemo.java)

