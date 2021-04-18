package io.github.brightloong.kafka;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author BrightLoong
 * @date 2021/4/16 16:00
 * @description
 */

@Component
@Slf4j
public class Producer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    //自定义topic
    public static final String TOPIC_TEST = "topic.test";

    //
    public static final String TOPIC_GROUP1 = "topic.group1";

    //
    public static final String TOPIC_GROUP2 = "topic.group2";

    public void send(Object obj) {
        String obj2String = JSONObject.toJSONString(obj);
        log.info("准备发送消息为：{}", obj2String);
        //发送消息
        int partitionNum = 0;
        int hCode = obj.hashCode();
        partitionNum = (hCode & 0x7fffffff) % 2;
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_TEST, partitionNum, String.valueOf(partitionNum), obj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.info(TOPIC_TEST + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                //成功的处理
                log.info(TOPIC_TEST + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });


    }
}
