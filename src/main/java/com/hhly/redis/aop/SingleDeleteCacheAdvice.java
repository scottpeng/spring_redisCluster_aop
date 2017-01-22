/**
 * @Desc    redis 删除数据缓存--切面抽象类
 * @author  scott
 * @date    2017-1-16
 * @company 益彩网络科技公司
 * @version v1.0
 */
package com.hhly.redis.aop;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.redis.annotation.DeleteThroughAssignCache;
import com.hhly.redis.annotation.RedisCacheType;
import com.hhly.redis.cache.ICacheService;


public abstract class SingleDeleteCacheAdvice<T> extends CacheAdvice {

	@Autowired
	private ICacheService<T> cacheService;
	
    protected Object delete(final ProceedingJoinPoint pjp) throws Throwable {
    	//验证 缓存 是否开启
        if (isEnable()) {
            getLogger().info("Cache disabled");
            return pjp.proceed();
        }
        
        Method method = getMethod(pjp);
        DeleteThroughAssignCache cacheable = method.getAnnotation(DeleteThroughAssignCache.class);
        
        final Signature sig = pjp.getSignature();
        final MethodSignature msig = (MethodSignature) sig;
        
        //验证 方法缓存是否开启
        if(cacheable != null && cacheable.cacheEnable()){
        	// 获取 KEY规则
        	String namespace =  cacheable.namespace();
        	String assignedKey =  cacheable.assignedKey();
        	Annotation [][] anns = method.getParameterAnnotations();
        	// 判断缓存类型
        	if(cacheable.cacheType() == RedisCacheType.Map ){ //  缓存数据为 map类型 
        		String mapkey = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		String valuekey = getCacheMapValueKey(anns, pjp.getArgs());
        		cacheService.removeMap(mapkey,valuekey);
    		    return pjp.proceed();
        	} else {  // 缓存其它类型数据  
        		String key = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		cacheService.remove(key);
        	    return pjp.proceed();
        	}
        }else{  //方法 缓存没开启
        	getLogger().info("Method cache disabled . Name {}", msig.getName());
            return pjp.proceed();
        }
      }
    
}
