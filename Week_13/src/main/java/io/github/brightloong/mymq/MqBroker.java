package io.github.brightloong.mymq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BrightLoong
 * @date 2021/4/17 15:10
 * @description
 */
public class MqBroker {
    private final Map<String, Mq> mqMap = new ConcurrentHashMap<>(64);

    public static final int CAPACITY = 10000;

    public void createTopic(String name){
        mqMap.putIfAbsent(name, new Mq(name, CAPACITY));
    }

    public Mq findMq(String topic) {
        return this.mqMap.get(topic);
    }

    public <T>  MqProducer<T> createProducer() {
        return new MqProducer<T>(this);
    }

    public <T> MqConsumer<T> createConsumer() {
        return new MqConsumer<T>(this);
    }

}
