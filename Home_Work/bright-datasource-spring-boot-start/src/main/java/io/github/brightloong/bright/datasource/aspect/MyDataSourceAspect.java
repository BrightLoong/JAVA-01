package io.github.brightloong.bright.datasource.aspect;

import io.github.brightloong.bright.datasource.annotation.BrightDs;
import io.github.brightloong.bright.datasource.config.BrightDataSources;
import io.github.brightloong.bright.datasource.config.DynamicDataSource;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Random;


@Aspect
public class MyDataSourceAspect {

    @Autowired
    private BrightDataSources brightDataSources;

    private final static Logger logger = LoggerFactory.getLogger(MyDataSourceAspect.class);

    @Around(value = "@annotation(brightDs) || @within(brightDs)", argNames = "point, brightDs")
    public Object setDataSource(ProceedingJoinPoint point, BrightDs brightDs) throws Throwable {
        //因为事务代理会导致获取不到值，所以重新手动获取
        final MethodSignature signature = (MethodSignature) point.getSignature();
        final Class<?> target = point.getTarget().getClass();
        final Method method = signature.getMethod();
        final Class<?> clazz = target.getInterfaces()[0];
        if (method.isAnnotationPresent(BrightDs.class)) {
            brightDs = method.getAnnotation(BrightDs.class);
        } else if (clazz.isAnnotationPresent(BrightDs.class)){
            brightDs = clazz.getAnnotation(BrightDs.class);
        } else {
            //没有注解，直接返回
            return point.proceed(point.getArgs());
        }
        String dataSource = brightDs.value();
        boolean readOnly = brightDs.readOnly();
        if (StringUtils.isNotBlank(dataSource)) {
            DynamicDataSource.setDataSource(dataSource);
        } else if (readOnly) {
            Random random = new Random(System.currentTimeMillis());
            DynamicDataSource.setDataSource(brightDataSources.getSlaves().get(random.nextInt(brightDataSources.getSlaves().size())));
        } else {
            throw new RuntimeException("请检查数据源配置");
        }

        logger.debug("切换数据源为：{}", dataSource);
        Object result;
        try {
             result = point.proceed(point.getArgs());
        } finally {
            DynamicDataSource.reset();
        }
        return result;
    }
}
