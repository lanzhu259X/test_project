package com.lanzhu.testwork.rabbitmq;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public class Message {

    private Integer id;

    private String info;

    private Long ttl; //有效时间

    private Date createTime;


}
