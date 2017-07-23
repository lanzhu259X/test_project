package com.lanzhu.testwork.model;

import com.alibaba.fastjson.JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModelTest {

    private String name;
    private int age;
    private Date brithDay;

    private JSON getPrivateAgeAndName(String name, Integer age, Date brithDay) {
        ModelTest modelTest = new ModelTest();
        modelTest.setAge(age);
        modelTest.setBrithDay(brithDay);
        modelTest.setName(name);
        return (JSON) JSON.toJSON(modelTest);
    }

    public JSON getPublicAgeAndName(String name, Integer age, String brithDayStr) throws ParseException, Exception {
        ModelTest modelTest = new ModelTest();
        modelTest.setAge(age);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(brithDayStr);
        modelTest.setBrithDay(date);
        modelTest.setName(name);
        return (JSON) JSON.toJSON(modelTest);
    }

    public static JSON getStaticAgeAndName(String name, Integer age, String brithDayStr) throws ParseException {
        ModelTest modelTest = new ModelTest();
        modelTest.setAge(age);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(brithDayStr);
        modelTest.setBrithDay(date);
        modelTest.setName(name);
        return (JSON) JSON.toJSON(modelTest);
    }

    public String[] getStringArr(String str, String separator) {
        if (str == null || separator == null) {
            return null;
        }
        return str.split(separator);
    }

    public void requestParamList(List<ModelTest> modelTests) {
        System.out.println(JSON.toJSONString(modelTests));
    }

    public Map<String, ModelTest> requestParamArr(String[] arr, Map<String, ModelTest> map) {
        System.out.println(JSON.toJSONString(arr));
        return map;
    }

    public List<ModelTest> getModelTestArr() {
        List<ModelTest> modelTests = new ArrayList<ModelTest>();
        modelTests.add(new ModelTest());
        modelTests.add(new ModelTest());
        return modelTests;
    }

    public Map<String, Object> getObjectMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("1", new Date());
        map.put("2", new ModelTest());
        return map;
    }

    public boolean AageIsLetterThan_18(int age) {
        if (age >= 18) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBrithDay() {
        return brithDay;
    }

    public void setBrithDay(Date brithDay) {
        this.brithDay = brithDay;
    }
}
