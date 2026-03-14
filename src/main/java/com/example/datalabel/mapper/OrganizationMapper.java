package com.example.datalabel.mapper;

import com.example.datalabel.cache.SILocalCache;
import com.example.datalabel.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrganizationMapper {
    
    @Autowired
    private SILocalCache siLocalCache;
    
    public int insert(Organization org) {
        org.setId(siLocalCache.generateOrgId());
        org.setCreateTime(LocalDateTime.now());
        org.setUpdateTime(LocalDateTime.now());
        siLocalCache.putOrg(org);
        return 1;
    }
    
    public int update(Organization org) {
        Organization existing = siLocalCache.getOrg(org.getId());
        if (existing == null) {
            return 0;
        }
        org.setCreateTime(existing.getCreateTime());
        org.setUpdateTime(LocalDateTime.now());
        siLocalCache.putOrg(org);
        return 1;
    }
    
    public int deleteById(Long id) {
        siLocalCache.removeOrg(id);
        return 1;
    }
    
    public Organization selectById(Long id) {
        return siLocalCache.getOrg(id);
    }
    
    public List<Organization> selectAll() {
        return siLocalCache.getAllOrgs();
    }
    
    public List<Organization> selectByParentId(Long parentId) {
        return siLocalCache.getOrgsByParentId(parentId);
    }
}
