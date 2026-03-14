package com.example.datalabel.controller;

import com.example.datalabel.annotation.SIRequirePermission;
import com.example.datalabel.cache.LocalCache;
import com.example.datalabel.common.SIResult;
import com.example.datalabel.entity.ApiInfo;
import com.example.datalabel.entity.ApiResource;
import com.example.datalabel.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private LocalCache localCache;

    @GetMapping("/list")
    public String listPage(Model model) {
        List<ApiInfo> apiInfos = localCache.getAllApiInfos();
        List<Menu> menus = localCache.getAllMenus();
        model.addAttribute("apis", apiInfos);
        model.addAttribute("menus", menus);
        return "api-list";
    }

    @GetMapping("/resource")
    public String resourcePage(Model model) {
        List<ApiInfo> apiInfos = localCache.getAllApiInfos();
        List<Menu> menus = localCache.getAllMenus();
        List<ApiResource> apiResources = localCache.getAllApiResources();

        Map<Long, String> apiNameMap = apiInfos.stream()
                .collect(Collectors.toMap(ApiInfo::getId, ApiInfo::getApiName));
        Map<Long, String> menuNameMap = menus.stream()
                .collect(Collectors.toMap(Menu::getId, Menu::getMenuName));

        model.addAttribute("apis", apiInfos);
        model.addAttribute("menus", menus);
        model.addAttribute("apiResources", apiResources);
        model.addAttribute("apiNameMap", apiNameMap);
        model.addAttribute("menuNameMap", menuNameMap);
        return "api-resource";
    }

    @GetMapping("/info/list")
    @ResponseBody
    @SIRequirePermission("api:list")
    public SIResult<List<ApiInfo>> list() {
        return SIResult.success(localCache.getAllApiInfos());
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    @SIRequirePermission("api:view")
    public SIResult<ApiInfo> getById(@PathVariable Long id) {
        return SIResult.success(localCache.getApiInfo(id));
    }

    @PostMapping("/info/save")
    @ResponseBody
    @SIRequirePermission("api:save")
    public SIResult<Boolean> save(@RequestBody ApiInfo apiInfo) {
        if (apiInfo.getId() == null) {
            apiInfo.setId(localCache.generateApiInfoId());
            apiInfo.setCreateTime(LocalDateTime.now());
        }
        apiInfo.setUpdateTime(LocalDateTime.now());
        if (apiInfo.getStatus() == null) {
            apiInfo.setStatus(1);
        }
        localCache.putApiInfo(apiInfo);
        return SIResult.success(true);
    }

    @DeleteMapping("/info/{id}")
    @ResponseBody
    @SIRequirePermission("api:delete")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        localCache.removeApiInfo(id);
        return SIResult.success(true);
    }

    @GetMapping("/resource/list")
    @ResponseBody
    @SIRequirePermission("api:resource:list")
    public SIResult<List<Map<String, Object>>> resourceList() {
        List<ApiResource> apiResources = localCache.getAllApiResources();
        List<Map<String, Object>> result = new ArrayList<>();

        for (ApiResource ar : apiResources) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", ar.getId());
            map.put("apiId", ar.getApiId());
            map.put("menuId", ar.getMenuId());
            map.put("description", ar.getDescription());
            map.put("status", ar.getStatus());

            ApiInfo apiInfo = localCache.getApiInfo(ar.getApiId());
            Menu menu = localCache.getMenu(ar.getMenuId());

            map.put("apiName", apiInfo != null ? apiInfo.getApiName() : "未知");
            map.put("apiUrl", apiInfo != null ? apiInfo.getApiUrl() : "");
            map.put("menuName", menu != null ? menu.getMenuName() : "未知");

            result.add(map);
        }

        return SIResult.success(result);
    }

    @PostMapping("/resource/save")
    @ResponseBody
    @SIRequirePermission("api:resource:save")
    public SIResult<Boolean> saveResource(@RequestBody ApiResource apiResource) {
        if (apiResource.getId() == null) {
            apiResource.setId(localCache.generateApiResourceId());
            apiResource.setCreateTime(LocalDateTime.now());
        }
        apiResource.setUpdateTime(LocalDateTime.now());
        if (apiResource.getStatus() == null) {
            apiResource.setStatus(1);
        }
        localCache.putApiResource(apiResource);
        return SIResult.success(true);
    }

    @DeleteMapping("/resource/{id}")
    @ResponseBody
    @SIRequirePermission("api:resource:delete")
    public SIResult<Boolean> deleteResource(@PathVariable Long id) {
        localCache.removeApiResource(id);
        return SIResult.success(true);
    }

    @GetMapping("/resource/byApi/{apiId}")
    @ResponseBody
    @SIRequirePermission("api:resource:view")
    public SIResult<List<ApiResource>> getResourcesByApiId(@PathVariable Long apiId) {
        return SIResult.success(localCache.getApiResourcesByApiId(apiId));
    }

    @GetMapping("/resource/byMenu/{menuId}")
    @ResponseBody
    @SIRequirePermission("api:resource:view")
    public SIResult<List<ApiResource>> getResourcesByMenuId(@PathVariable Long menuId) {
        return SIResult.success(localCache.getApiResourcesByMenuId(menuId));
    }
}
