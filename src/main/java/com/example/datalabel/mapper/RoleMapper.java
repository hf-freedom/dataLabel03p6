package com.example.datalabel.mapper;

import com.example.datalabel.cache.SILocalCache;
import com.example.datalabel.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RoleMapper {
    
    @Autowired
    private SILocalCache siLocalCache;
    
    public int insert(Role role) {
        role.setId(siLocalCache.generateRoleId());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        siLocalCache.putRole(role);
        return 1;
    }
    
    public int update(Role role) {
        Role existing = siLocalCache.getRole(role.getId());
        if (existing == null) {
            return 0;
        }
        role.setCreateTime(existing.getCreateTime());
        role.setUpdateTime(LocalDateTime.now());
        siLocalCache.putRole(role);
        return 1;
    }
    
    public int deleteById(Long id) {
        siLocalCache.removeRole(id);
        return 1;
    }
    
    public Role selectById(Long id) {
        return siLocalCache.getRole(id);
    }
    
    public List<Role> selectAll() {
        return siLocalCache.getAllRoles();
    }
}
