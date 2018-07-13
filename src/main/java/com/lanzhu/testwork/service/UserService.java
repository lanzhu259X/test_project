package com.lanzhu.testwork.service;

import com.lanzhu.testwork.constants.IdentifierType;
import com.lanzhu.testwork.dao.UserAuthMapper;
import com.lanzhu.testwork.dao.UserMapper;
import com.lanzhu.testwork.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthMapper userAuthMapper;

    public User getUserByPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            log.warn("get user by phone but param phone is empty.");
            throw new IllegalArgumentException("Request Param phone is empty.");
        }
        if (phone.length() != 11) {
            log.warn("request param phone format is error: {}", phone);
            throw new IllegalArgumentException("Request param phone format is error.");
        }
        return userMapper.selectByPhone(phone);
    }

    /**
     * 登录校验
     * @param identifier
     * @param type
     * @param credential
     * @return
     */
    public boolean validateCredential(String identifier, IdentifierType type, String credential) {
        return true;
    }



}
