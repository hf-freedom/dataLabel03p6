package com.example.datalabel.service;

import com.example.datalabel.entity.User;
import com.example.datalabel.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public User getById(Long id) {
        return userMapper.selectById(id);
    }
    
    public List<User> getAll() {
        return userMapper.selectAll();
    }
    
    public boolean save(User user) {
        if (user.getId() == null) {
            return userMapper.insert(user) > 0;
        } else {
            return userMapper.update(user) > 0;
        }
    }
    
    public boolean delete(Long id) {
        return userMapper.deleteById(id) > 0;
    }
    
    public boolean updateProfile(User user) {
        return userMapper.update(user) > 0;
    }
}
