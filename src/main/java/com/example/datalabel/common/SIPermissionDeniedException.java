package com.example.datalabel.common;

public class SIPermissionDeniedException extends RuntimeException {
    
    public SIPermissionDeniedException(String message) {
        super(message);
    }
    
    public SIPermissionDeniedException() {
        super("权限不足，无法访问该资源");
    }
}
