package com.example.datalabel.controller;

import com.example.datalabel.common.Result;
import com.example.datalabel.entity.Menu;
import com.example.datalabel.entity.Role;
import com.example.datalabel.entity.User;
import com.example.datalabel.service.MenuService;
import com.example.datalabel.service.RoleService;
import com.example.datalabel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    
    @Value("${admin.username}")
    private String adminUsername;
    
    @Value("${admin.password}")
    private String adminPassword;
    
    @Value("${admin.name}")
    private String adminName;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private MenuService menuService;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    @ResponseBody
    public Result<Map<String, Object>> login(@RequestParam String username,
                                              @RequestParam String password,
                                              @RequestParam String loginType,
                                              HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        if ("admin".equals(loginType)) {
            if (adminUsername.equals(username) && adminPassword.equals(password)) {
                User admin = new User();
                admin.setId(0L);
                admin.setUsername(adminUsername);
                admin.setName(adminName);
                admin.setStatus(1);
                session.setAttribute("user", admin);
                session.setAttribute("isAdmin", true);
                result.put("isAdmin", true);
                result.put("user", admin);
                return Result.success(result);
            } else {
                return Result.error("管理员账号或密码错误");
            }
        } else {
            User user = userService.login(username, password);
            if (user != null) {
                if (user.getStatus() != 1) {
                    return Result.error("账号已被禁用");
                }
                session.setAttribute("user", user);
                session.setAttribute("isAdmin", false);
                result.put("isAdmin", false);
                result.put("user", user);
                return Result.success(result);
            } else {
                return Result.error("用户名或密码错误");
            }
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/main")
    public ModelAndView mainPage(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute("user");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        
        if (user == null) {
            mv.setViewName("redirect:/login");
            return mv;
        }
        
        List<Menu> menus = getUserMenus(user, isAdmin);
        session.setAttribute("menus", menus);
        
        mv.addObject("user", user);
        mv.addObject("isAdmin", isAdmin);
        mv.addObject("menus", menus);
        
        if (Boolean.TRUE.equals(isAdmin)) {
            mv.setViewName("admin-main");
        } else {
            mv.setViewName("user-main");
        }
        return mv;
    }
    
    private List<Menu> getUserMenus(User user, Boolean isAdmin) {
        List<Menu> allMenus = menuService.getAll();
        
        if (Boolean.TRUE.equals(isAdmin)) {
            return allMenus.stream()
                    .filter(m -> m.getStatus() == 1)
                    .sorted(Comparator.comparing(Menu::getSort))
                    .collect(Collectors.toList());
        }
        
        List<Long> userMenuIds = new ArrayList<>();
        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            for (Long roleId : user.getRoleIds()) {
                Role role = roleService.getById(roleId);
                if (role != null && role.getStatus() == 1 && role.getMenuIds() != null) {
                    userMenuIds.addAll(role.getMenuIds());
                }
            }
        }
        
        Set<Long> menuIdSet = new HashSet<>(userMenuIds);
        
        Set<Long> parentIds = new HashSet<>();
        for (Menu menu : allMenus) {
            if (menuIdSet.contains(menu.getId()) && menu.getParentId() != null && menu.getParentId() > 0) {
                parentIds.add(menu.getParentId());
            }
        }
        menuIdSet.addAll(parentIds);
        
        return allMenus.stream()
                .filter(m -> m.getStatus() == 1 && menuIdSet.contains(m.getId()))
                .sorted(Comparator.comparing(Menu::getSort))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/api/menu/user")
    @ResponseBody
    public Result<List<Menu>> getUserMenus(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Menu> menus = (List<Menu>) session.getAttribute("menus");
        if (menus == null) {
            User user = (User) session.getAttribute("user");
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            menus = getUserMenus(user, isAdmin);
            session.setAttribute("menus", menus);
        }
        return Result.success(menus);
    }
}
