package com.example.datalabel.service;

import com.example.datalabel.entity.Organization;
import com.example.datalabel.mapper.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {
    
    @Autowired
    private OrganizationMapper organizationMapper;
    
    public Organization getById(Long id) {
        return organizationMapper.selectById(id);
    }
    
    public List<Organization> getAll() {
        return organizationMapper.selectAll();
    }
    
    public List<Organization> getByParentId(Long parentId) {
        return organizationMapper.selectByParentId(parentId);
    }
    
    public boolean save(Organization org) {
        if (org.getId() == null) {
            return organizationMapper.insert(org) > 0;
        } else {
            return organizationMapper.update(org) > 0;
        }
    }
    
    public boolean delete(Long id) {
        return organizationMapper.deleteById(id) > 0;
    }
}
