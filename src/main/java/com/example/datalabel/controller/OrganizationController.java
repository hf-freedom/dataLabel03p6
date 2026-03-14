package com.example.datalabel.controller;

import com.example.datalabel.annotation.SIRequirePermission;
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
    @SIRequirePermission("org:list")
    public SIResult<List<Organization>> list() {
        return SIResult.success(organizationService.getAll());
    }

    @GetMapping("/tree")
    @SIRequirePermission("org:view")
    public SIResult<List<Organization>> tree() {
        return SIResult.success(organizationService.getAll());
    }

    @GetMapping("/{id}")
    @SIRequirePermission("org:view")
    public SIResult<Organization> getById(@PathVariable Long id) {
        return SIResult.success(organizationService.getById(id));
    }

    @PostMapping("/save")
    @SIRequirePermission("org:save")
    public SIResult<Boolean> save(@RequestBody Organization org) {
        return SIResult.success(organizationService.save(org));
    }

    @DeleteMapping("/{id}")
    @SIRequirePermission("org:delete")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(organizationService.delete(id));
    }
}
