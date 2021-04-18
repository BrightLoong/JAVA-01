package io.github.brightloong.mymq;

import java.util.Objects;
import java.util.UUID;

/**
 * @author BrightLoong
 * @date 2021/4/17 15:24
 * @description
 */
public class MqConsumer<T> {

    private MqBroker mqBroker;

    private String consumerName;

    private Mq mq;


    public MqConsumer(MqBroker mqBroker) {
        this.mqBroker = mqBroker;
        //暂时给个默认的名字UUID
        this.consumerName = UUID.randomUUID().toString();
    }

    public void subscribe(String topic) {
        this.mq = this.mqBroker.findMq(topic);
        if (Objects.isNull(mq)) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
    }

    public MqMessage<T> readMessage() {
        return (MqMessage<T>) mq.read(this.consumerName);
    }

    public void commit() {
        mq.commit(this.consumerName);
    }
}
