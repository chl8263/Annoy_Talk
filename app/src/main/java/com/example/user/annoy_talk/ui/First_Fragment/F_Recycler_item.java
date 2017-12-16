package com.example.user.annoy_talk.ui.First_Fragment;

/**
 * Created by choi on 2017-11-27.
 */

public class F_Recycler_item {
    private String name;
    private String age;
    private String sex;

    public F_Recycler_item(String name, String age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
