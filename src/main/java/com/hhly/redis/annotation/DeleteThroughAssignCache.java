/**
 * @Desc    缓存delete方法注解
 * @author  scott
 * @date    2017-1-17
 * @version v1.0
 */
package com.hhly.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface DeleteThroughAssignCache {  
	
	/** KEY 前缀名  **/
	String namespace() default AnnotationConstants.DEFAULT_STRING;
	
	/** KEY 后缀名 **/
	String assignedKey() default AnnotationConstants.DEFAULT_STRING;
	
	/** 缓存 类型 **/
	RedisCacheType cacheType() default RedisCacheType.String ;
	
	/** 是否启用  默认 开启 **/
	boolean cacheEnable() default true;

}
