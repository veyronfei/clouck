//package com.clouck.repository;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Target({ ElementType.METHOD, ElementType.TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//public @interface RetryRequired {
//    int interval() default 2; //in second
//    int time() default 5;
//}