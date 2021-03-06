package io.github.brightloong.bright.datasource.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

/**
 * @author BrightLoong
 * @date 2021/3/5 11:01
 * @description
 */

@ConfigurationProperties(prefix = "bright")
public class BrightDataSources {
    private Map<String, DataSourceProperties> dataSources = new HashMap<>();

    private String main;

    public Map<String, DataSourceProperties> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, DataSourceProperties> dataSources) {
        this.dataSources = dataSources;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public List<String> getSlaves() {
        Set<String> allDatasource = dataSources.keySet();
        allDatasource.remove(main);
        return new ArrayList<>(allDatasource);
    }
}
