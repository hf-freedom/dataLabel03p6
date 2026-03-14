package com.example.datalabel.controller;

import com.example.datalabel.common.SIApiPermission;
import com.example.datalabel.common.SIResult;
import com.example.datalabel.entity.Menu;
import com.example.datalabel.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @GetMapping("/list")
    @SIApiPermission(name = "菜单列表", resourceCode = "menu")
    public SIResult<List<Menu>> list() {
        return SIResult.success(menuService.getAll());
    }
    
    @GetMapping("/tree")
    @SIApiPermission(name = "菜单树", resourceCode = "menu")
    public SIResult<List<Menu>> tree() {
        return SIResult.success(menuService.getAll());
    }
    
    @GetMapping("/{id}")
    @SIApiPermission(name = "获取菜单", resourceCode = "menu")
    public SIResult<Menu> getById(@PathVariable Long id) {
        return SIResult.success(menuService.getById(id));
    }
    
    @PostMapping("/save")
    @SIApiPermission(name = "保存菜单", resourceCode = "menu")
    public SIResult<Boolean> save(@RequestBody Menu menu) {
        return SIResult.success(menuService.save(menu));
    }
    
    @DeleteMapping("/{id}")
    @SIApiPermission(name = "删除菜单", resourceCode = "menu")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(menuService.delete(id));
    }
}
