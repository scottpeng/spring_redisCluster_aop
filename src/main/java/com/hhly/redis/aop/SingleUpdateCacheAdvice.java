/**
 * @Desc    redis 读取数据缓存--切面抽象类
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

import com.hhly.redis.annotation.RedisCacheType;
import com.hhly.redis.annotation.UpdateThroughAssignCache;
import com.hhly.redis.cache.ICacheService;

public abstract class SingleUpdateCacheAdvice<T> extends CacheAdvice {

	@Autowired
	private ICacheService<T> cacheService;
	
    protected Object update(final ProceedingJoinPoint pjp) throws Throwable {
    	//验证 缓存 是否开启
        if (isEnable()) {
            getLogger().info("Cache disabled");
            return pjp.proceed();
        }
        
        Method method = getMethod(pjp);
        UpdateThroughAssignCache cacheable=method.getAnnotation(UpdateThroughAssignCache.class);
        final Signature sig = pjp.getSignature();
        final MethodSignature msig = (MethodSignature) sig;
        
        //验证 方法缓存是否开启
        if(cacheable != null && cacheable.cacheEnable()){
        	// 获取 KEY规则
        	String namespace =  cacheable.namespace();
        	String assignedKey =  cacheable.assignedKey();
        	Annotation [][] anns = method.getParameterAnnotations();
        	if(cacheable.cacheType() == RedisCacheType.Map){  // 缓存类型  map 
        		String mapkey = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		String valuekey = getCacheMapValueKey(anns, pjp.getArgs());
        		Object value = null ;
				value = pjp.proceed();
				if(value != null){
					cacheService.setMap(mapkey,valuekey, value);
				}
				return value ;
        	}else if(cacheable.cacheType() == RedisCacheType.List){ // 缓存类型 list
        		String key   = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		Object value = null ;
        		value = pjp.proceed();
        		if(value!=null){
        			cacheService.lpushx(key, (String)value);
        		}
        		return value ;
        	}else if(cacheable.cacheType() == RedisCacheType.Set){  // 缓存类型 set
        		String key   = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		Object value = null ;
        		value = pjp.proceed();
        		if(value!=null){
        			cacheService.sadd(key, value);
        		}
        		return value ;
        	}else {
        		String key = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		Object value = null ;
        		if(cacheable.cacheType() == RedisCacheType.String ){
    				value = pjp.proceed();
    				if(value != null){
    					cacheService.set(key, value,cacheable.expireTime());
    				}
        			return value ;
        		}
        	}
        }else{  //方法 缓存没开启
        	getLogger().info("Method cache disabled . Name ", msig.getName());
            return pjp.proceed();
        }
        return null;
    }
    
}
