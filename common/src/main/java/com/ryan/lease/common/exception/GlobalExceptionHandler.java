package com.ryan.lease.common.exception;

import com.ryan.lease.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理器方法
     * 用于捕获所有未处理的异常，并返回统一的错误响应
     *
     * @param e 异常对象，类型为Exception，表示捕获到的异常
     * @return Result对象，表示处理异常后的结果，其中包含错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        // 打印异常的堆栈跟踪信息，便于问题排查
        e.printStackTrace();
        // 返回一个表示失败的Result对象，告知客户端发生了错误
        return Result.fail();
    }

    /**
     * 全局异常处理器方法：专门处理LeaseException，
     * 虽然这两个方法方法名一样，但是会有精确匹配原则，抛出LeaseException就走这个方法
     * @param e LeaseException异常对象
     * @return
     */
    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result handle(LeaseException e) {
        e.printStackTrace();
        return Result.fail(e.getCode(), e.getMessage());
    }
}
