package com.example.datalabel.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SIRequirePermission {
    
    String value() default "";
    
    boolean requireAuth() default true;
}
