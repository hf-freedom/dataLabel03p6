package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SIApiInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String apiName;
    private String apiPath;
    private String apiMethod;
    private String description;
    private Long resourceId;
    private String resourceType;
    private Integer requireAuth;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
