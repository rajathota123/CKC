// *******************************************************************************//
// Author: Balveer Murki (393086)-Cognizant Technology Services.

// Description: 

// Modified Date: June-20-2016

// Reason for Modification: 

//*******************************************************************************//
package com.cisco.util;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Reporter;

public class TestUtil {
	
	public static int TestScenarioloopCount = 0;
	public static int TestScenarioFirstCaseRowNo = 0;
	public static int TestScenarioColumnNO = 0;

	public static boolean isExecutable(String testName, Xls_Reader xls){

		for(int rNum=2;rNum<=xls.getRowCount("Test Cases");rNum++){

			if(testName.equals(xls.getCellData("Test Cases", "TCID", rNum))){
				if(xls.getCellData("Test Cases", "Runmode", rNum).equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}

		}

		return false;

	}

	public static Object[][] getData(String testName,Xls_Reader xls){
		// find the testdata in the xls file
		// print the data of the test
		// put the data in object array
		int testCaseStartIndex=0;
		for(int rNum=1;rNum<=xls.getRowCount("Test Data");rNum++){
			if(testName.equals(xls.getCellData("Test Data", 0, rNum))){
				testCaseStartIndex=rNum;
				break;
			}

		}

		int colStartIndex=testCaseStartIndex+1;
		int cols=0;
		while(!xls.getCellData("Test Data", cols, colStartIndex).equals("")){
			cols++;
		}

		int dataStartIndex=testCaseStartIndex+2;
		TestScenarioFirstCaseRowNo = dataStartIndex; // added by Jashwanth
		TestScenarioColumnNO = cols;//added by Jashwanth
		int rows=0;
		while(!xls.getCellData("Test Data", 0, (dataStartIndex+rows)).equals("")){
			rows++;
		}
		System.out.println("Total rows are - "+rows);
		TestScenarioloopCount = rows; //added by jashwanth
		Object[][] data = new Object[rows][1];
		Hashtable<String,String> table = null;
		for(int rNum=dataStartIndex;rNum<(dataStartIndex+rows);rNum++){
			table = new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++){
				table.put(xls.getCellData("Test Data", cNum, colStartIndex), xls.getCellData("Test Data", cNum, rNum));
				//System.out.print(xls.getCellData("Test Data", cNum, rNum)+" -- ");
			}
			
			System.out.println("test Data = "+ table);
			data[rNum-dataStartIndex][0]=table;
		}

		System.out.println(TestScenarioFirstCaseRowNo);
		System.out.println(TestScenarioColumnNO);
		System.out.println(TestScenarioloopCount);

		return data;

	}

	public static void captureScreenshot(String sFileName, WebDriver driver) 
	{ 
		File dir = new File("C:\\SCREENSHOTS"); 
		if (!dir.exists()) 
		{ 
			dir.mkdirs(); 
		} 

		File screenshotFile = new File(dir, sFileName + ".png"); 
		if (screenshotFile.exists()) 
		{ 
			screenshotFile.delete(); 
		} 

		try { 
			File screenshot = ((TakesScreenshot) driver) .getScreenshotAs(OutputType.FILE); 
			// Now you can do whatever you need to do with it, for example copy 
			// somewhere 
			try 
			{ 
				FileUtils.copyFile(screenshot, screenshotFile); 
			} 
			catch (IOException e) 
			{ 
				Reporter.log("Some exception occured in Copying the file" + e.getMessage()); 
			} 

			Reporter.log("Test screenshot located at: " + screenshotFile.getAbsolutePath()); 
		} 
		catch (WebDriverException e) 
		{ 
			Reporter.log("Error occurred taking screenshot: " + e.getMessage()); 
		} 
	} 
	
	
	public static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
}
	

}

