package com.example.datalabel.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SIApiPermission {
    
    String name() default "";
    
    String description() default "";
    
    boolean requireAuth() default true;
    
    String resourceCode() default "";
}
