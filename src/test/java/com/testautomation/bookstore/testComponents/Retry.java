package com.testautomation.bookstore.testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	private int retryCount=0;
	private static final int maxRetryCount = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(retryCount<maxRetryCount)
		{
			retryCount++;
			return true;
		}
				
		return false;
	}
	
	public int getRetryCount() {
	    return retryCount;
	}

}
