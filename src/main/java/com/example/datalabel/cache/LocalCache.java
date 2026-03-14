package com.example.datalabel.cache;

import com.example.datalabel.entity.ApiInfo;
import com.example.datalabel.entity.ApiResource;
import com.example.datalabel.entity.Menu;
import com.example.datalabel.entity.Organization;
import com.example.datalabel.entity.Role;
import com.example.datalabel.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class LocalCache {
    
    private final Map<Long, User> userCache = new ConcurrentHashMap<>();
    private final Map<Long, Role> roleCache = new ConcurrentHashMap<>();
    private final Map<Long, Organization> orgCache = new ConcurrentHashMap<>();
    private final Map<Long, Menu> menuCache = new ConcurrentHashMap<>();
    private final Map<Long, ApiInfo> apiInfoCache = new ConcurrentHashMap<>();
    private final Map<Long, ApiResource> apiResourceCache = new ConcurrentHashMap<>();
    
    private final AtomicLong userIdGenerator = new AtomicLong(1);
    private final AtomicLong roleIdGenerator = new AtomicLong(1);
    private final AtomicLong orgIdGenerator = new AtomicLong(1);
    private final AtomicLong menuIdGenerator = new AtomicLong(1);
    private final AtomicLong apiInfoIdGenerator = new AtomicLong(1);
    private final AtomicLong apiResourceIdGenerator = new AtomicLong(1);
    
    public LocalCache() {
        initDefaultData();
    }
    
    private void initDefaultData() {
        Menu menu1 = new Menu();
        menu1.setId(menuIdGenerator.getAndIncrement());
        menu1.setMenuName("系统管理");
        menu1.setMenuCode("system");
        menu1.setParentId(0L);
        menu1.setMenuUrl("#");
        menu1.setMenuIcon("fa-cog");
        menu1.setMenuType(1);
        menu1.setSort(1);
        menu1.setStatus(1);
        menuCache.put(menu1.getId(), menu1);
        
        Menu menu2 = new Menu();
        menu2.setId(menuIdGenerator.getAndIncrement());
        menu2.setMenuName("用户管理");
        menu2.setMenuCode("user");
        menu2.setParentId(menu1.getId());
        menu2.setMenuUrl("/user/list");
        menu2.setMenuIcon("fa-user");
        menu2.setMenuType(2);
        menu2.setSort(1);
        menu2.setStatus(1);
        menuCache.put(menu2.getId(), menu2);
        
        Menu menu3 = new Menu();
        menu3.setId(menuIdGenerator.getAndIncrement());
        menu3.setMenuName("角色管理");
        menu3.setMenuCode("role");
        menu3.setParentId(menu1.getId());
        menu3.setMenuUrl("/role/list");
        menu3.setMenuIcon("fa-users");
        menu3.setMenuType(2);
        menu3.setSort(2);
        menu3.setStatus(1);
        menuCache.put(menu3.getId(), menu3);
        
        Menu menu4 = new Menu();
        menu4.setId(menuIdGenerator.getAndIncrement());
        menu4.setMenuName("组织机构");
        menu4.setMenuCode("org");
        menu4.setParentId(menu1.getId());
        menu4.setMenuUrl("/org/list");
        menu4.setMenuIcon("fa-building");
        menu4.setMenuType(2);
        menu4.setSort(3);
        menu4.setStatus(1);
        menuCache.put(menu4.getId(), menu4);
        
        Menu menu5 = new Menu();
        menu5.setId(menuIdGenerator.getAndIncrement());
        menu5.setMenuName("菜单管理");
        menu5.setMenuCode("menu");
        menu5.setParentId(menu1.getId());
        menu5.setMenuUrl("/menu/list");
        menu5.setMenuIcon("fa-bars");
        menu5.setMenuType(2);
        menu5.setSort(4);
        menu5.setStatus(1);
        menuCache.put(menu5.getId(), menu5);

        Menu menu6 = new Menu();
        menu6.setId(menuIdGenerator.getAndIncrement());
        menu6.setMenuName("API管理");
        menu6.setMenuCode("api");
        menu6.setParentId(menu1.getId());
        menu6.setMenuUrl("/api/list");
        menu6.setMenuIcon("fa-plug");
        menu6.setMenuType(2);
        menu6.setSort(5);
        menu6.setStatus(1);
        menuCache.put(menu6.getId(), menu6);

        Menu menu7 = new Menu();
        menu7.setId(menuIdGenerator.getAndIncrement());
        menu7.setMenuName("API资源绑定");
        menu7.setMenuCode("api-resource");
        menu7.setParentId(menu1.getId());
        menu7.setMenuUrl("/api/resource");
        menu7.setMenuIcon("fa-link");
        menu7.setMenuType(2);
        menu7.setSort(6);
        menu7.setStatus(1);
        menuCache.put(menu7.getId(), menu7);

        Organization org = new Organization();
        org.setId(orgIdGenerator.getAndIncrement());
        org.setOrgName("总公司");
        org.setOrgCode("HQ");
        org.setParentId(0L);
        org.setSort(1);
        org.setStatus(1);
        orgCache.put(org.getId(), org);
        
        Role role = new Role();
        role.setId(roleIdGenerator.getAndIncrement());
        role.setRoleName("普通用户");
        role.setRoleCode("user");
        role.setDescription("普通用户角色");
        role.setStatus(1);
        role.setMenuIds(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L));
        roleCache.put(role.getId(), role);
        
        initApiData();
    }
    
    private void initApiData() {
        LocalDateTime now = LocalDateTime.now();
        
        ApiInfo api1 = new ApiInfo();
        api1.setId(apiInfoIdGenerator.getAndIncrement());
        api1.setApiName("用户列表");
        api1.setApiUrl("/api/user/list");
        api1.setApiMethod("GET");
        api1.setControllerClass("UserController");
        api1.setControllerMethod("list");
        api1.setDescription("获取用户列表");
        api1.setStatus(1);
        api1.setCreateTime(now);
        api1.setUpdateTime(now);
        apiInfoCache.put(api1.getId(), api1);
        
        ApiInfo api2 = new ApiInfo();
        api2.setId(apiInfoIdGenerator.getAndIncrement());
        api2.setApiName("获取用户详情");
        api2.setApiUrl("/api/user/{id}");
        api2.setApiMethod("GET");
        api2.setControllerClass("UserController");
        api2.setControllerMethod("getById");
        api2.setDescription("根据ID获取用户详情");
        api2.setStatus(1);
        api2.setCreateTime(now);
        api2.setUpdateTime(now);
        apiInfoCache.put(api2.getId(), api2);
        
        ApiInfo api3 = new ApiInfo();
        api3.setId(apiInfoIdGenerator.getAndIncrement());
        api3.setApiName("保存用户");
        api3.setApiUrl("/api/user/save");
        api3.setApiMethod("POST");
        api3.setControllerClass("UserController");
        api3.setControllerMethod("save");
        api3.setDescription("保存用户信息");
        api3.setStatus(1);
        api3.setCreateTime(now);
        api3.setUpdateTime(now);
        apiInfoCache.put(api3.getId(), api3);
        
        ApiInfo api4 = new ApiInfo();
        api4.setId(apiInfoIdGenerator.getAndIncrement());
        api4.setApiName("删除用户");
        api4.setApiUrl("/api/user/{id}");
        api4.setApiMethod("DELETE");
        api4.setControllerClass("UserController");
        api4.setControllerMethod("delete");
        api4.setDescription("删除用户");
        api4.setStatus(1);
        api4.setCreateTime(now);
        api4.setUpdateTime(now);
        apiInfoCache.put(api4.getId(), api4);
        
        ApiInfo api5 = new ApiInfo();
        api5.setId(apiInfoIdGenerator.getAndIncrement());
        api5.setApiName("角色列表");
        api5.setApiUrl("/api/role/list");
        api5.setApiMethod("GET");
        api5.setControllerClass("RoleController");
        api5.setControllerMethod("list");
        api5.setDescription("获取角色列表");
        api5.setStatus(1);
        api5.setCreateTime(now);
        api5.setUpdateTime(now);
        apiInfoCache.put(api5.getId(), api5);
        
        ApiInfo api6 = new ApiInfo();
        api6.setId(apiInfoIdGenerator.getAndIncrement());
        api6.setApiName("保存角色");
        api6.setApiUrl("/api/role/save");
        api6.setApiMethod("POST");
        api6.setControllerClass("RoleController");
        api6.setControllerMethod("save");
        api6.setDescription("保存角色信息");
        api6.setStatus(1);
        api6.setCreateTime(now);
        api6.setUpdateTime(now);
        apiInfoCache.put(api6.getId(), api6);
        
        ApiInfo api7 = new ApiInfo();
        api7.setId(apiInfoIdGenerator.getAndIncrement());
        api7.setApiName("删除角色");
        api7.setApiUrl("/api/role/{id}");
        api7.setApiMethod("DELETE");
        api7.setControllerClass("RoleController");
        api7.setControllerMethod("delete");
        api7.setDescription("删除角色");
        api7.setStatus(1);
        api7.setCreateTime(now);
        api7.setUpdateTime(now);
        apiInfoCache.put(api7.getId(), api7);
        
        ApiInfo api8 = new ApiInfo();
        api8.setId(apiInfoIdGenerator.getAndIncrement());
        api8.setApiName("菜单列表");
        api8.setApiUrl("/api/menu/list");
        api8.setApiMethod("GET");
        api8.setControllerClass("MenuController");
        api8.setControllerMethod("list");
        api8.setDescription("获取菜单列表");
        api8.setStatus(1);
        api8.setCreateTime(now);
        api8.setUpdateTime(now);
        apiInfoCache.put(api8.getId(), api8);
        
        ApiInfo api9 = new ApiInfo();
        api9.setId(apiInfoIdGenerator.getAndIncrement());
        api9.setApiName("保存菜单");
        api9.setApiUrl("/api/menu/save");
        api9.setApiMethod("POST");
        api9.setControllerClass("MenuController");
        api9.setControllerMethod("save");
        api9.setDescription("保存菜单信息");
        api9.setStatus(1);
        api9.setCreateTime(now);
        api9.setUpdateTime(now);
        apiInfoCache.put(api9.getId(), api9);
        
        ApiInfo api10 = new ApiInfo();
        api10.setId(apiInfoIdGenerator.getAndIncrement());
        api10.setApiName("删除菜单");
        api10.setApiUrl("/api/menu/{id}");
        api10.setApiMethod("DELETE");
        api10.setControllerClass("MenuController");
        api10.setControllerMethod("delete");
        api10.setDescription("删除菜单");
        api10.setStatus(1);
        api10.setCreateTime(now);
        api10.setUpdateTime(now);
        apiInfoCache.put(api10.getId(), api10);
        
        ApiInfo api11 = new ApiInfo();
        api11.setId(apiInfoIdGenerator.getAndIncrement());
        api11.setApiName("组织机构列表");
        api11.setApiUrl("/api/org/list");
        api11.setApiMethod("GET");
        api11.setControllerClass("OrganizationController");
        api11.setControllerMethod("list");
        api11.setDescription("获取组织机构列表");
        api11.setStatus(1);
        api11.setCreateTime(now);
        api11.setUpdateTime(now);
        apiInfoCache.put(api11.getId(), api11);
        
        ApiInfo api12 = new ApiInfo();
        api12.setId(apiInfoIdGenerator.getAndIncrement());
        api12.setApiName("保存组织机构");
        api12.setApiUrl("/api/org/save");
        api12.setApiMethod("POST");
        api12.setControllerClass("OrganizationController");
        api12.setControllerMethod("save");
        api12.setDescription("保存组织机构信息");
        api12.setStatus(1);
        api12.setCreateTime(now);
        api12.setUpdateTime(now);
        apiInfoCache.put(api12.getId(), api12);
        
        ApiInfo api13 = new ApiInfo();
        api13.setId(apiInfoIdGenerator.getAndIncrement());
        api13.setApiName("删除组织机构");
        api13.setApiUrl("/api/org/{id}");
        api13.setApiMethod("DELETE");
        api13.setControllerClass("OrganizationController");
        api13.setControllerMethod("delete");
        api13.setDescription("删除组织机构");
        api13.setStatus(1);
        api13.setCreateTime(now);
        api13.setUpdateTime(now);
        apiInfoCache.put(api13.getId(), api13);
        
        ApiResource ar1 = new ApiResource();
        ar1.setId(apiResourceIdGenerator.getAndIncrement());
        ar1.setApiId(1L);
        ar1.setMenuId(2L);
        ar1.setDescription("用户列表-用户管理");
        ar1.setStatus(1);
        ar1.setCreateTime(now);
        ar1.setUpdateTime(now);
        apiResourceCache.put(ar1.getId(), ar1);
        
        ApiResource ar2 = new ApiResource();
        ar2.setId(apiResourceIdGenerator.getAndIncrement());
        ar2.setApiId(2L);
        ar2.setMenuId(2L);
        ar2.setDescription("获取用户详情-用户管理");
        ar2.setStatus(1);
        ar2.setCreateTime(now);
        ar2.setUpdateTime(now);
        apiResourceCache.put(ar2.getId(), ar2);
        
        ApiResource ar3 = new ApiResource();
        ar3.setId(apiResourceIdGenerator.getAndIncrement());
        ar3.setApiId(3L);
        ar3.setMenuId(2L);
        ar3.setDescription("保存用户-用户管理");
        ar3.setStatus(1);
        ar3.setCreateTime(now);
        ar3.setUpdateTime(now);
        apiResourceCache.put(ar3.getId(), ar3);
        
        ApiResource ar4 = new ApiResource();
        ar4.setId(apiResourceIdGenerator.getAndIncrement());
        ar4.setApiId(4L);
        ar4.setMenuId(2L);
        ar4.setDescription("删除用户-用户管理");
        ar4.setStatus(1);
        ar4.setCreateTime(now);
        ar4.setUpdateTime(now);
        apiResourceCache.put(ar4.getId(), ar4);
        
        ApiResource ar5 = new ApiResource();
        ar5.setId(apiResourceIdGenerator.getAndIncrement());
        ar5.setApiId(5L);
        ar5.setMenuId(3L);
        ar5.setDescription("角色列表-角色管理");
        ar5.setStatus(1);
        ar5.setCreateTime(now);
        ar5.setUpdateTime(now);
        apiResourceCache.put(ar5.getId(), ar5);
        
        ApiResource ar6 = new ApiResource();
        ar6.setId(apiResourceIdGenerator.getAndIncrement());
        ar6.setApiId(6L);
        ar6.setMenuId(3L);
        ar6.setDescription("保存角色-角色管理");
        ar6.setStatus(1);
        ar6.setCreateTime(now);
        ar6.setUpdateTime(now);
        apiResourceCache.put(ar6.getId(), ar6);
        
        ApiResource ar7 = new ApiResource();
        ar7.setId(apiResourceIdGenerator.getAndIncrement());
        ar7.setApiId(7L);
        ar7.setMenuId(3L);
        ar7.setDescription("删除角色-角色管理");
        ar7.setStatus(1);
        ar7.setCreateTime(now);
        ar7.setUpdateTime(now);
        apiResourceCache.put(ar7.getId(), ar7);
        
        ApiResource ar8 = new ApiResource();
        ar8.setId(apiResourceIdGenerator.getAndIncrement());
        ar8.setApiId(8L);
        ar8.setMenuId(5L);
        ar8.setDescription("菜单列表-菜单管理");
        ar8.setStatus(1);
        ar8.setCreateTime(now);
        ar8.setUpdateTime(now);
        apiResourceCache.put(ar8.getId(), ar8);
        
        ApiResource ar9 = new ApiResource();
        ar9.setId(apiResourceIdGenerator.getAndIncrement());
        ar9.setApiId(9L);
        ar9.setMenuId(5L);
        ar9.setDescription("保存菜单-菜单管理");
        ar9.setStatus(1);
        ar9.setCreateTime(now);
        ar9.setUpdateTime(now);
        apiResourceCache.put(ar9.getId(), ar9);
        
        ApiResource ar10 = new ApiResource();
        ar10.setId(apiResourceIdGenerator.getAndIncrement());
        ar10.setApiId(10L);
        ar10.setMenuId(5L);
        ar10.setDescription("删除菜单-菜单管理");
        ar10.setStatus(1);
        ar10.setCreateTime(now);
        ar10.setUpdateTime(now);
        apiResourceCache.put(ar10.getId(), ar10);
        
        ApiResource ar11 = new ApiResource();
        ar11.setId(apiResourceIdGenerator.getAndIncrement());
        ar11.setApiId(11L);
        ar11.setMenuId(4L);
        ar11.setDescription("组织机构列表-组织机构");
        ar11.setStatus(1);
        ar11.setCreateTime(now);
        ar11.setUpdateTime(now);
        apiResourceCache.put(ar11.getId(), ar11);
        
        ApiResource ar12 = new ApiResource();
        ar12.setId(apiResourceIdGenerator.getAndIncrement());
        ar12.setApiId(12L);
        ar12.setMenuId(4L);
        ar12.setDescription("保存组织机构-组织机构");
        ar12.setStatus(1);
        ar12.setCreateTime(now);
        ar12.setUpdateTime(now);
        apiResourceCache.put(ar12.getId(), ar12);
        
        ApiResource ar13 = new ApiResource();
        ar13.setId(apiResourceIdGenerator.getAndIncrement());
        ar13.setApiId(13L);
        ar13.setMenuId(4L);
        ar13.setDescription("删除组织机构-组织机构");
        ar13.setStatus(1);
        ar13.setCreateTime(now);
        ar13.setUpdateTime(now);
        apiResourceCache.put(ar13.getId(), ar13);

        // API管理相关API
        ApiInfo api14 = new ApiInfo();
        api14.setId(apiInfoIdGenerator.getAndIncrement());
        api14.setApiName("API列表");
        api14.setApiUrl("/api/info/list");
        api14.setApiMethod("GET");
        api14.setControllerClass("ApiController");
        api14.setControllerMethod("list");
        api14.setDescription("获取API列表");
        api14.setStatus(1);
        api14.setCreateTime(now);
        api14.setUpdateTime(now);
        apiInfoCache.put(api14.getId(), api14);

        ApiInfo api15 = new ApiInfo();
        api15.setId(apiInfoIdGenerator.getAndIncrement());
        api15.setApiName("保存API");
        api15.setApiUrl("/api/info/save");
        api15.setApiMethod("POST");
        api15.setControllerClass("ApiController");
        api15.setControllerMethod("save");
        api15.setDescription("保存API信息");
        api15.setStatus(1);
        api15.setCreateTime(now);
        api15.setUpdateTime(now);
        apiInfoCache.put(api15.getId(), api15);

        ApiInfo api16 = new ApiInfo();
        api16.setId(apiInfoIdGenerator.getAndIncrement());
        api16.setApiName("删除API");
        api16.setApiUrl("/api/info/{id}");
        api16.setApiMethod("DELETE");
        api16.setControllerClass("ApiController");
        api16.setControllerMethod("delete");
        api16.setDescription("删除API");
        api16.setStatus(1);
        api16.setCreateTime(now);
        api16.setUpdateTime(now);
        apiInfoCache.put(api16.getId(), api16);

        ApiInfo api17 = new ApiInfo();
        api17.setId(apiInfoIdGenerator.getAndIncrement());
        api17.setApiName("API资源绑定列表");
        api17.setApiUrl("/api/resource/list");
        api17.setApiMethod("GET");
        api17.setControllerClass("ApiController");
        api17.setControllerMethod("resourceList");
        api17.setDescription("获取API资源绑定列表");
        api17.setStatus(1);
        api17.setCreateTime(now);
        api17.setUpdateTime(now);
        apiInfoCache.put(api17.getId(), api17);

        ApiInfo api18 = new ApiInfo();
        api18.setId(apiInfoIdGenerator.getAndIncrement());
        api18.setApiName("保存API资源绑定");
        api18.setApiUrl("/api/resource/save");
        api18.setApiMethod("POST");
        api18.setControllerClass("ApiController");
        api18.setControllerMethod("saveResource");
        api18.setDescription("保存API资源绑定");
        api18.setStatus(1);
        api18.setCreateTime(now);
        api18.setUpdateTime(now);
        apiInfoCache.put(api18.getId(), api18);

        ApiInfo api19 = new ApiInfo();
        api19.setId(apiInfoIdGenerator.getAndIncrement());
        api19.setApiName("删除API资源绑定");
        api19.setApiUrl("/api/resource/{id}");
        api19.setApiMethod("DELETE");
        api19.setControllerClass("ApiController");
        api19.setControllerMethod("deleteResource");
        api19.setDescription("删除API资源绑定");
        api19.setStatus(1);
        api19.setCreateTime(now);
        api19.setUpdateTime(now);
        apiInfoCache.put(api19.getId(), api19);

        // API管理相关资源绑定
        ApiResource ar14 = new ApiResource();
        ar14.setId(apiResourceIdGenerator.getAndIncrement());
        ar14.setApiId(14L);
        ar14.setMenuId(6L);
        ar14.setDescription("API列表-API管理");
        ar14.setStatus(1);
        ar14.setCreateTime(now);
        ar14.setUpdateTime(now);
        apiResourceCache.put(ar14.getId(), ar14);

        ApiResource ar15 = new ApiResource();
        ar15.setId(apiResourceIdGenerator.getAndIncrement());
        ar15.setApiId(15L);
        ar15.setMenuId(6L);
        ar15.setDescription("保存API-API管理");
        ar15.setStatus(1);
        ar15.setCreateTime(now);
        ar15.setUpdateTime(now);
        apiResourceCache.put(ar15.getId(), ar15);

        ApiResource ar16 = new ApiResource();
        ar16.setId(apiResourceIdGenerator.getAndIncrement());
        ar16.setApiId(16L);
        ar16.setMenuId(6L);
        ar16.setDescription("删除API-API管理");
        ar16.setStatus(1);
        ar16.setCreateTime(now);
        ar16.setUpdateTime(now);
        apiResourceCache.put(ar16.getId(), ar16);

        ApiResource ar17 = new ApiResource();
        ar17.setId(apiResourceIdGenerator.getAndIncrement());
        ar17.setApiId(17L);
        ar17.setMenuId(7L);
        ar17.setDescription("API资源绑定列表-API资源绑定");
        ar17.setStatus(1);
        ar17.setCreateTime(now);
        ar17.setUpdateTime(now);
        apiResourceCache.put(ar17.getId(), ar17);

        ApiResource ar18 = new ApiResource();
        ar18.setId(apiResourceIdGenerator.getAndIncrement());
        ar18.setApiId(18L);
        ar18.setMenuId(7L);
        ar18.setDescription("保存API资源绑定-API资源绑定");
        ar18.setStatus(1);
        ar18.setCreateTime(now);
        ar18.setUpdateTime(now);
        apiResourceCache.put(ar18.getId(), ar18);

        ApiResource ar19 = new ApiResource();
        ar19.setId(apiResourceIdGenerator.getAndIncrement());
        ar19.setApiId(19L);
        ar19.setMenuId(7L);
        ar19.setDescription("删除API资源绑定-API资源绑定");
        ar19.setStatus(1);
        ar19.setCreateTime(now);
        ar19.setUpdateTime(now);
        apiResourceCache.put(ar19.getId(), ar19);
    }
    
    public Long generateUserId() {
        return userIdGenerator.getAndIncrement();
    }
    
    public Long generateRoleId() {
        return roleIdGenerator.getAndIncrement();
    }
    
    public Long generateOrgId() {
        return orgIdGenerator.getAndIncrement();
    }
    
    public Long generateMenuId() {
        return menuIdGenerator.getAndIncrement();
    }
    
    public void putUser(User user) {
        userCache.put(user.getId(), user);
    }
    
    public User getUser(Long id) {
        return userCache.get(id);
    }
    
    public void removeUser(Long id) {
        userCache.remove(id);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(userCache.values());
    }
    
    public User getUserByUsername(String username) {
        return userCache.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    public void putRole(Role role) {
        roleCache.put(role.getId(), role);
    }
    
    public Role getRole(Long id) {
        return roleCache.get(id);
    }
    
    public void removeRole(Long id) {
        roleCache.remove(id);
    }
    
    public List<Role> getAllRoles() {
        return new ArrayList<>(roleCache.values());
    }
    
    public void putOrg(Organization org) {
        orgCache.put(org.getId(), org);
    }
    
    public Organization getOrg(Long id) {
        return orgCache.get(id);
    }
    
    public void removeOrg(Long id) {
        orgCache.remove(id);
    }
    
    public List<Organization> getAllOrgs() {
        return new ArrayList<>(orgCache.values());
    }
    
    public void putMenu(Menu menu) {
        menuCache.put(menu.getId(), menu);
    }
    
    public Menu getMenu(Long id) {
        return menuCache.get(id);
    }
    
    public void removeMenu(Long id) {
        menuCache.remove(id);
    }
    
    public List<Menu> getAllMenus() {
        return new ArrayList<>(menuCache.values());
    }
    
    public List<Menu> getMenusByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return ids.stream()
                .map(menuCache::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    public List<Organization> getOrgsByParentId(Long parentId) {
        return orgCache.values().stream()
                .filter(o -> Objects.equals(o.getParentId(), parentId))
                .collect(Collectors.toList());
    }
    
    public List<Menu> getMenusByParentId(Long parentId) {
        return menuCache.values().stream()
                .filter(m -> Objects.equals(m.getParentId(), parentId))
                .collect(Collectors.toList());
    }
    
    public Long generateApiInfoId() {
        return apiInfoIdGenerator.getAndIncrement();
    }
    
    public Long generateApiResourceId() {
        return apiResourceIdGenerator.getAndIncrement();
    }
    
    public void putApiInfo(ApiInfo apiInfo) {
        apiInfoCache.put(apiInfo.getId(), apiInfo);
    }
    
    public ApiInfo getApiInfo(Long id) {
        return apiInfoCache.get(id);
    }
    
    public void removeApiInfo(Long id) {
        apiInfoCache.remove(id);
    }
    
    public List<ApiInfo> getAllApiInfos() {
        return new ArrayList<>(apiInfoCache.values());
    }
    
    public ApiInfo getApiInfoByUrlAndMethod(String url, String method) {
        return apiInfoCache.values().stream()
                .filter(api -> api.getStatus() == 1 
                        && api.getApiUrl().equals(url) 
                        && api.getApiMethod().equalsIgnoreCase(method))
                .findFirst()
                .orElse(null);
    }
    
    public void putApiResource(ApiResource apiResource) {
        apiResourceCache.put(apiResource.getId(), apiResource);
    }
    
    public ApiResource getApiResource(Long id) {
        return apiResourceCache.get(id);
    }
    
    public void removeApiResource(Long id) {
        apiResourceCache.remove(id);
    }
    
    public List<ApiResource> getAllApiResources() {
        return new ArrayList<>(apiResourceCache.values());
    }
    
    public List<ApiResource> getApiResourcesByApiId(Long apiId) {
        return apiResourceCache.values().stream()
                .filter(ar -> ar.getStatus() == 1 && Objects.equals(ar.getApiId(), apiId))
                .collect(Collectors.toList());
    }
    
    public List<ApiResource> getApiResourcesByMenuId(Long menuId) {
        return apiResourceCache.values().stream()
                .filter(ar -> ar.getStatus() == 1 && Objects.equals(ar.getMenuId(), menuId))
                .collect(Collectors.toList());
    }
}
