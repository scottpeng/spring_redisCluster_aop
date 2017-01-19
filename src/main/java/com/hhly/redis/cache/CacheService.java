/**
 * @Desc 益彩网络缓存redis具体操作类
 * @author scott
 * @date 2017-1-16
 * @version v1.0
 */
package com.hhly.redis.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hhly.redis.cache.ICacheService;


/**
 * @desc 缓存服务操作类
 * @author scott
 * @param 缓存的对象类型(包括普通Bean,集合中的对象等)
 * @date 2017年1月16日
 */
@Service
public class CacheService<T> implements ICacheService<T> {

	private static Logger logger = Logger.getLogger(CacheService.class);

	/** 对象,集合缓存模板 */
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	private Gson gson = new Gson();

	// -------------------object 操作------------------------ //
	@Override
	public void set(String key, Object value, int expire) throws Exception {
		logger.info("object===>set操作!");
	    redisTemplate.opsForValue().set(key, gson.toJson(value));
		if (expire > 0){
			expire(key, expire);
		}
	}

	@Override
	public <T> T get(String key, Class<T> cls) throws Exception {
		logger.info("object===>get操作!");
	    String jsonValue = (String)redisTemplate.opsForValue().get(key);
		return gson.fromJson(jsonValue, cls);
	}

	// -------------------set 操作-------------------------- //
	@Override
	public Long scard(String key) throws Exception {
		logger.info("set===>scard操作!");
		Long count = 0l;
		count = redisTemplate.opsForSet().size(key);
		return count;
	}

	@Override
	public Long sadd(String key, Object... members) throws Exception {
		logger.info("set===>sadd操作!");
		Long rows = 0l;
		Object[] item = new String[members.length];
		int i = 0;
		for (Object obj : members) {
			item[i] = gson.toJson(obj);
			i++;
		}
		rows = redisTemplate.opsForSet().add(key, item);
		return rows;
	}

	@Override
	public Long srem(String key, Object... members) throws Exception {
		logger.info("set===>srem操作!");
		Long rows = 0l;
		Object[] item = new String[members.length];
		int i = 0;
		for (Object obj : members) {
			item[i] = gson.toJson(obj);
			i++;
		}
		rows = redisTemplate.opsForSet().remove(key,item);
		return rows;
	}

	@Override
	public <T> Set<T> smembers(String key, Class<T> t) throws Exception {
	    logger.info("set===>smembers操作!");
	    Set<Object> sItem = redisTemplate.opsForSet().members(key);
        Set<T> result = new HashSet<T>();
		Iterator<Object> rt = sItem.iterator();
		while (rt.hasNext()) {
			result.add(gson.fromJson((String)rt.next(), t));
		}
		return result;
	}

	// -------------------Hash 操作-------------------------- //
	@Override
	public void setMap(String key, String mapKey, Object value) throws Exception {
		    String jsonValue = gson.toJson(value);
		    redisTemplate.opsForHash().put(key, mapKey, jsonValue);
		    
	}

	@Override
	public <T> T getMapValue(String key, String mapKey, Class<T> t) throws Exception {
		String value = (String)redisTemplate.opsForHash().get(key, mapKey);
		return gson.fromJson(value, t);
	}

	@Override
	public <T> List<T> getMapValues(String key, Class<T> t) throws Exception {
		List<Object> list = new ArrayList<Object>();
		List<T> rList = new ArrayList<T>();
        list = redisTemplate.opsForHash().values(key);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				rList.add(gson.fromJson((String)list.get(i), t));
			}
		}
        return rList;
	}
	
	@Override
	public Map<Object,Object> getMaps(String key) throws Exception {
		Map<Object, Object> map  = redisTemplate.opsForHash().entries(key);
		return map ;
	}
	

	@Override
	public Long removeMap(String key, String valueKey) throws Exception {
		Long result = 0L;
		result = redisTemplate.opsForHash().delete(key, valueKey);
		return result;
	}
	
	// -------------------List 操作-------------------------- //
	@Override
	public Long lpushx(String key, String value) throws Exception {
		Long result = 0L;
		result = redisTemplate.opsForList().leftPush(key, value);
		return result;
	}

	@Override
	public Long lpushx(String key, String[] values) throws Exception {
		Long result = 0L;
		result = redisTemplate.opsForList().leftPushAll(key, values);
		return result;
	}
	
	@Override
	public String lpop(String key) throws Exception  {
		return (String)redisTemplate.opsForList().leftPop(key);
	}

	@Override
	public String rpop(String key) throws Exception {
		return (String)redisTemplate.opsForList().rightPop(key);
		
	}
	
	
	@Override
	public Long lrem(String key, long count, String value) throws Exception {
		Long result = 0L;
		result = redisTemplate.opsForList().remove(key, count, value);
		return result;
	}
	

	// -------------------common-------------------------- //
	@Override
	public void remove(String key) throws Exception {
		redisTemplate.delete(key);
	}

	@Override
	public boolean exists(String key) throws Exception {
		return redisTemplate.hasKey(key);
	}

	@Override
	public boolean expire(String key, int seconds) throws Exception {
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	

	

}
