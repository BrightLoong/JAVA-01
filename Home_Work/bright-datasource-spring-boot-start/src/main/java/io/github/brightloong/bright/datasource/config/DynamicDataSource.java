package io.github.brightloong.bright.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();

    public static void setDataSource(String name) {
        dataSourceHolder.set(name);
    }

    public static String getDataSource() {
        return dataSourceHolder.get();
    }

    @Override
    protected Object determineCurrentLookupKey() {

        return DynamicDataSource.getDataSource();
    }

    public static void reset() {
        dataSourceHolder.remove();
    }
}
