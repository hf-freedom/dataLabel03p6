package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String orgName;
    private String orgCode;
    private Long parentId;
    private Integer sort;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
