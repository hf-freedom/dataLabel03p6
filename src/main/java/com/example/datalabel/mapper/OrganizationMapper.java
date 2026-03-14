package com.example.datalabel.mapper;

import com.example.datalabel.cache.LocalCache;
import com.example.datalabel.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrganizationMapper {
    
    @Autowired
    private LocalCache localCache;
    
    public int insert(Organization org) {
        org.setId(localCache.generateOrgId());
        org.setCreateTime(LocalDateTime.now());
        org.setUpdateTime(LocalDateTime.now());
        localCache.putOrg(org);
        return 1;
    }
    
    public int update(Organization org) {
        Organization existing = localCache.getOrg(org.getId());
        if (existing == null) {
            return 0;
        }
        org.setCreateTime(existing.getCreateTime());
        org.setUpdateTime(LocalDateTime.now());
        localCache.putOrg(org);
        return 1;
    }
    
    public int deleteById(Long id) {
        localCache.removeOrg(id);
        return 1;
    }
    
    public Organization selectById(Long id) {
        return localCache.getOrg(id);
    }
    
    public List<Organization> selectAll() {
        return localCache.getAllOrgs();
    }
    
    public List<Organization> selectByParentId(Long parentId) {
        return localCache.getOrgsByParentId(parentId);
    }
}
