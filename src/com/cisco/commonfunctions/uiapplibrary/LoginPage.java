package com.cisco.commonfunctions.uiapplibrary;

import org.testng.annotations.AfterSuite;

import com.cisco.base.BaseTest;

public class LoginPage extends BaseTest {

	// null - if login is not success
	// void - if login is success
	boolean done = false;

	// Logger log = Logger.getLogger(LoginPage.class.getName());

	public void launchAPP() throws InterruptedException {
		try {
			driver.get(setConfig("appURL"));
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doLoginAsAdmin() throws InterruptedException {
		log.info("enter username and password for admin login");
		doLogin(setConfig("adminUserName"), setConfig("adminPassword"));
	}

	public void doLoginAsOperator() throws InterruptedException {

		log.info("enter username and password for operator login");

		doLogin(setConfig("operatorUserName"), setConfig("operatorpassword"));

	}

	public void doLoginAsContentCreator() throws InterruptedException {

		log.info("enter username and password for operator login");

		doLogin(setConfig("CCUserName"), setConfig("CCpassword"));

	}

	public void doLogin(String userName, String password) throws InterruptedException {
		launchAPP();
		input("LOGIN_USERNAME", userName);
		input("LOGIN_PASSWORD", password);
		click("LOGIN_SIGNIN");

//		clickElementById("app_mainView_loginView_loginButton");

	}

}
