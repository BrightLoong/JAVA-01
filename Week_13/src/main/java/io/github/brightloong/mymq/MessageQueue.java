package io.github.brightloong.mymq;

import java.util.Map;

/**
 * @author BrightLoong
 * @date 2021/4/17 15:14
 * @description
 */
public class MessageQueue {

    private MqMessage[] mqMessages;

    private int capacity;



    public MessageQueue(int capacity) {
        this.capacity = capacity;
        this.mqMessages = new MqMessage[capacity];
    }

    public <T> void write(MqMessage<T> mqMessage, int offset) {
        if (offset == capacity) {
            throw new RuntimeException("the queue message is full，can not accept more");
        }
        mqMessages[offset] = mqMessage;
    }

    public <T> MqMessage<T> read(int offset) {
        return mqMessages[offset];
    }
}
