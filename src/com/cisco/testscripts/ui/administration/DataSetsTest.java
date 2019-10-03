package com.cisco.testscripts.ui.administration;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cisco.base.BaseTest;
import com.cisco.base.DataProviderr;
import com.cisco.commonfunctions.uiapplibrary.DataSetsPage;
import com.cisco.util.ErrorUtil;

public class DataSetsTest extends BaseTest {

	public static SoftAssert softAssert = new SoftAssert();

	DataSetsPage orcPage = new DataSetsPage();

	@Test(priority = 1)
	public void testCreateTemplate() {
		try {
			orcPage.toClickAdmin();
		} catch (Exception e) {
		}
	}

	@Test(dataProvider = "CreateTemplate", dataProviderClass = DataProviderr.class, priority = 2)
	public void testCreateTemplate(Hashtable<String, String> data, ITestContext ctx) {
		try {
			
			ctx.setAttribute("testName", data.get("testName"));
			orcPage.testData_CreateTemplate(data);
			String templatename = orcPage.createTemplate(data.get("Name"), data.get("FileType"), data.get("BaseURL"),
					data.get("Authstrategy"));
			if (data.get("Name").contains("No_Auth"))
				BaseTest.createdTemplateNames.put("TemplateNoAuth", templatename);
			else if (data.get("Name").contains("Basic_Auth"))
				BaseTest.createdTemplateNames.put("TemplateBasicAuth", templatename);
			else if (data.get("Name").contains("Custom_Auth"))
				BaseTest.createdTemplateNames.put("TemplateCustomAuth", templatename);
			else if (data.get("Name").contains("FILE"))
				BaseTest.createdTemplateNames.put("TemplateFile", templatename);
			orcPage.validation_CreatedTemplate();

		} catch (Exception e) {
			System.out.println("exception occured while Creating Template.");
			Reporter.log("exception occured while Creating Template.");
			BaseTest.takeScreenshot("Template.jpg");
			ErrorUtil.addVerificationFailure(e);
			Assert.fail();
		}

	}

	/*
	 * @Test(priority = 3) public void validateTemplate() {
	 * 
	 * }
	 */

	//@Test(dataProvider = "CreateConnector", dataProviderClass = DataProviderr.class, priority = 3)
	public void testCreateConnector(Hashtable<String, String> data, ITestContext ctx) {
		try {
			ctx.setAttribute("testName", data.get("testName"));
			orcPage.testData_CreateConnector(data);
			orcPage.createConnector(data.get("ConnectorName"), data.get("connectorType"), data.get("pathURL"));
			orcPage.validation_CreatedConnector();

		} catch (Exception e) {
			System.out.println("exception occured while Creating Connector.");
			Reporter.log("exception occured while Creating Connector.");
			BaseTest.takeScreenshot("Connector.jpg");
			ErrorUtil.addVerificationFailure(e);
			Assert.fail();
		}

	}

	/*
	 * @Test(priority = 5) public void validateConnector() {
	 * 
	 * }
	 */

	//@Test(dataProvider = "CreateDataSet", dataProviderClass = DataProviderr.class, priority = 4)
	public void testCreateDataSet(Hashtable<String, String> data, ITestContext ctx) {
		try {
			ctx.setAttribute("testName", data.get("testName"));
			orcPage.dataSetCreation(data.get("ProviderType"), data.get("DatasetName"), data.get("Outputformat"));
			orcPage.validate_createdDataset();

		} catch (Exception e) {
			System.out.println("exception occured while Creating DataSet.");
			Reporter.log("exception occured while Creating DataSet.");
			BaseTest.takeScreenshot("DataSet.jpg");
			ErrorUtil.addVerificationFailure(e);
			Assert.fail();
		}
	}

	/*
	 * @Test(priority = 7) public void validateDataSet() {
	 * 
	 * }
	 */

}