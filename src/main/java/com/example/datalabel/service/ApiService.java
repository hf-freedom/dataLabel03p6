package com.example.datalabel.service;

import com.example.datalabel.entity.SIApi;
import com.example.datalabel.mapper.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {
    
    @Autowired
    private ApiMapper apiMapper;
    
    public SIApi getById(Long id) {
        return apiMapper.selectById(id);
    }
    
    public List<SIApi> getAll() {
        return apiMapper.selectAll();
    }
    
    public boolean save(SIApi api) {
        if (api.getId() == null) {
            return apiMapper.insert(api) > 0;
        } else {
            return apiMapper.update(api) > 0;
        }
    }
    
    public boolean delete(Long id) {
        return apiMapper.deleteById(id) > 0;
    }
}
