package com.bear.admin.config;

import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 16:16.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * feignClient调用异常，将服务的异常和http状态码解析
     *
     * @param exception
     * @param response
     * @return
     */
    @ExceptionHandler({ FeignException.class })
    public Map<String, Object> feignException(FeignException exception, HttpServletResponse response) {
        int httpStatus = exception.status();
        int status = 500;
        if (httpStatus >= status) {
            log.error("feignClient调用异常", exception);
        }

        Map<String, Object> data = new HashMap<>(8);

        String msg = exception.getMessage();

        if (!StringUtils.isEmpty(msg)) {
            int index = msg.indexOf("\n");
            if (index > 0) {
                String string = msg.substring(index);
                if (!StringUtils.isEmpty(string)) {
                    JSONObject json = JSONObject.parseObject(string.trim());
                    data.putAll(json.getInnerMap());
                }
            }
        }
        if (data.isEmpty()) {
            data.put("message", msg);
        }

        data.put("code", httpStatus + "");

        response.setStatus(httpStatus);

        return data;
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> badRequestException(IllegalArgumentException exception) {
        Map<String, Object> data = new HashMap<>(8);
        data.put("code", HttpStatus.BAD_REQUEST.value());
        data.put("message", exception.getMessage());

        return data;
    }
}