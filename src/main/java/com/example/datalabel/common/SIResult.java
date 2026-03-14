package com.example.datalabel.common;

import lombok.Data;

@Data
public class SIResult<T> {
    private int code;
    private String message;
    private T data;
    
    public static <T> SIResult<T> success(T data) {
        SIResult<T> result = new SIResult<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
    
    public static <T> SIResult<T> success() {
        return success(null);
    }
    
    public static <T> SIResult<T> error(String message) {
        SIResult<T> result = new SIResult<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
    
    public static <T> SIResult<T> error(int code, String message) {
        SIResult<T> result = new SIResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
