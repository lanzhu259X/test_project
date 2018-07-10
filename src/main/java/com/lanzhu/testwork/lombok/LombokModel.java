package com.lanzhu.testwork.lombok;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString(exclude = {"age", "parent"})
public class LombokModel {

    @NonNull
    private Integer id;

    private String name;

    private int age;

    private LombokModel parent;

    private String desc;

    public String getDesc() {
        System.out.println("get desc method invoke ----> " + this.desc);
        return id + ":" + name + "[" + age + "]" ;
    }
}
