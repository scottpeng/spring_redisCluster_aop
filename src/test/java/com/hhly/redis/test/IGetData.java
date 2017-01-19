package com.hhly.redis.test;

import com.hhly.redis.annotation.ParameterValueKeyProvider;

public interface IGetData {
	
	
	
	
	public  TestData addData(TestData testData);
	
	
	public  TestData getData(String uid ,@ParameterValueKeyProvider String aa);
	
	
	public  boolean  deleteData(int uid);
	
	
	public TestData  updateData(int uid ,TestData testData);
	

}
