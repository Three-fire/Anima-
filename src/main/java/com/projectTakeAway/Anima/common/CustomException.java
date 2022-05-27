package com.projectTakeAway.Anima.common;

/**
 * @author CodemyMind
 * @Date 2022/5/17 10:39 AM
 * Description：             自定义业务异常
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
