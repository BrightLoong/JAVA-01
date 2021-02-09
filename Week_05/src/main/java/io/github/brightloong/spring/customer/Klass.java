package io.github.brightloong.spring.customer;

import java.util.List;

public class Klass {

    private String id;
    
   private List<Student> students;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Klass{" +
                "id='" + id + '\'' +
                ", students=" + students +
                '}';
    }
}
