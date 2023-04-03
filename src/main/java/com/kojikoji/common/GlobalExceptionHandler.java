package com.kojikoji.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName GlobalExceptionHandler
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/26 9:51
 * @Version
 */

@Slf4j
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(Exception ex){
        String message = ex.getMessage();
        log.info(message);
        if(message.contains("Duplicate entry")){
            String[] s = message.split(" ");
            return R.error(s[9] + "已存在");
        }
        return R.error("失败了");
    }

    @ExceptionHandler(CustomerException.class)
    public R<String> customerExceptionHandler(Exception ex){
        String message = ex.getMessage();
        log.info(message);

        return R.error(message);
    }
}
