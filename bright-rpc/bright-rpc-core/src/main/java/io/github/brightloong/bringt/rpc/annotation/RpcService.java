package io.github.brightloong.bringt.rpc.annotation;

import java.lang.annotation.*;

/**
 * @author BrightLoong
 * @date 2021/3/18 21:25
 * @description
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
    String group();
}
