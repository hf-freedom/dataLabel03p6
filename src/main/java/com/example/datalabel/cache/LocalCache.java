package com.example.datalabel.cache;

import com.example.datalabel.entity.Menu;
import com.example.datalabel.entity.Organization;
import com.example.datalabel.entity.Role;
import com.example.datalabel.entity.SIApi;
import com.example.datalabel.entity.User;
import org.springframework.stereotype.Component;

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
    private final Map<String, SIApi> apiCache = new ConcurrentHashMap<>();
    private final Map<String, List<Long>> apiToMenuCache = new ConcurrentHashMap<>();
    
    private final AtomicLong userIdGenerator = new AtomicLong(1);
    private final AtomicLong roleIdGenerator = new AtomicLong(1);
    private final AtomicLong orgIdGenerator = new AtomicLong(1);
    private final AtomicLong menuIdGenerator = new AtomicLong(1);
    private final AtomicLong apiIdGenerator = new AtomicLong(1);
    
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
        menu6.setMenuIcon("fa-cogs");
        menu6.setMenuType(2);
        menu6.setSort(5);
        menu6.setStatus(1);
        menuCache.put(menu6.getId(), menu6);
        
        initDefaultApiData();
        
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
        role.setMenuIds(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L));
        roleCache.put(role.getId(), role);

        Role noPermRole = new Role();
        noPermRole.setId(roleIdGenerator.getAndIncrement());
        noPermRole.setRoleName("无权限角色");
        noPermRole.setRoleCode("no_perm");
        noPermRole.setDescription("无任何权限角色");
        noPermRole.setStatus(1);
        noPermRole.setMenuIds(new ArrayList<>());
        roleCache.put(noPermRole.getId(), noPermRole);

        User testUser1 = new User();
        testUser1.setId(generateUserId());
        testUser1.setUsername("user1");
        testUser1.setPassword("123456");
        testUser1.setName("普通用户");
        testUser1.setStatus(1);
        testUser1.setRoleIds(Arrays.asList(1L));
        userCache.put(testUser1.getId(), testUser1);

        User testUser2 = new User();
        testUser2.setId(generateUserId());
        testUser2.setUsername("user2");
        testUser2.setPassword("123456");
        testUser2.setName("无权限用户");
        testUser2.setStatus(1);
        testUser2.setRoleIds(Arrays.asList(2L));
        userCache.put(testUser2.getId(), testUser2);
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
    
    private void initDefaultApiData() {
        addApi("/api/user/list", "GET", "用户列表", Arrays.asList(2L));
        addApi("/api/user", "GET", "获取用户", Arrays.asList(2L));
        addApi("/api/user/save", "POST", "保存用户", Arrays.asList(2L));
        addApi("/api/user", "DELETE", "删除用户", Arrays.asList(2L));
        addApi("/api/user/updateProfile", "POST", "更新个人资料", new ArrayList<>());
        addApi("/api/user/profile", "GET", "获取个人资料", new ArrayList<>());
        
        addApi("/api/role/list", "GET", "角色列表", Arrays.asList(3L));
        addApi("/api/role", "GET", "获取角色", Arrays.asList(3L));
        addApi("/api/role/save", "POST", "保存角色", Arrays.asList(3L));
        addApi("/api/role", "DELETE", "删除角色", Arrays.asList(3L));
        
        addApi("/api/org/list", "GET", "组织机构列表", Arrays.asList(4L));
        addApi("/api/org/tree", "GET", "组织机构树", Arrays.asList(4L));
        addApi("/api/org", "GET", "获取组织机构", Arrays.asList(4L));
        addApi("/api/org/save", "POST", "保存组织机构", Arrays.asList(4L));
        addApi("/api/org", "DELETE", "删除组织机构", Arrays.asList(4L));
        
        addApi("/api/menu/list", "GET", "菜单列表", Arrays.asList(5L));
        addApi("/api/menu/tree", "GET", "菜单树", Arrays.asList(5L));
        addApi("/api/menu", "GET", "获取菜单", Arrays.asList(5L));
        addApi("/api/menu/save", "POST", "保存菜单", Arrays.asList(5L));
        addApi("/api/menu", "DELETE", "删除菜单", Arrays.asList(5L));
        
        addApi("/api/api/list", "GET", "API列表", Arrays.asList(6L));
        addApi("/api/api", "GET", "获取API", Arrays.asList(6L));
        addApi("/api/api/save", "POST", "保存API", Arrays.asList(6L));
        addApi("/api/api", "DELETE", "删除API", Arrays.asList(6L));
        
        addApi("/api/menu/user", "GET", "用户菜单", new ArrayList<>());
        addApi("/main", "GET", "主页面", new ArrayList<>());
        addApi("/logout", "GET", "登出", new ArrayList<>());
    }
    
    private void addApi(String path, String method, String name, List<Long> menuIds) {
        SIApi api = new SIApi();
        api.setId(apiIdGenerator.getAndIncrement());
        api.setApiPath(path);
        api.setApiMethod(method);
        api.setApiName(name);
        api.setMenuIds(menuIds);
        String key = method + ":" + path;
        apiCache.put(key, api);
        apiToMenuCache.put(key, menuIds);
    }
    
    public Long generateApiId() {
        return apiIdGenerator.getAndIncrement();
    }
    
    public void putApi(SIApi api) {
        String key = api.getApiMethod() + ":" + api.getApiPath();
        apiCache.put(key, api);
        apiToMenuCache.put(key, api.getMenuIds() != null ? api.getMenuIds() : new ArrayList<>());
    }
    
    public SIApi getApi(String path, String method) {
        String key = method + ":" + path;
        return apiCache.get(key);
    }
    
    public SIApi getApiById(Long id) {
        return apiCache.values().stream()
                .filter(a -> Objects.equals(a.getId(), id))
                .findFirst()
                .orElse(null);
    }
    
    public void removeApi(Long id) {
        SIApi api = getApiById(id);
        if (api != null) {
            String key = api.getApiMethod() + ":" + api.getApiPath();
            apiCache.remove(key);
            apiToMenuCache.remove(key);
        }
    }
    
    public List<SIApi> getAllApis() {
        return new ArrayList<>(apiCache.values());
    }
    
    public List<Long> getMenuIdsByApi(String path, String method) {
        String key = method + ":" + path;
        List<Long> menuIds = apiToMenuCache.get(key);
        if (menuIds == null) {
            return new ArrayList<>();
        }
        return menuIds;
    }
    
    public boolean hasApiPermission(String path, String method, List<Long> userMenuIds) {
        List<Long> requiredMenuIds = getMenuIdsByApi(path, method);
        if (requiredMenuIds.isEmpty()) {
            return true;
        }
        if (userMenuIds == null || userMenuIds.isEmpty()) {
            return false;
        }
        for (Long requiredId : requiredMenuIds) {
            if (userMenuIds.contains(requiredId)) {
                return true;
            }
        }
        return false;
    }
}
