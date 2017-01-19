/**
 * @Desc 缓存update操作AOP
 * @author scott
 * @date 2017-1-16
 * @version v1.0
 */
package com.hhly.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UpdateThroughAssignAOP<T> extends SingleUpdateCacheAdvice<T> {

	private static final Logger LOG = LoggerFactory.getLogger(UpdateThroughAssignAOP.class);

	@Pointcut("@annotation(com.hhly.redis.annotation.UpdateThroughAssignCache)")
	public void getSingleAssign() {

	}

	@Around("getSingleAssign()")
    public Object cacheSingleAssign(final ProceedingJoinPoint jp) throws Throwable {
        return update(jp);
    }

	@Override
	protected Logger getLogger() {
		return LOG;
	}

	

}