package com.example.datalabel.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class SIApi implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String apiPath;
    private String apiMethod;
    private String apiName;
    private String description;
    private List<Long> menuIds;
}
