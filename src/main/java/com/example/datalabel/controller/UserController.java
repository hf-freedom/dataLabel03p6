package com.example.datalabel.controller;

import com.example.datalabel.annotation.SIRequirePermission;
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
    @SIRequirePermission("user:list")
    public SIResult<List<User>> list() {
        return SIResult.success(userService.getAll());
    }

    @GetMapping("/{id}")
    @SIRequirePermission("user:view")
    public SIResult<User> getById(@PathVariable Long id) {
        return SIResult.success(userService.getById(id));
    }

    @PostMapping("/save")
    @SIRequirePermission("user:save")
    public SIResult<Boolean> save(@RequestBody User user) {
        return SIResult.success(userService.save(user));
    }

    @DeleteMapping("/{id}")
    @SIRequirePermission("user:delete")
    public SIResult<Boolean> delete(@PathVariable Long id) {
        return SIResult.success(userService.delete(id));
    }

    @PostMapping("/updateProfile")
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
    public SIResult<User> getProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return SIResult.error(401, "未登录");
        }
        return SIResult.success(user);
    }
}
