package com.example.datalabel.controller;

import com.example.datalabel.common.Result;
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
    public Result<List<Organization>> list() {
        return Result.success(organizationService.getAll());
    }
    
    @GetMapping("/tree")
    public Result<List<Organization>> tree() {
        return Result.success(organizationService.getAll());
    }
    
    @GetMapping("/{id}")
    public Result<Organization> getById(@PathVariable Long id) {
        return Result.success(organizationService.getById(id));
    }
    
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Organization org) {
        return Result.success(organizationService.save(org));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(organizationService.delete(id));
    }
}
