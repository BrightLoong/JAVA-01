package io.github.brightloong.mymq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * @author BrightLoong
 * @date 2021/4/17 15:12
 * @description
 */

@AllArgsConstructor
@Data
public class MqMessage<T> {

    private HashMap<String,Object> headers;

    private T body;
}
