package com.example.datalabel.controller;

import com.example.datalabel.cache.SILocalCache;
import com.example.datalabel.common.SIApiPermission;
import com.example.datalabel.common.SIResult;
import com.example.datalabel.entity.Menu;
import com.example.datalabel.entity.SIApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/api")
public class SIApiController {
    
    @Autowired
    private SILocalCache siLocalCache;
    
    @GetMapping("/list")
    @SIApiPermission(name = "API列表", resourceCode = "api", requireAuth = false)
    public SIResult<List<SIApiInfo>> list() {
        return SIResult.success(siLocalCache.getAllApis());
    }
    
    @GetMapping("/{id}")
    @SIApiPermission(name = "获取API", resourceCode = "api", requireAuth = false)
    public SIResult<SIApiInfo> getById(@PathVariable Long id) {
        return SIResult.success(siLocalCache.getApi(id));
    }
    
    @GetMapping("/byResource/{resourceId}")
    @SIApiPermission(name = "按资源获取API", resourceCode = "api", requireAuth = false)
    public SIResult<List<SIApiInfo>> getByResourceId(@PathVariable Long resourceId) {
        return SIResult.success(siLocalCache.getApisByResourceId(resourceId));
    }
    
    @PostMapping("/save")
    @SIApiPermission(name = "保存API", resourceCode = "api", requireAuth = false)
    public SIResult<Boolean> save(@RequestBody SIApiInfo api) {
        if (api.getId() == null) {
            api.setId(siLocalCache.generateApiId());
        }
        api.setCreateTime(LocalDateTime.now());
        api.setUpdateTime(LocalDateTime.now());
        siLocalCache.putApi(api);
        return SIResult.success(true);
    }
    
    @PostMapping("/bindResource")
    @SIApiPermission(name = "绑定资源", resourceCode = "api", requireAuth = false)
    public SIResult<Boolean> bindResource(@RequestBody BindRequest request) {
        SIApiInfo api = siLocalCache.getApi(request.getApiId());
        if (api == null) {
            return SIResult.error("API不存在");
        }
        api.setResourceId(request.getResourceId());
        api.setUpdateTime(LocalDateTime.now());
        siLocalCache.putApi(api);
        return SIResult.success(true);
    }
    
    @DeleteMapping("/{id}")
    @SIApiPermission(name = "删除API", resourceCode = "api", requireAuth = false)
    public SIResult<Boolean> delete(@PathVariable Long id) {
        siLocalCache.removeApi(id);
        return SIResult.success(true);
    }
    
    @GetMapping("/resources")
    @SIApiPermission(name = "获取资源列表", resourceCode = "api", requireAuth = false)
    public SIResult<List<Menu>> getResources() {
        return SIResult.success(siLocalCache.getAllMenus());
    }
    
    @GetMapping("/unbound")
    @SIApiPermission(name = "未绑定API列表", resourceCode = "api", requireAuth = false)
    public SIResult<List<SIApiInfo>> getUnboundApis() {
        List<SIApiInfo> allApis = siLocalCache.getAllApis();
        List<SIApiInfo> unboundApis = allApis.stream()
                .filter(api -> api.getResourceId() == null)
                .collect(Collectors.toList());
        return SIResult.success(unboundApis);
    }
    
    public static class BindRequest {
        private Long apiId;
        private Long resourceId;
        
        public Long getApiId() { return apiId; }
        public void setApiId(Long apiId) { this.apiId = apiId; }
        public Long getResourceId() { return resourceId; }
        public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    }
}
