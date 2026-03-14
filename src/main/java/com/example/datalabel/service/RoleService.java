package com.example.datalabel.service;

import com.example.datalabel.entity.Role;
import com.example.datalabel.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    
    @Autowired
    private RoleMapper roleMapper;
    
    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }
    
    public List<Role> getAll() {
        return roleMapper.selectAll();
    }
    
    public boolean save(Role role) {
        if (role.getId() == null) {
            return roleMapper.insert(role) > 0;
        } else {
            return roleMapper.update(role) > 0;
        }
    }
    
    public boolean delete(Long id) {
        return roleMapper.deleteById(id) > 0;
    }
}
