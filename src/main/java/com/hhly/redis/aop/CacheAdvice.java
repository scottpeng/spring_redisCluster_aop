/**
 * @Desc    redis缓存基础切面抽象类
 * @author  scott
 * @date    2017-1-16
 * @company 益彩网络科技公司
 * @version v1.0
 */
package com.hhly.redis.aop;

import java.lang.annotation.Annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.hhly.redis.annotation.AnnotationConstants;
import com.hhly.redis.annotation.ParameterMapKeyProvider;
import com.hhly.redis.annotation.ParameterValueKeyProvider;

public abstract class CacheAdvice  {
    
	
    public static final String ENABLE_CACHE_PROPERTY = "ssm.cache.enable";
    
    /** 缓存开启状态  **/
    public static final String ENABLE_CACHE_PROPERTY_VALUE = "true";
    
    protected abstract Logger getLogger();
    
    /**
     * @desc 缓存是否开启
     * @author scott
     * @date 2017-1-18
     * @return boolean
     */
    protected boolean isEnable() {
        return ENABLE_CACHE_PROPERTY_VALUE.equals(System.getProperty(ENABLE_CACHE_PROPERTY));
    }
    
    /**
     * @desc   警告日志输出
     * @author scott
     * @date   2017-1-18
     * @param  e  异常信息
     * @param  format 格式化异常信息
     * @param  args  参数
     */
    protected void warn(final Exception e, final String format, final Object... args) {
        if (getLogger().isWarnEnabled()) {
            getLogger().warn(String.format(format, args), e);
        }
    }
    
    
    
    /**
     * @desc   获取方法注解参数和参数注解
     * @author scott
     * @date   2017-1-18
     * @param  ProceedingJoinPoint
     * @return Method
     */
    protected Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		@SuppressWarnings("rawtypes")
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}
    
    /**
     * @desc   获取缓存的key
     * @author scott
     * @date   2017-1-19
     * @param  namespace   key 前缀不能为空
     * @param  assignedKey key 后缀不能为空
     * @param  ans         方法注解
     * @param  args        方法参数
     * @return
     */
    protected String getCacheKey(String namespace,String assignedKey,Annotation[][] ans,Object [] args){
    	StringBuilder sb = new StringBuilder();
    	// 获取key前缀
    	if(!StringUtils.isEmpty(namespace) && !namespace.equals(AnnotationConstants.DEFAULT_STRING)) {
    		sb.append(namespace);
    		sb.append("_");
    	}
    	//  获取key后缀
    	if(!StringUtils.isEmpty(assignedKey) && !assignedKey.equals(AnnotationConstants.DEFAULT_STRING) ) {
    		sb.append(assignedKey);
    		sb.append("_");
    	}
    	
    	// 遍历参数中的注解,只取遍历中的一个参数作为key后缀的补充
    	if(ans!= null && ans.length >0){
    		for(Annotation[] anitem : ans){
    			if(anitem.length > 0 && anitem[0] instanceof ParameterValueKeyProvider){
	            	for(Annotation an : anitem){
            			ParameterValueKeyProvider vk = (ParameterValueKeyProvider)an;
            			sb.append(args[vk.order()]);
            			sb.append("_");
	            	}
	            	break;
    			}
            }
    	}
    	return sb.toString();
    }
    
    
    
    protected String getCacheMapValueKey(Annotation[][] ans,Object [] args){
    	StringBuilder sb = new StringBuilder();
    	if( ans != null && ans.length >0){
    		for(Annotation[] anitem : ans){
    			if(anitem.length > 0 && anitem[0] instanceof ParameterMapKeyProvider){
	            	for(Annotation an : anitem){
	            		ParameterMapKeyProvider vk = (ParameterMapKeyProvider)an;
            			sb.append(args[vk.order()]);
            			sb.append("_");
	            	}
	            	break;
    			}
            }
    	}
    	
    	return sb.toString();
    }
    
    

}
