/**
 * @Desc    缓存注解key常量类
 *          默认情况下key的值： unassigned_default
 * @author  scott
 * @date    2017-1-16
 * @company 益彩网络科技公司
 * @version v1.0
 */
package com.hhly.redis.annotation;


public interface AnnotationConstants {
    
	/** key前缀  默认空间常量  **/
    String DEFAULT_STRING = "unassigned";
    
    /** key后缀  默认缓存常量 **/
    String DEFAULT_CACHE_NAME = "default";

}