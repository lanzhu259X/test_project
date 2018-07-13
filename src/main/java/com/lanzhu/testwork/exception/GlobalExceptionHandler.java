package com.lanzhu.testwork.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lanzhu.testwork.aop.WebLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     * HTTP_STATUS 400
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity<JSON> handleBizException(HttpServletRequest request, BizException e) {
        return ResponseEntity.badRequest().body(getResult(request, e.getErrorCode() == null ? ErrorCode.SYS_ERROR : e.getErrorCode(), e.getExtra()));
    }

    /**
     * 业务
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BizRuntimeException.class)
    @ResponseBody
    public ResponseEntity<JSON> handleBizRuntimeException(HttpServletRequest request, BizRuntimeException e) {
        JSON result = getResult(request, e.getErrorCode() == null ? ErrorCode.SYS_ERROR : e.getErrorCode(), e.getExtra());
        log.error("BizRuntimeException.", e);
        return new ResponseEntity<JSON>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 未定义异常
     * HttpStatus 500
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<JSON> handleDefaultException(HttpServletRequest request, Exception e) {
        JSON result = getResult(request, ErrorCode.SYS_ERROR, e.toString());
        log.error("undefined exception.", e);
        return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private JSON getResult(HttpServletRequest request, ErrorCode errorCode, Object extra) {
        String url = request.getRequestURL().toString();
        WebLogAspect.ThreadLog threadLog = WebLogAspect.threadLog.get();
        log.warn("Request action url:{} LogId:{} have an Exception: {}", url, threadLog == null ? "" : threadLog.getLogId(), errorCode.toString());
        JSONObject result = new JSONObject();
        result.put("errorCode", errorCode.getErrorCode());
        result.put("errorMessage", errorCode.getErrorMessage());
        result.put("errorDisplay", errorCode.getErrorDisplay());
        result.put("logId", threadLog == null ? "" : threadLog.getLogId());
        result.put("extra", getExtraJson(extra));
        return (JSON) result;
    }

    private JSON getExtraJson(Object extra) {
        if (extra == null) {
            return null;
        }
        JSON result = null;
        if (extra instanceof String) {
            JSONObject extraInfo = new JSONObject();
            extraInfo.put("message", extra);
            result = extraInfo;
        }else {
            try{
                result = (JSON) JSON.toJSON(extra);
            }catch (Exception e) {
                log.error("extra parse to json fail, extra:{}", extra.toString());
                JSONObject extraInfo = new JSONObject();
                extraInfo.put("message", extra);
                result = extraInfo;
            }
        }
        return result;
    }

}
