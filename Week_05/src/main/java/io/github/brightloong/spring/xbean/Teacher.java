package io.github.brightloong.spring.xbean;

import java.io.Serializable;

/**
 * @author BrightLoong
 * @date 2021/2/5 11:31
 * @description
 */

public class Teacher implements Serializable {

    private static final long serialVersionUID = -8409611806997881614L;

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
