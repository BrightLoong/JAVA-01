package io.github.brightloong.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BrightLoong
 * @date 2021/4/16 16:18
 * @description
 */
@RestController
@RequestMapping("kafka")
public class KafkaController {
    @Autowired
    private Producer producer;

    @RequestMapping("send")
    public String sendMessage(@RequestParam String message) {
        producer.send(message);
        return "发送成功";
    }
}
