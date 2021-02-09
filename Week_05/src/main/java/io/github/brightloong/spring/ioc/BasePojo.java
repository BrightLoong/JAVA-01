package io.github.brightloong.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author BrightLoong
 * @date 2021/2/4 14:24
 * @description
 */
@Component("annotationBasePojo")
public class BasePojo {

    @Qualifier("annotationRefPojo")
    @Autowired
    private RefPojo refPojo;

    public void sayRefType() {
        System.out.println(refPojo.getRefType());
    }

    public RefPojo getRefPojo() {
        return refPojo;
    }

    public void setRefPojo(RefPojo refPojo) {
        this.refPojo = refPojo;
    }
}
