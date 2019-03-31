package com.pyp.ad.advice;

import com.pyp.ad.exception.AdException;
import com.pyp.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:统一的异常处理类
 * @author: yy
 * @data: Created in  2019/3/31 21:51
 * @modifier:
 * @version: V1.0
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handleAdException(HttpServletRequest request, AdException ex) {
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }
}
