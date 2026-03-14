package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String roleName;
    private String roleCode;
    private String description;
    private Integer status;
    private List<Long> menuIds;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
