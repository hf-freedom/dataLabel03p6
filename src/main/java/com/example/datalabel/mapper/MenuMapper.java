package com.example.datalabel.mapper;

import com.example.datalabel.cache.SILocalCache;
import com.example.datalabel.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MenuMapper {
    
    @Autowired
    private SILocalCache siLocalCache;
    
    public int insert(Menu menu) {
        menu.setId(siLocalCache.generateMenuId());
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        siLocalCache.putMenu(menu);
        return 1;
    }
    
    public int update(Menu menu) {
        Menu existing = siLocalCache.getMenu(menu.getId());
        if (existing == null) {
            return 0;
        }
        menu.setCreateTime(existing.getCreateTime());
        menu.setUpdateTime(LocalDateTime.now());
        siLocalCache.putMenu(menu);
        return 1;
    }
    
    public int deleteById(Long id) {
        siLocalCache.removeMenu(id);
        return 1;
    }
    
    public Menu selectById(Long id) {
        return siLocalCache.getMenu(id);
    }
    
    public List<Menu> selectAll() {
        return siLocalCache.getAllMenus();
    }
    
    public List<Menu> selectByIds(List<Long> ids) {
        return siLocalCache.getMenusByIds(ids);
    }
}
