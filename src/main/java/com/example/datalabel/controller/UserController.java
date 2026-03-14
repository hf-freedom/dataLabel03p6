package com.example.datalabel.controller;

import com.example.datalabel.common.SIApiPermission;
import com.example.datalabel.common.SIResult;
import com.example.datalabel.entity.User;
import com.example.datalabel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    @SIApiPermission(name = "用户列表", resourceCode = "user")
    public SIResult<List<User>> list() {
        return SIResult.success(userService.getAll());
    }
    
    @GetMapping("/{id}")
    @SIApiPermission(name = "获取用户", resourceCode = "user")
    public SIResult<User> getById(@PathVariable Long id) {
        return SIResult.success(userService.getById(id));
    }
    
    @PostMapping("/save")
    @SIApiPermission(name = "保存用户", resourceCode = "user")
    public SIResult<Boolean> save(@RequestBody User user) {
        return SIResult.success(userService.save(user));
    }
    
    @DeleteMapping("/{id}")
    @SIApiPermission(name = "删除用户", resourceCode = "user")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(userService.delete(id));
    }
    
    @PostMapping("/updateProfile")
    @SIApiPermission(name = "更新个人资料", resourceCode = "user", requireAuth = false)
    public SIResult<Boolean> updateProfile(@RequestBody User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return SIResult.error(401, "未登录");
        }
        user.setId(currentUser.getId());
        boolean result = userService.updateProfile(user);
        if (result) {
            User updatedUser = userService.getById(currentUser.getId());
            session.setAttribute("user", updatedUser);
        }
        return SIResult.success(result);
    }
    
    @GetMapping("/profile")
    @SIApiPermission(name = "获取个人资料", resourceCode = "user", requireAuth = false)
    public SIResult<User> getProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return SIResult.error(401, "未登录");
        }
        return SIResult.success(user);
    }
}
