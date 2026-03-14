package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Integer status;
    private Long orgId;
    private List<Long> roleIds;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
