package com.cisco.commonfunctions.uiapplibrary;

import org.testng.annotations.AfterSuite;

import com.cisco.base.BaseTest;
public class HomePage extends BaseTest{

	// null - if login is not success
	// void - if login is success
	boolean done= false;
	
	//Logger log = Logger.getLogger(LoginPage.class.getName());

	
	public void clickAdminBtn(){
		
		BaseTest.click("HomePage_AdminBtn");
		
	}
	

	
	
}
