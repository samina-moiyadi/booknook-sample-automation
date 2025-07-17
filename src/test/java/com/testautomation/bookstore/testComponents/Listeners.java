package com.testautomation.bookstore.testComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.testautomation.bookstore.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	
	public Listeners() {
	    // default constructor
	}

	ExtentReports extent = ExtentReporterNG.getReportObject();
	ExtentTest test;
	
	//making object thread safe for parallel execution
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		
		//assigning unique thread id to test variable
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {		
        if (result.getMethod().getRetryAnalyzer(result) != null) {
            int currentRetry = ((com.testautomation.bookstore.testComponents.Retry) result.getMethod().getRetryAnalyzer(result)).getRetryCount();
            System.out.println("Retry attempt " + currentRetry + " for test: " + result.getName());
        }
        
        ExtentTest testInstance = extentTest.get();
        if (testInstance == null) {
            testInstance = extent.createTest(result.getMethod().getMethodName());
            extentTest.set(testInstance);
        }

        testInstance.fail(result.getThrowable());

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
            String filePath = getScreenshot(result.getMethod().getMethodName(), driver);
            testInstance.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

	@Override
	public void onTestSkipped(ITestResult result) {
	    ExtentTest testInstance = extentTest.get();
	    if (testInstance == null) {
	        testInstance = extent.createTest(result.getMethod().getMethodName());
	        extentTest.set(testInstance);
	    }
	    testInstance.log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		//ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		//ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		//ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}