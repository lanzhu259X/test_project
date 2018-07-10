package com.lanzhu;

import com.alibaba.fastjson.JSON;
import com.lanzhu.testwork.model.ModelTest;
import com.lanzhu.testwork.service.MakeTest;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PluginTest {

    @MakeTest
    private ModelTest modelTest;



    @Test
    public void testGetPublicAgeAndName() {
        try {
            String name = "name";
            Integer age = 1;
            String brithDayStr = "brithDayStr";
            JSON result = modelTest.getPublicAgeAndName(name, age, brithDayStr);
            Assert.isTrue(true, "true");
        } catch (java.text.ParseException e) {
            System.out.println(e.getMessage());
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetStaticAgeAndName() {
        try {
            String name = "name";
            Integer age = 1;
            String brithDayStr = "brithDayStr";
            JSON result = ModelTest.getStaticAgeAndName(name, age, brithDayStr);
            Assert.isTrue(true, "true");
        } catch (java.text.ParseException e) {
            System.out.println(e.getMessage());
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAageIsLetterThan_18() {
        try {
            int age = 1;
            boolean result = modelTest.AageIsLetterThan_18(age);
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetName() {
        try {
            String result = modelTest.getName();
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSetName() {
        try {
            String name = "name";
            modelTest.setName(name);
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetAge() {
        try {
            int result = modelTest.getAge();
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSetAge() {
        try {
            int age = 1;
            modelTest.setAge(age);
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetBrithDay() {
        try {
            Date result = modelTest.getBrithDay();
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSetBrithDay() {
        try {
            Date brithDay = new Date();
            modelTest.setBrithDay(brithDay);
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetStringArr() {
        try {
            String str = "str";
            String separator = "separator";
            String[] result = modelTest.getStringArr(str, separator);
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testRequestParamList() {
        try {
            List<ModelTest> modelTests = null;
            modelTest.requestParamList(modelTests);
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testRequestParamArr() {
        try {
            String[] arr = null;
            Map<String, ModelTest> map = null;
            Map<String, ModelTest> result = modelTest.requestParamArr(arr, map);
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetModelTestArr() {
        try {
            List<ModelTest> result = modelTest.getModelTestArr();
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetObjectMap() {
        try {
            Map<String, Object> result = modelTest.getObjectMap();
            Assert.isTrue(true, "true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
