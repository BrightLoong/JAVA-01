package io.github.brightloong.spring.customer;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author BrightLoong
 * @date 2021/2/5 10:55
 * @description
 */
public class SchoolDefinitionParser extends AbstractSingleBeanDefinitionParser {
    /**
     * Determine the bean class corresponding to the supplied {@link Element}.
     * <p>Note that, for application classes, it is generally preferable to
     * override {@link #getBeanClassName} instead, in order to avoid a direct
     * dependence on the bean implementation class. The BeanDefinitionParser
     * and its NamespaceHandler can be used within an IDE plugin then, even
     * if the application classes are not available on the plugin's classpath.
     *
     * @param element the {@code Element} that is being parsed
     * @return the {@link Class} of the bean that is being defined via parsing
     * the supplied {@code Element}, or {@code null} if none
     * @see #getBeanClassName
     */
    @Override
    protected Class<?> getBeanClass(Element element) {
        return School.class;
    }

    /**
     * Parse the supplied {@link Element} and populate the supplied
     * {@link BeanDefinitionBuilder} as required.
     * <p>The default implementation does nothing.
     *
     * @param element the XML element being parsed
     * @param builder used to define the {@code BeanDefinition}
     */
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String id = element.getAttribute("id");
        if (StringUtils.hasText(id)) {
            builder.addPropertyValue("id", id);
        }
        NodeList childNodes = element.getChildNodes();
        int length = childNodes.getLength();
        if (length == 0) {
            return;
        }
        ManagedList<Object> refs = new ManagedList<>();

        for (int i = 0; i < length; i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
                if ("class-ref".equals(node.getNodeName()) || "klass-ref".equals(node.getLocalName())) {
                    String nodeValue = ((Element) node).getAttribute("bean");
                    RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(nodeValue);
                    refs.add(runtimeBeanReference);
                }
            }
        }
        builder.addPropertyValue("classes", refs);
    }
}
