package com.example.datalabel.interceptor;

import com.example.datalabel.cache.SILocalCache;
import com.example.datalabel.common.SIApiPermission;
import com.example.datalabel.common.SIPermissionDeniedException;
import com.example.datalabel.entity.Menu;
import com.example.datalabel.entity.Role;
import com.example.datalabel.entity.SIApiInfo;
import com.example.datalabel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SIApiInterceptor implements HandlerInterceptor {
    
    @Autowired
    private SILocalCache siLocalCache;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        SIApiPermission annotation = handlerMethod.getMethodAnnotation(SIApiPermission.class);
        
        if (annotation == null || !annotation.requireAuth()) {
            return true;
        }
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        if (siLocalCache.isRootUser(user.getId())) {
            return true;
        }
        
        String requestPath = request.getRequestURI();
        String requestMethod = request.getMethod();
        
        SIApiInfo apiInfo = siLocalCache.getApiByPathAndMethod(requestPath, requestMethod);
        if (apiInfo == null || apiInfo.getRequireAuth() == 0) {
            return true;
        }
        
        Long resourceId = apiInfo.getResourceId();
        if (resourceId == null) {
            return true;
        }
        
        if (hasPermission(user, resourceId)) {
            return true;
        }
        
        throw new SIPermissionDeniedException("权限不足，无法访问该资源");
    }
    
    private boolean hasPermission(User user, Long resourceId) {
        List<Long> roleIds = user.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) {
            return false;
        }
        
        Set<Long> userResourceIds = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = siLocalCache.getRole(roleId);
            if (role != null && role.getStatus() == 1) {
                List<Long> menuIds = role.getMenuIds();
                if (menuIds != null) {
                    userResourceIds.addAll(menuIds);
                }
            }
        }
        
        if (userResourceIds.contains(resourceId)) {
            return true;
        }
        
        Menu resource = siLocalCache.getMenu(resourceId);
        if (resource != null && resource.getParentId() != null && resource.getParentId() > 0) {
            return userResourceIds.contains(resource.getParentId());
        }
        
        return false;
    }
}
