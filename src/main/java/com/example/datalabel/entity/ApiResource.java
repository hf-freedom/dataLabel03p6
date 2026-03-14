package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApiResource implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long apiId;
    private Long menuId;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
