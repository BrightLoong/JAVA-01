package io.github.brightloong.spring.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author BrightLoong
 * @date 2021/2/8 11:06
 * @description
 */
@ConfigurationProperties(prefix = "school")
public class SchoolProperties {

    private Map<String, List<Student>> KlassPropertiesMap;


    public Map<String, List<Student>> getKlassPropertiesMap() {
        return KlassPropertiesMap;
    }

    public void setKlassPropertiesMap(Map<String, List<Student>> klassPropertiesMap) {
        KlassPropertiesMap = klassPropertiesMap;
    }

    static class KlassProperties {
        private Map<String, Student> studentMap;

        public Map<String, Student> getStudentMap() {
            return studentMap;
        }

        public void setStudentMap(Map<String, Student> studentMap) {
            this.studentMap = studentMap;
        }
    }
}
