package com.lanzhu.testwork.rabbitmq;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class MessageB extends Message implements Serializable {

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("MessageB: {");
        sb.append("id=" + getId());
        sb.append(", info=" + getInfo());
        sb.append(", ttl=" + getTtl());
        sb.append(", createTime=" + (getCreateTime() == null ? "" : format.format(getCreateTime())));
        sb.append("}");
        return sb.toString();
    }
}
