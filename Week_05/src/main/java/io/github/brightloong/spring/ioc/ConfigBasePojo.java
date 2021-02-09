package io.github.brightloong.spring.ioc;

/**
 * @author BrightLoong
 * @date 2021/2/4 14:24
 * @description
 */
public class ConfigBasePojo {

    private ConfigRefPojo refPojo;

    public void sayRefType() {
        System.out.println(refPojo.getRefType());
    }

    public ConfigRefPojo getRefPojo() {
        return refPojo;
    }

    public void setRefPojo(ConfigRefPojo refPojo) {
        this.refPojo = refPojo;
    }
}
