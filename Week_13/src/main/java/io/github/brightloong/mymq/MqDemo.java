package io.github.brightloong.mymq;

import java.util.HashMap;

/**
 * @author BrightLoong
 * @date 2021/4/18 21:03
 * @description
 */
public class MqDemo {
    public static void main(String[] args) {
        String topic = "my_topic";
        MqBroker broker = new MqBroker();
        broker.createTopic(topic);

        MqProducer<String> producer = broker.createProducer();


        MqMessage<String> message1 = new MqMessage<>(new HashMap<>(), "1 message----");
        MqMessage<String> message2 = new MqMessage<>(new HashMap<>(), "2 message----");

        producer.send(topic, message1);
        producer.send(topic, message2);

        MqConsumer<String> consumer = broker.createConsumer();
        consumer.subscribe(topic);
        System.out.println(consumer.readMessage().getBody());
        System.out.println(consumer.readMessage().getBody());
        consumer.commit();
        System.out.println(consumer.readMessage().getBody());

        MqConsumer<String> consumer2 = broker.createConsumer();
        consumer2.subscribe(topic);
        System.out.println(consumer2.readMessage().getBody());
        System.out.println(consumer2.readMessage().getBody());
        consumer2.commit();
        System.out.println(consumer2.readMessage().getBody());
    }
}
