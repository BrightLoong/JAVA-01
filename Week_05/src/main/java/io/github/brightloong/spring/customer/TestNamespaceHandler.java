package io.github.brightloong.spring.customer;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author BrightLoong
 * @date 2021/2/5 11:21
 * @description 注册自定义标签解析器
 */
public class TestNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("student", new StudentDefinitionParser());
        registerBeanDefinitionParser("klass", new KlassDefinitionParser());
        registerBeanDefinitionParser("school", new SchoolDefinitionParser());
    }
}
