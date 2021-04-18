package io.github.brightloong.mymq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author BrightLoong
 * @date 2021/4/17 15:27
 * @description
 */
public class Mq {

    private String topic;

    private int capacity;

    private int offset;

    private Map<String, Integer> comsumerOffsetRecord;

    private MessageQueue messageQueue;

    public Mq(String topic, int capacity) {
        this.topic = topic;
        messageQueue = new MessageQueue(capacity);
        this.offset = 0;
        comsumerOffsetRecord = new ConcurrentHashMap<>();
    }

    public <T>  void send(MqMessage<T> mqMessage) {
        messageQueue.write(mqMessage, this.offset);
        this.offset++;
    }

    public <T> MqMessage<T> read(String consumerName) {
        Integer offset = comsumerOffsetRecord.getOrDefault(consumerName, 0);
        if (offset > this.offset) {
            throw new RuntimeException("the message offset now is :"  + this.offset + ",but you want to read " + offset);
        }
        return  messageQueue.read(offset);
    }

    public void commit(String consumerName) {
        comsumerOffsetRecord.putIfAbsent(consumerName, 1);
        comsumerOffsetRecord.computeIfPresent(consumerName, (s, v) -> v++);
    }
}
