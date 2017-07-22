package com.lanzhu.testwork.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<?> helloWork() {
        JSONObject res = new JSONObject();
        res.put("result", "Hello Work");
        return ResponseEntity.ok().body(res);
    }
}
