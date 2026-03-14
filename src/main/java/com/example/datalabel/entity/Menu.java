package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String menuName;
    private String menuCode;
    private Long parentId;
    private String menuUrl;
    private String menuIcon;
    private Integer menuType;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
