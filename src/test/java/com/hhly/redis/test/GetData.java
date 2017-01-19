package com.hhly.redis.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hhly.redis.annotation.RedisCacheType;
import com.hhly.redis.annotation.ParameterValueKeyProvider;
import com.hhly.redis.annotation.ReadThroughAssignCache;




@Service
public class GetData implements IGetData {
	
	private Logger log = LoggerFactory.getLogger(GetData.class);
	
	
	public static Map<String, TestData> init = null ;

	@Override
	public TestData addData(TestData testData) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@ReadThroughAssignCache(namespace="TEST",assignedKey="GET",cacheType = RedisCacheType.String,valueclass=TestData.class,expireTime=0)
	@Override
	public TestData getData(String uid,@ParameterValueKeyProvider String aa) {
		log.info("Test----getData");
		return init.get(uid);
	}

	@Override
	public boolean deleteData(int uid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TestData updateData(int uid, TestData testData) {
		// TODO Auto-generated method stub
		return null;
	}

}
