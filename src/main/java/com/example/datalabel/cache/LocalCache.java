package com.example.datalabel.cache;

import com.example.datalabel.entity.Menu;
import com.example.datalabel.entity.Organization;
import com.example.datalabel.entity.Role;
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
    
    private final AtomicLong userIdGenerator = new AtomicLong(1);
    private final AtomicLong roleIdGenerator = new AtomicLong(1);
    private final AtomicLong orgIdGenerator = new AtomicLong(1);
    private final AtomicLong menuIdGenerator = new AtomicLong(1);
    
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
        role.setMenuIds(Arrays.asList(1L, 2L, 3L, 4L, 5L));
        roleCache.put(role.getId(), role);
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
}
