/**
 * @Desc 益彩网络缓存管理接口类
 * @author scott
 * @date 2017-1-16
 * @version v1.0
 */

package com.hhly.redis.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author scott
 * @Date 2017年1月26日
 * @Desc 缓存基础服务接口(提供刷新、清除等公共接口)
 */
public interface ICacheService<T> {
	
	
	    //----------------Object-------------------//
		/**
		 * @desc  KEY设置一个 值 ,并且设置有效时间
		 * @param key 存储的KEY
		 * @param value 存储KEY的值
		 * @param expire 有效时间
		 * @throws Exception 异常
		 */
		public void set(String key, Object value,int expire ) throws Exception;
		
		/**
		 * @desc  获取 KEY对应的值
		 * @param key 查询的KEY
		 * @param cls 返回的对象类型
		 * @return
		 * @throws Exception 异常
		 */
		public <T> T get(String key, Class<T> cls) throws Exception;
		
		
		//--------------Set操作-----------------------//
		/**
		 * @desc 返回Set中成员的数量，如果该Key并不存在，返回0。
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public Long scard(String key) throws Exception;

		/**
		 * @desc 如果在插入的过程用，参数中有的成员在Set中已经存在，该成员将被忽略，而其它成员仍将会被正常插入。
		 * 如果执行该命令之前，该Key并不存在，该命令将会创建一个新的Set，此后再将参数中的成员陆续插入。
		 * 如果该Key的Value不是Set类型，该命令将返回相关的错误信息。
		 * @param key
		 * @param members
		 * @return 本次操作实际插入的成员数量。
		 * @throws Exception 
		 */
		public Long sadd(String key, Object... members) throws Exception;

		/**
		 * @desc   从与Key关联的Set中删除参数中指定的成员，
		 *         不存在的参数成员将被忽略，如果该Key并不存在，将视为空Set处理。
		 * @param  key
		 * @param  members
		 * @return 从Set中实际移除的成员数量，如果没有则返回0。
		 * @throws Exception 
		 */
		public Long srem(String key, Object... members) throws Exception;

		/**
		 * @desc   查询set 里面的成员
		 * @param  key
		 * @throws Exception 
		 */
		public <T> Set<T> smembers(String key,Class<T> t ) throws Exception;

		
		//--------------Hash操作-----------------------//
		
		/**
		 * @desc  Map集合存储值
		 * @param key map的key
		 * @param mapKey map里面value对应的key
		 * @param value 要存储的值
		 * @throws Exception 异常
		 */
		public void setMap(String key, String mapKey, Object value) throws Exception;
		
		/**
		 * @desc  获取缓存中，map集合中mapkey存放的对象
		 * @param key  map的key
		 * @param mapKey map里面value对应的key
		 * @param t 返回实体对象类型
		 * @throws Exception
		 */
		public <T> T getMapValue(String key, String mapKey, Class<T> t) throws Exception;
		
		/**
		 * @desc  获取缓存中，map集合中的值
		 * @param name 以对象形式存储的名字
		 * @param t 返回实体对象类型
		 * @throws Exception
		 */
		public <T> List<T> getMapValues(String key, Class<T> t) throws Exception;
		
		/**
		 * @desc 删除 map里面的某一个值
		 * @param key map的外层key
		 * @param valueKey 值对应的key
		 * @return
		 * @throws Exception
		 */
		public Long removeMap(String key, String valueKey) throws Exception;
		
		/**
		 * @desc  获取 map里面 所有 key对应的 value
		 * @param name 以对象形式存储的key
		 * @throws Exception 
		 */
		public Map<Object,Object> getMaps(String key) throws Exception;
		
		
		//----------------------List操作-------------------------------//
		/** 
	     * @desc 将一个或多个值插入到已存在的列表头部，当成功时，
	     *       返回List的长度；当不成功（即key不存在时，返回0）
	     * @param key 
	     * @param value String 
	     * @return 返回List的长度 
	     */
		public  Long lpushx(String key, String value) throws Exception;
		
		/** 
	     * @desc  将一个或多个值插入到已存在的列表头部，当成功时，
	     *        返回List的长度；当不成功（即key不存在时，返回0）  
	     * @param key 
	     * @param values String[] 
	     * @return 返回List的数量size 
	     */ 
		public  Long lpushx(String key, String[] values) throws Exception;
		
		
		/** 
	     * @desc  移出并获取列表的第一个元素，
	     *        当列表不存在或者为空时，返回Null 
	     * @param key 
	     * @return String 
	     */  
		public String lpop(String key) throws Exception ;
		
		
		/** 
	     * @desc  移除并获取列表最后一个元素，
	     *        当列表不存在或者为空时，返回Null 
	     * @param key 
	     * @return String 
	     */ 
		public String rpop(String key) throws Exception;
		
		
		/** 
	     * @desc 移除列表元素，返回移除的元素数量 
	     * @param key 
	     * @param count，标识，表示动作或者查找方向 
	     * <li>当count=0时，移除所有匹配的元素；</li> 
	     * <li>当count为负数时，移除方向是从尾到头；</li> 
	     * <li>当count为正数时，移除方向是从头到尾；</li> 
	     * @param value 匹配的元素 
	     * @return Long 
	     */ 
		public  Long lrem(String key, long count, String value) throws Exception;
		
		//--------------common--------------------//
		
		/**
		 * @desc 删除 KEY -----此处可以删除 任意数据类型的KEY数据
		 * @param key 要删除的KEY
		 * @throws Exception 异常
		 */
		public void remove(String key) throws Exception;
		
		/**
		 * @desc 检测 KEY在缓存中是否存在
		 * @param key 检测的KEY
		 * @return
		 * @throws Exception
		 */
		public boolean exists(String key) throws Exception;
		
		/**
		 * @desc  设置有效期
		 * @param key 有效期的key
		 * @param seconds 有效时间 秒
		 */
		public boolean expire(String key,int seconds) throws Exception;
		
}
