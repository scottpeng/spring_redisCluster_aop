package com.hhly.redis.test;


import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RedisAOPTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	public IGetData getData ;
	
	@Test
	public void aopTest(){
		Map<String, TestData> maps = new HashMap<String, TestData>();
		// 测试数据
		maps.put("1", new TestData(1, "scott1", 15));
		maps.put("2", new TestData(2, "scott2", 16));
		maps.put("3", new TestData(3, "scott3", 17));
		maps.put("4", new TestData(4, "scott4", 18));
		GetData.init = maps ;
		
		//test-add 测试新增对象  测试缓存类型为String 
		TestData test_add = new TestData(5,"scott5",60);
		getData.addData("5",test_add);
		
		//test-get 测试从缓存读取数据
		System.err.println(getData.getData("5").getName());
		
		
		//test-update 测试修改数据
		TestData test_update = new TestData(5,"scott_upate",600);
		getData.updateData("5", test_update);
		
		
		//test-delete 测试删除数据
		getData.deleteData("5");
		
	}

}
