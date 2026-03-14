package com.example.datalabel.mapper;

import com.example.datalabel.cache.LocalCache;
import com.example.datalabel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserMapper {
    
    @Autowired
    private LocalCache localCache;
    
    public int insert(User user) {
        user.setId(localCache.generateUserId());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        localCache.putUser(user);
        return 1;
    }
    
    public int update(User user) {
        User existing = localCache.getUser(user.getId());
        if (existing == null) {
            return 0;
        }
        user.setCreateTime(existing.getCreateTime());
        user.setUpdateTime(LocalDateTime.now());
        localCache.putUser(user);
        return 1;
    }
    
    public int deleteById(Long id) {
        localCache.removeUser(id);
        return 1;
    }
    
    public User selectById(Long id) {
        return localCache.getUser(id);
    }
    
    public User selectByUsername(String username) {
        return localCache.getUserByUsername(username);
    }
    
    public List<User> selectAll() {
        return localCache.getAllUsers();
    }
}
