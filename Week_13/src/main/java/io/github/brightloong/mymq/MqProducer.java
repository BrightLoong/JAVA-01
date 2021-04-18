package io.github.brightloong.mymq;

import java.util.Objects;

/**
 * @author BrightLoong
 * @date 2021/4/17 15:24
 * @description
 */
public class MqProducer<T> {

    private final MqBroker mqBroker;


    public MqProducer(MqBroker mqBroker) {
        this.mqBroker = mqBroker;
    }

    public void send(String topic, MqMessage<T> mqMessage) {
        Mq mq = mqBroker.findMq(topic);
        if (Objects.isNull(mq)) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
        mq.send(mqMessage);
    }
}
