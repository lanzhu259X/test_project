package com.lanzhu.testwork.aop;

import com.alibaba.fastjson.JSONObject;
import com.lanzhu.testwork.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static ThreadLocal<ThreadLog> threadLog = new ThreadLocal<ThreadLog>();

    @Pointcut("execution(public * com.lanzhu.testwork.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ThreadLog info = new ThreadLog();
        threadLog.set(info);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String URL = request.getRequestURL().toString();
        String HTTP_METHOD = request.getMethod();
        String IP = request.getRemoteAddr();
        String CLASS_METHOD = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String ARGS = Arrays.toString(joinPoint.getArgs());
        String USER = "";
        String REQUEST_TIME = DATE_FORMAT.format(new Date(info.getStartTime()));

        for (Object item : joinPoint.getArgs()) {
            if (item instanceof User) {
                User user = (User) item;
                USER = user.getId() + ":" + user.getUsername() + ":" + user.getPhone();
                break;
            }
        }

        JSONObject json = new JSONObject();
        json.put("LOG_ID", info.getLogId());
        json.put("URL", URL);
        json.put("HTTP_METHOD", HTTP_METHOD);
        json.put("IP", IP);
        json.put("CLASS_METHOD", CLASS_METHOD);
        json.put("ARGS", ARGS);
        json.put("USER", USER);
        json.put("REQUEST_TIME", REQUEST_TIME);

        log.info(json.toString());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        JSONObject json = new JSONObject();
        ThreadLog info = threadLog.get();
        json.put("LOG_ID", info == null ? "" : info.getLogId());
        json.put("RESPONSE", ret);
        json.put("SPEND_TIME", info == null ? "unknown" : System.currentTimeMillis() - info.getStartTime());

        log.info(json.toString());
    }

    @AfterThrowing(throwing = "error", pointcut = "webLog()")
    public void doAfterThrowing(Throwable error) throws Throwable {
        String errorMessage = error.toString();
        JSONObject json = new JSONObject();
        ThreadLog info = threadLog.get();
        json.put("LOG_ID", info == null ? "" : info.getLogId());
        json.put("RESPONSE", errorMessage);
        json.put("ERROR_INFO", error);
        json.put("SPEND_TIME", info == null ? "unknown" : System.currentTimeMillis() - info.getStartTime());

        log.info(json.toString());
    }

    @Getter
    public class ThreadLog {
        private String logId;
        private long startTime;

        public ThreadLog() {
            this.logId = UUID.randomUUID().toString();
            this.startTime = System.currentTimeMillis();
        }
        public ThreadLog(String logId) {
            this.logId = logId;
            this.startTime = System.currentTimeMillis();
        }
        public ThreadLog(String logId, long startTime) {
            this.logId = logId;
            this.startTime = startTime;
        }
    }
}
