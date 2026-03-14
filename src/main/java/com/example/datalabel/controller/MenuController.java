package com.example.datalabel.controller;

import com.example.datalabel.annotation.SIRequirePermission;
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
    @SIRequirePermission("menu:list")
    public SIResult<List<Menu>> list() {
        return SIResult.success(menuService.getAll());
    }

    @GetMapping("/tree")
    @SIRequirePermission("menu:view")
    public SIResult<List<Menu>> tree() {
        return SIResult.success(menuService.getAll());
    }

    @GetMapping("/{id}")
    @SIRequirePermission("menu:view")
    public SIResult<Menu> getById(@PathVariable Long id) {
        return SIResult.success(menuService.getById(id));
    }

    @PostMapping("/save")
    @SIRequirePermission("menu:save")
    public SIResult<Boolean> save(@RequestBody Menu menu) {
        return SIResult.success(menuService.save(menu));
    }

    @DeleteMapping("/{id}")
    @SIRequirePermission("menu:delete")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(menuService.delete(id));
    }
}
