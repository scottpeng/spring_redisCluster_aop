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
		maps.put("222", new TestData(222, "lisi", 21));
		maps.put("333", new TestData(333, "zhangsan", 24));
		maps.put("444", new TestData(444, "mito", 22));
		maps.put("555", new TestData(555, "yes", 40));
		
		GetData.init = maps ;
		TestData vo = getData.getData("333","test");
		System.out.println(vo.getName());
		
	}

}
