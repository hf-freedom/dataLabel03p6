package com.example.datalabel.mapper;

import com.example.datalabel.cache.LocalCache;
import com.example.datalabel.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MenuMapper {
    
    @Autowired
    private LocalCache localCache;
    
    public int insert(Menu menu) {
        menu.setId(localCache.generateMenuId());
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        localCache.putMenu(menu);
        return 1;
    }
    
    public int update(Menu menu) {
        Menu existing = localCache.getMenu(menu.getId());
        if (existing == null) {
            return 0;
        }
        menu.setCreateTime(existing.getCreateTime());
        menu.setUpdateTime(LocalDateTime.now());
        localCache.putMenu(menu);
        return 1;
    }
    
    public int deleteById(Long id) {
        localCache.removeMenu(id);
        return 1;
    }
    
    public Menu selectById(Long id) {
        return localCache.getMenu(id);
    }
    
    public List<Menu> selectAll() {
        return localCache.getAllMenus();
    }
    
    public List<Menu> selectByIds(List<Long> ids) {
        return localCache.getMenusByIds(ids);
    }
    
    public List<Menu> selectByParentId(Long parentId) {
        return localCache.getMenusByParentId(parentId);
    }
}
