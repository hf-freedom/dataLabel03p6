package com.example.datalabel.controller;

import com.example.datalabel.annotation.SIRequirePermission;
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
    @SIRequirePermission("role:list")
    public SIResult<List<Role>> list() {
        return SIResult.success(roleService.getAll());
    }

    @GetMapping("/{id}")
    @SIRequirePermission("role:view")
    public SIResult<Role> getById(@PathVariable Long id) {
        return SIResult.success(roleService.getById(id));
    }

    @PostMapping("/save")
    @SIRequirePermission("role:save")
    public SIResult<Boolean> save(@RequestBody Role role) {
        return SIResult.success(roleService.save(role));
    }

    @DeleteMapping("/{id}")
    @SIRequirePermission("role:delete")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(roleService.delete(id));
    }
}
