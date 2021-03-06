package io.github.brightloong.bright.datasource.annotation;

import java.lang.annotation.*;

/**
 * @author BrightLoong
 * @date 2021/3/5 10:45
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface BrightDs {
    String value() default "";

    /**
     * readOnly = true,并且value为空的情况下会从从库里面进行选择。
     * @return
     */
    boolean readOnly() default false;
}
