package com.example.datalabel.mapper;

import com.example.datalabel.cache.LocalCache;
import com.example.datalabel.entity.SIApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiMapper {
    
    @Autowired
    private LocalCache localCache;
    
    public int insert(SIApi api) {
        api.setId(localCache.generateApiId());
        localCache.putApi(api);
        return 1;
    }
    
    public int update(SIApi api) {
        SIApi existing = localCache.getApiById(api.getId());
        if (existing == null) {
            return 0;
        }
        localCache.putApi(api);
        return 1;
    }
    
    public int deleteById(Long id) {
        localCache.removeApi(id);
        return 1;
    }
    
    public SIApi selectById(Long id) {
        return localCache.getApiById(id);
    }
    
    public List<SIApi> selectAll() {
        return localCache.getAllApis();
    }
}
