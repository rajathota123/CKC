package listener;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.ITestListener; 

import com.cisco.base.BaseTest;
import com.cisco.util.TestUtil;

public class TestListener implements ITestListener{

	@Override 
	public void onTestFailure(ITestResult result) 
	{ 
		System.out.println("***** Error " + result.getName() + " test has failed *****"); 
		String methodName = result.getName().toString().trim(); 
		System.out.println("Capturing screenshot");
		TestUtil.captureScreenshot(methodName, BaseTest.driver); // webdriver from BaseTest class

		System.out.println(methodName + " Screenshot cpatured"); 
	} 

	public void onFinish(ITestContext result) { 
		System.out.println("***** " + result.getName() + " test has Finished *****"); 
	} 

	public void onTestStart(ITestResult result) { 
		System.out.println("***** " + result.getName() + " test has Started *****"); 
	} 

	public void onTestSuccess(ITestResult result) {

		System.out.println("***** " + result.getName() + " test has PASSED *****"); 
		String methodName = result.getName().toString().trim(); 
		System.out.println("Capturing screenshot"); 
		TestUtil.captureScreenshot(methodName,BaseTest.driver); // webdriver from BaseTest class
		System.out.println(methodName + " Screenshot cpatured"); 
	} 

	public void onTestSkipped(ITestResult result) { 
		System.out.println("***** " + result.getName() + " test has Skipped *****"); 
	} 
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) { } 

	public void onStart(ITestContext context) { 
		System.out.println("***** test has Started *****"); 
	}
	
}
