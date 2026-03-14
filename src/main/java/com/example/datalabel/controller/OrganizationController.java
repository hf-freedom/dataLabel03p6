package com.example.datalabel.controller;

import com.example.datalabel.common.SIApiPermission;
import com.example.datalabel.common.SIResult;
import com.example.datalabel.entity.Organization;
import com.example.datalabel.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/org")
public class OrganizationController {
    
    @Autowired
    private OrganizationService organizationService;
    
    @GetMapping("/list")
    @SIApiPermission(name = "组织列表", resourceCode = "org")
    public SIResult<List<Organization>> list() {
        return SIResult.success(organizationService.getAll());
    }
    
    @GetMapping("/tree")
    @SIApiPermission(name = "组织树", resourceCode = "org")
    public SIResult<List<Organization>> tree() {
        return SIResult.success(organizationService.getAll());
    }
    
    @GetMapping("/{id}")
    @SIApiPermission(name = "获取组织", resourceCode = "org")
    public SIResult<Organization> getById(@PathVariable Long id) {
        return SIResult.success(organizationService.getById(id));
    }
    
    @PostMapping("/save")
    @SIApiPermission(name = "保存组织", resourceCode = "org")
    public SIResult<Boolean> save(@RequestBody Organization org) {
        return SIResult.success(organizationService.save(org));
    }
    
    @DeleteMapping("/{id}")
    @SIApiPermission(name = "删除组织", resourceCode = "org")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(organizationService.delete(id));
    }
}
