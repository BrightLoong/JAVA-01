package io.github.brightloong.spring.customer;


import java.util.List;

public class School {

    private String id;
    
    private List<Klass> classes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Klass> getClasses() {
        return classes;
    }

    public void setClasses(List<Klass> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "School{" +
                "id='" + id + '\'' +
                ", classes=" + classes +
                '}';
    }
}
