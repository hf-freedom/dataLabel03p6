package com.example.datalabel.mapper;

import com.example.datalabel.cache.SILocalCache;
import com.example.datalabel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserMapper {
    
    @Autowired
    private SILocalCache siLocalCache;
    
    public int insert(User user) {
        user.setId(siLocalCache.generateUserId());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        siLocalCache.putUser(user);
        return 1;
    }
    
    public int update(User user) {
        User existing = siLocalCache.getUser(user.getId());
        if (existing == null) {
            return 0;
        }
        user.setCreateTime(existing.getCreateTime());
        user.setUpdateTime(LocalDateTime.now());
        siLocalCache.putUser(user);
        return 1;
    }
    
    public int deleteById(Long id) {
        siLocalCache.removeUser(id);
        return 1;
    }
    
    public User selectById(Long id) {
        return siLocalCache.getUser(id);
    }
    
    public User selectByUsername(String username) {
        return siLocalCache.getUserByUsername(username);
    }
    
    public List<User> selectAll() {
        return siLocalCache.getAllUsers();
    }
}
