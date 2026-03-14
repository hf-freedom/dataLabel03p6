package com.example.datalabel.controller;

import com.example.datalabel.common.Result;
import com.example.datalabel.common.annotation.SIRequiredPermission;
import com.example.datalabel.entity.User;
import com.example.datalabel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@SIRequiredPermission
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    public Result<List<User>> list() {
        return Result.success(userService.getAll());
    }
    
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }
    
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody User user) {
        return Result.success(userService.save(user));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(userService.delete(id));
    }
    
    @SIRequiredPermission(required = false)
    @PostMapping("/updateProfile")
    public Result<Boolean> updateProfile(@RequestBody User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.error(401, "未登录");
        }
        user.setId(currentUser.getId());
        boolean result = userService.updateProfile(user);
        if (result) {
            User updatedUser = userService.getById(currentUser.getId());
            session.setAttribute("user", updatedUser);
        }
        return Result.success(result);
    }
    
    @SIRequiredPermission(required = false)
    @GetMapping("/profile")
    public Result<User> getProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(user);
    }
}
