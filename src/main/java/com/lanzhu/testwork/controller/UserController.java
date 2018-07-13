package com.lanzhu.testwork.controller;

import com.alibaba.fastjson.JSON;
import com.lanzhu.testwork.model.User;
import com.lanzhu.testwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/phone/{phone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getByPhone(@PathVariable String phone) throws Exception {
        User user = userService.getUserByPhone(phone);
        return ResponseEntity.ok().body(JSON.toJSONString(user));
    }
}
