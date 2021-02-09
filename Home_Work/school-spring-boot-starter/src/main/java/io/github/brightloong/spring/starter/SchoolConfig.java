package io.github.brightloong.spring.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author BrightLoong
 * @date 2021/2/8 11:16
 * @description
 */
@Configuration
@EnableConfigurationProperties(SchoolProperties.class)
public class SchoolConfig {
    @Autowired
    private SchoolProperties schoolProperties;

    @Bean(name = "school")
    public School schoolService(){
        School school = new School();
        Map<String, List<Student>> klassPropertiesMap = schoolProperties.getKlassPropertiesMap();
        List<Klass> klassList = new ArrayList<>();
        klassPropertiesMap.forEach((k, klassProperties) -> {
            Klass klass = new Klass();
            klass.setId(k);
            klass.setStudents(klassProperties);
            klassList.add(klass);
        });
        school.setClasses(klassList);
        return school;
    }
}
