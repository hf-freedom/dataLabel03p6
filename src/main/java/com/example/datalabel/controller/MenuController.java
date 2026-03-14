package com.example.datalabel.controller;

import com.example.datalabel.common.Result;
import com.example.datalabel.common.annotation.SIRequiredPermission;
import com.example.datalabel.entity.Menu;
import com.example.datalabel.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SIRequiredPermission
@RestController
@RequestMapping("/api/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @GetMapping("/list")
    public Result<List<Menu>> list() {
        return Result.success(menuService.getAll());
    }
    
    @GetMapping("/tree")
    public Result<List<Menu>> tree() {
        return Result.success(menuService.getAll());
    }
    
    @GetMapping("/{id}")
    public Result<Menu> getById(@PathVariable Long id) {
        return Result.success(menuService.getById(id));
    }
    
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Menu menu) {
        return Result.success(menuService.save(menu));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(menuService.delete(id));
    }
}
