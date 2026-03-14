package com.example.datalabel.controller;

import com.example.datalabel.common.SIApiPermission;
import com.example.datalabel.common.SIResult;
import com.example.datalabel.entity.Role;
import com.example.datalabel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @GetMapping("/list")
    @SIApiPermission(name = "角色列表", resourceCode = "role")
    public SIResult<List<Role>> list() {
        return SIResult.success(roleService.getAll());
    }
    
    @GetMapping("/{id}")
    @SIApiPermission(name = "获取角色", resourceCode = "role")
    public SIResult<Role> getById(@PathVariable Long id) {
        return SIResult.success(roleService.getById(id));
    }
    
    @PostMapping("/save")
    @SIApiPermission(name = "保存角色", resourceCode = "role")
    public SIResult<Boolean> save(@RequestBody Role role) {
        return SIResult.success(roleService.save(role));
    }
    
    @DeleteMapping("/{id}")
    @SIApiPermission(name = "删除角色", resourceCode = "role")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(roleService.delete(id));
    }
}
