package io.github.brightloong.spring.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author BrightLoong
 * @date 2021/2/4 14:37
 * @description
 */
@Component("annotationRefPojo")
public class RefPojo {

    @Value("${:ANNOTATION}")
    private String refType;

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }
}
