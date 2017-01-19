/**
 * @desc   Redis Hash参数注解类
 * @author scott
 * @date  2017-01-17
 * @version V1.0
 */
package com.hhly.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ParameterMapKeyProvider {

	/** 默认参数顺序 **/
	public int order() default 0;

}