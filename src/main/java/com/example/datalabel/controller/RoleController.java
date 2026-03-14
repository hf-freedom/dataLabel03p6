package com.example.datalabel.controller;

import com.example.datalabel.common.Result;
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
    public Result<List<Role>> list() {
        return Result.success(roleService.getAll());
    }
    
    @GetMapping("/{id}")
    public Result<Role> getById(@PathVariable Long id) {
        return Result.success(roleService.getById(id));
    }
    
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Role role) {
        return Result.success(roleService.save(role));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(roleService.delete(id));
    }
}
