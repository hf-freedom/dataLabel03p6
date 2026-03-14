package com.example.datalabel.controller;

import com.example.datalabel.common.Result;
import com.example.datalabel.common.annotation.SIRequiredPermission;
import com.example.datalabel.entity.SIApi;
import com.example.datalabel.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SIRequiredPermission
@RestController
@RequestMapping("/api/api")
public class ApiController {
    
    @Autowired
    private ApiService apiService;
    
    @GetMapping("/list")
    public Result<List<SIApi>> list() {
        return Result.success(apiService.getAll());
    }
    
    @GetMapping("/{id}")
    public Result<SIApi> getById(@PathVariable Long id) {
        return Result.success(apiService.getById(id));
    }
    
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SIApi api) {
        return Result.success(apiService.save(api));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(apiService.delete(id));
    }
}
