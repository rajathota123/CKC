package com.cisco.base;

import org.testng.annotations.DataProvider;

import com.cisco.util.TestUtil;


public class DataProviderr  {	

	@DataProvider(name="CreateRegion")
	public  static Object[][] getSBData(){
		return TestUtil.getData("CreateRegionTest", BaseTest.dashboardxls);	
	}
	
	@DataProvider(name="CreateTemplate")
	public  static Object[][] getTemplatesData(){
		return TestUtil.getData("CreateTemplateTest", BaseTest.datasetxls);	
	}
	
	//this should be used for ConnectorCreation
	@DataProvider(name="CreateConnector")
	public  static Object[][] getConnectorsData(){
		return TestUtil.getData("CreateConnectorTest", BaseTest.datasetxls);	
	}
	
	//this should be used for ConnectorCreation
	@DataProvider(name="CreateDataSet")
	public  static Object[][] getDataSetsData(){
		return TestUtil.getData("CreateDataSetTest", BaseTest.datasetxls);	
	}
	
}
