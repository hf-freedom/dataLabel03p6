package com.example.datalabel.exception;

import com.example.datalabel.common.SIPermissionDeniedException;
import com.example.datalabel.common.SIResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SIGlobalExceptionHandler {
    
    @ExceptionHandler(SIPermissionDeniedException.class)
    public SIResult<Void> handlePermissionDenied(SIPermissionDeniedException e) {
        return SIResult.forbidden(e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public SIResult<Void> handleException(Exception e) {
        return SIResult.error("系统错误：" + e.getMessage());
    }
}
