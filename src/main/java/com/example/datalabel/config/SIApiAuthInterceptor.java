package com.example.datalabel.config;

import com.example.datalabel.cache.LocalCache;
import com.example.datalabel.common.annotation.SIRequiredPermission;
import com.example.datalabel.entity.Role;
import com.example.datalabel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SIApiAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private LocalCache localCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        SIRequiredPermission methodAnnotation = method.getAnnotation(SIRequiredPermission.class);
        SIRequiredPermission classAnnotation = clazz.getAnnotation(SIRequiredPermission.class);

        boolean requiresPermission = (methodAnnotation != null && methodAnnotation.required())
                || (classAnnotation != null && classAnnotation.required());

        if (!requiresPermission) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        if (Boolean.TRUE.equals(isAdmin)) {
            return true;
        }

        List<Long> userMenuIds = getUserMenuIds(user);
        String requestUri = request.getRequestURI();
        String methodType = request.getMethod();

        String path = normalizePath(requestUri);

        if (localCache.hasApiPermission(path, methodType, userMenuIds)) {
            return true;
        }

        if (isAjaxRequest(request)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":403,\"message\":\"权限不足\",\"data\":null}");
        } else {
            response.sendRedirect("/forbidden");
        }
        return false;
    }

    private List<Long> getUserMenuIds(User user) {
        Set<Long> menuIdSet = new HashSet<>();
        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            for (Long roleId : user.getRoleIds()) {
                Role role = localCache.getRole(roleId);
                if (role != null && role.getStatus() == 1 && role.getMenuIds() != null) {
                    menuIdSet.addAll(role.getMenuIds());
                }
            }
        }
        return new ArrayList<>(menuIdSet);
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header) || request.getRequestURI().startsWith("/api/");
    }

    private String normalizePath(String requestUri) {
        if (requestUri.matches(".*/\\d+$")) {
            return requestUri.replaceAll("/\\d+$", "");
        }
        return requestUri;
    }
}
