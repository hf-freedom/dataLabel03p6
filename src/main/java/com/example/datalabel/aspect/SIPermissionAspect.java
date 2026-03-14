package com.example.datalabel.aspect;

import com.example.datalabel.annotation.SIRequirePermission;
import com.example.datalabel.cache.LocalCache;
import com.example.datalabel.common.SIResult;
import com.example.datalabel.entity.ApiInfo;
import com.example.datalabel.entity.ApiResource;
import com.example.datalabel.entity.Menu;
import com.example.datalabel.entity.Role;
import com.example.datalabel.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Order(1)
public class SIPermissionAspect {

    @Autowired
    private LocalCache localCache;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(com.example.datalabel.annotation.SIRequirePermission)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return point.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        HttpSession session = request.getSession();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SIRequirePermission annotation = method.getAnnotation(SIRequirePermission.class);

        if (!annotation.requireAuth()) {
            return point.proceed();
        }

        User user = (User) session.getAttribute("user");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (user == null) {
            return handleNoPermission(response, "未登录");
        }

        if (Boolean.TRUE.equals(isAdmin) || "admin".equals(user.getUsername()) || user.getId() == 0L) {
            return point.proceed();
        }

        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();

        ApiInfo apiInfo = localCache.getApiInfoByUrlAndMethod(requestUri, requestMethod);

        if (apiInfo == null) {
            return handleNoPermission(response, "API未注册，无法验证权限");
        }

        List<ApiResource> apiResources = localCache.getApiResourcesByApiId(apiInfo.getId());

        if (apiResources == null || apiResources.isEmpty()) {
            return point.proceed();
        }

        List<Long> resourceMenuIds = apiResources.stream()
                .map(ApiResource::getMenuId)
                .distinct()
                .collect(Collectors.toList());

        List<Long> userRoleIds = user.getRoleIds();
        if (userRoleIds == null || userRoleIds.isEmpty()) {
            return handleNoPermission(response, "权限不足");
        }

        boolean hasPermission = false;
        for (Long roleId : userRoleIds) {
            Role role = localCache.getRole(roleId);
            if (role != null && role.getStatus() == 1 && role.getMenuIds() != null) {
                for (Long menuId : resourceMenuIds) {
                    if (role.getMenuIds().contains(menuId)) {
                        hasPermission = true;
                        break;
                    }
                }
            }
            if (hasPermission) {
                break;
            }
        }

        if (!hasPermission) {
            return handleNoPermission(response, "权限不足");
        }

        return point.proceed();
    }

    private Object handleNoPermission(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        PrintWriter writer = response.getWriter();
        SIResult<?> result = SIResult.error(403, message);
        writer.write(objectMapper.writeValueAsString(result));
        writer.flush();
        writer.close();
        return null;
    }
}
