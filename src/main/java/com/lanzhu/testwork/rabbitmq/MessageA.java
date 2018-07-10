package com.lanzhu.testwork.rabbitmq;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
public class MessageA implements Serializable {

    private Integer id;

    private String info;

    private Long ttl; //有效时间

    private Date createTime;

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "MessageA:{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", ttl='" + ttl + '\'' +
                ", createTime=" + createTime == null ? "" : format.format(createTime) +
                '}';
    }
}
