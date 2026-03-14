package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApiInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String apiName;
    private String apiUrl;
    private String apiMethod;
    private String controllerClass;
    private String controllerMethod;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
