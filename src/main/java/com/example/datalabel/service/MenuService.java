package com.example.datalabel.service;

import com.example.datalabel.entity.Menu;
import com.example.datalabel.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    
    @Autowired
    private MenuMapper menuMapper;
    
    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }
    
    public List<Menu> getAll() {
        return menuMapper.selectAll();
    }
    
    public List<Menu> getByIds(List<Long> ids) {
        return menuMapper.selectByIds(ids);
    }
    
    public List<Menu> getByParentId(Long parentId) {
        return menuMapper.selectByParentId(parentId);
    }
    
    public boolean save(Menu menu) {
        if (menu.getId() == null) {
            return menuMapper.insert(menu) > 0;
        } else {
            return menuMapper.update(menu) > 0;
        }
    }
    
    public boolean delete(Long id) {
        return menuMapper.deleteById(id) > 0;
    }
}
