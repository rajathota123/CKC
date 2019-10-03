package com.cisco.commonfunctions.uiapplibrary;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.cisco.base.BaseTest;

public class DataSetsPage extends BaseTest {

	public static String templatename;
	static String BaseURLRequired;
	static String pathUralRequired;
	static String datasetname;
	String connectorname;
	


	String headerkey, headervalue, paramskey, paramsvalue, description, tags, username, password, url, method,
	payloadtype, payloadtext, authscript, pathURL, responsetype, output;

	//	private JSONObject templateNameMap = new JSONObject();

	// Header Values

	public void setHeaderKey(String headerkey) {
		this.headerkey = headerkey;
	}

	public void setHeaderValue(String headervalue) {
		this.headervalue = headervalue;
	}

	//	// Params values
	public void setParamsKey(String paramskey) {
		this.paramskey = paramskey;
	}

	public void setParamsValue(String paramsvalue) {
		this.paramsvalue = paramsvalue;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public void setMethodType(String method) {
		this.method = method;
	}

	public void setPayloadType(String payloadtype) {
		this.payloadtype = payloadtype;

	}

	public void setPayloadText(String payloadtext) {
		this.payloadtext = payloadtext;
	}

	public void setAuthScript(String authscript) {
		this.authscript = authscript;
	}

	public void setPathURL(String pathURL) {
		this.pathURL = pathURL;
	}

	public void setResponseType(String responsetype) {
		this.responsetype = responsetype;

	}

	/*
	 * Description: This method is used to navigate to Orechestration Page
	 */
	public void toClickAdmin() {
		try {
			driverwait(15000);
			click("HomePage_AdminBtn");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while navigating to admin module");
		}
	}

	public void testData_CreateTemplate(Hashtable<String, String> data) {

		description = data.get("Description");
		setDescription(description);

		tags = data.get("Tags");
		setTags(tags);

		headerkey = data.get("HeaderKey");
		setHeaderKey(headerkey);

		headervalue = data.get("HeaderValue");
		setHeaderValue(headervalue);

		paramskey = data.get("ParamsKey");
		setParamsKey(paramskey);

		paramsvalue = data.get("ParamsValue");
		setParamsValue(paramsvalue);

		username = data.get("UserName");
		setUserName(username);

		password = data.get("Password");
		setPassword(password);

		url = data.get("URL");
		setURL(url);

		method = data.get("Method");
		setMethodType(method);

		payloadtype = data.get("PayloadType");
		setPayloadType(payloadtype);

		payloadtext = data.get("PayloadText");
		setPayloadText(payloadtext);

		authscript = data.get("AuthScript");
		setAuthScript(authscript);

	}

	public void testData_CreateConnector(Hashtable<String, String> data) {
		pathURL = data.get("pathURL");
		setPathURL(pathURL);

		method = data.get("Method");
		setMethodType(method);

		responsetype = data.get("ResponseType");
		setResponseType(responsetype);
	}

	/*
	 * Description: This method contains process which is used to create HTTP
	 * Template using No_Auth Strategy
	 */
	private void processToHTTP_NoAuth_Template() {
		try {
			input("URL_Input", BaseURLRequired);
			click("Params_Dropdown");
			input("Params_Key_Input", paramskey);
			click("Params_Dropdown");
			input("Params_Value_Input", paramsvalue);
			click("Params0_Dropdown");
			input("Params0_Key_Input", headerkey);
			click("Params0_Value_Input");
			input("Params0_Value_Input", headervalue);
			click("Submit_button");
			//verifyMsg();
			driverwait(3000);
			verifyToastNotifications("Connection Template Created Successfully");
			//Report Added
			Reporter.log("NoAuth_Template Created Successfully");


		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating NoAuth_Template");
		}
	}

	/*
	 * Description: This method contains process which is used to create HTTP
	 * Template using Basic Auth Strategy
	 */
	private void processToHTTP_BasicAuth_Template() {
		try {
			input("URL_Input", BaseURLRequired);
			click("Auth_Strategy");
			input("UserName_Input", username);
			input("Password_Input", password);
			click("Params0_Dropdown");
			input("Params0_Key_Input", paramskey);
			click("Params0_Value_Input");
			input("Params0_Value_Input", paramsvalue);
			click("Headers0_Dropdown");
			input("Headers0_Key_Input", headerkey);
			click("Headers0_Value_Input");
			input("Headers0_Value_Input", headervalue);
			click("Submit_button");
			//verifyMsg();
			driverwait(3000);
			verifyToastNotifications("Connection Template Created Successfully");
			if(getText("Toast_containter").contentEquals("Connection Template Created Successfully")) {
				//Reporter.log("BasicAuth_Template Created Successfully");
			}  else {
				Reporter.log("Failed to create BasicAuth_Template");

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating BasicAuth_Template");
		}
	}

	/*
	 * Description: This method contains process which is used to create HTTP
	 * Template using Custom Auth Strategy
	 */
	private void processToHTTP_CustomAuth_Template() {
		try {
			input("URL_Input", BaseURLRequired);
			click("Auth_Strategy");
			input("Custom_Auth_URL", url);
			selectOptionFromDivList("Method", method);
			selectOptionFromDivList("Payload Type", payloadtype);
			input("Custom_Auth_Payload_Input", payloadtext);
			input("Custom_AuthScript_Input", authscript);
			// click("Payload_P0_DorpDown");
			// input("Payload_P0_Key_Input", "test" + BaseTest.generateDynamicData());
			// click("Payload_P0_Value_Input");
			// input("Payload_P0_Value_Input", "test" + BaseTest.generateDynamicData());
			// click("Payload_H0_DropDown");
			// input("Payload_H0_Key_Input", "test" + BaseTest.generateDynamicData());
			// click("Payload_P0_Value_Input");
			// input("Payload_H0_Value_Input", "test" + BaseTest.generateDynamicData());
			// click("Params0_Dropdown");
			// input("Params0_Key_Input", "test" + BaseTest.generateDynamicData());
			// click("Params0_Value_Input");
			// input("Params0_Value_Input", "test" + BaseTest.generateDynamicData());
			// click("Headers0_Dropdown");
			// input("Headers0_Key_Input", "test" + BaseTest.generateDynamicData());
			// click("Headers0_Value_Input");
			// input("Headers0_Value_Input", "test" + BaseTest.generateDynamicData());
			click("Submit_button");
			//verifyMsg();
			driverwait(20000);
			verifyToastNotifications("Connection Template Created Successfully");
			//Report
			Reporter.log("CustomAuth_Template Created Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating CustomAuth_Template");
		}
	}

	/*
	 * Description: This method contains process which is used to create HTTP
	 * Connector using all Response Types (JSON & CSV included for now)
	 */
	private void processToHTTPconnector(String ConnectorName) throws Exception {
		try {
			String Auth_Strategy = getText("Auth_Strategy_contains");
			//			System.out.println(Auth_Strategy);
			click("Search_Input");

			if (ConnectorName.contains("No_Auth"))
				input("Search_Input", createdTemplateNames.get("TemplateNoAuth"));
			//Reporter.log(ConnectorName+" is created with TemplateNoAuth");
			else if (ConnectorName.contains("Basic_Auth"))
				input("Search_Input", createdTemplateNames.get("TemplateBasicAuth"));
			else if (ConnectorName.contains("Custom_Auth"))
				input("Search_Input", createdTemplateNames.get("TemplateCustomAuth"));

			click("Right_Arrow");
			driverwait(2000);
			click("Next_Button");
			selectOptionFromDivList("Response Type", responsetype);

			if (responsetype.contains("JSON")) {
				if (Auth_Strategy.contains("no_auth")) {
					input("Ptah_Intput", pathURL);
					selectOptionFromDivList("Method", method);

				} else if (Auth_Strategy.contains("basic_auth")) {
					input("Ptah_Intput", pathURL);
					selectOptionFromDivList("Method", method);
				} else if (Auth_Strategy.contains("custom_auth")) {
					input("Ptah_Intput", pathURL);
					selectOptionFromDivList("Method", method);
				}
				//
			} else if (responsetype.contains("CSV")) {
				// this code needs to be modified
				if (Auth_Strategy.contains("no_auth")) {
					input("Ptah_Intput", pathUralRequired);
					selectOptionFromDivList("Method", method);

				} else if (Auth_Strategy.contains("basic_auth")) {
					input("Ptah_Intput", pathUralRequired);
					selectOptionFromDivList("Method", "POST");

				} else if (Auth_Strategy.contains("custom_auth")) {
					input("Ptah_Intput", pathUralRequired);
					selectOptionFromDivList("Method", "GET");

				}
			}
			// click("Headers_Dropdownn");
			// input("Headers_Key", "X-RapidAPI-Host");
			// input("Headers_Value", "transloc-api-1-2.p.rapidapi.com");
			click("Submit_button");
			//verifyMsg();
			driverwait(15000);
			verifyToastNotifications("Connector Created Successfully");
			Reporter.log("HTTP Connector Created Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating HTTP connectors");
		}
	}

	public void verifyMsg() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfAllElements(getLocatorType("TOSTED_MSG")));			
		List <WebElement> message = driver.findElements(By.xpath("//*[@class='toast-message ng-star-inserted' or @class='toast-title ng-star-inserted']"));
		if(message.size()>0){
			String error = message.get(0).getText();
			//!(message.get(0).getText().contains("Successfully");
			if(!(error.contains("Successfully"))) {
				System.out.println(message.get(0).getText());
				//test.log(LogStatus.FAIL, "connectors already present; error Message is "+error);
				Reporter.log("connectors already present; error Message is "+error);
				Assert.fail(error);
				//softAssert.assertAll();

					
			}	else {
				Reporter.log(error);
			}
		}

	}

	/*
	 * Description: This method contains process which is used to create FILE
	 * Connector using all Response Types (CSV included for now)
	 */
	private void processToFileConnector(String ConnectorName) {
		try {
			click("Search_Input");
			//			if (ConnectorName.contains("FILE"))
			input("Search_Input", createdTemplateNames.get("TemplateFile"));
			//			input("Search_Input", templatename);
			click("Right_Arrow");
			driverwait(2000);
			click("Next_Button");
			click("CLICK_DATA_SOURCE_TYPE");
			click("SELECT_LOCAL_DATA");
			click("CLICK_FORMATE_DATE");
			click("SELECT_CSV");
			input("CLICK_CSV_DATA", "city,rank\nhyd,1000\nbng,2000");
			click("BTN_TEST");
			click("BTN_SUBMIT");
			//verifyMsg();
			verifyToastNotifications("Connector Created Successfully");
			Reporter.log("FILE Connector Created Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating HTTP connectors");
		}
	}

	/*
	 * Description: This method used to create Templates HTTP & FILE Templates can
	 * be created
	 */
	public static String file_Type;

	public String createTemplate(String Name, String fileType, String newURL, String stratergy) {
		try {
			file_Type = fileType;
			BaseURLRequired = newURL;
			System.out.println(BaseURLRequired);
			click("ORCHESTRATION");
			click("Connection_Template");
			driverwait(1500);
			click("Create_Button");
			templatename = Name + BaseTest.generateDynamicData();
			input("ConnectorName_Input", Name + BaseTest.generateDynamicData());
			//			System.out.println(templatename);
			input("Description_Input", description);
			input("Tags_Input", tags);
			selectOptionFromDivList("Type", file_Type);
			if (file_Type.contains("HTTP")) {
				Reporter.log(file_Type+" File Type Created");
				click("Next_Button");
				selectOptionFromDivList("Auth Strategy", stratergy);
				//				System.out.println(stratergy);

				if (stratergy.toString().equals("No Auth")) {
					processToHTTP_NoAuth_Template();
					
				} else if (stratergy.toString().equals("Basic Auth")) {
					processToHTTP_BasicAuth_Template();
				} else if (stratergy.toString().equals("Custom Auth")) {
					processToHTTP_CustomAuth_Template();
				}
				Reporter.log("Created HTTP template: "+templatename);
			} else if (file_Type.contains("FILE")) {
				click("Submit_button");
				//verifyMsg();
				Reporter.log("Created FILE template: "+templatename);
			}
			driverwait(3000);
			//			click("Create_Button");
			//			driverwait(1000);
			//			click("Clear_Button");
			driver.navigate().back();
			return templatename;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating Templates");
			return null;
		}
	}




	/*
	 * Description: This method used to create Connectors HTTP & FILE Connector can
	 * be created using JSON & CSV
	 */
	//	public static String httpConnectorName;
	//	public static String fileConnectorName;

	public void createConnector(String ConnectorName, String connectorType, String pathURL) {
		try {
			pathUralRequired = pathURL;
			click("ORCHESTRATION");
			click("Create_Connector_Button");
			connectorname = ConnectorName + BaseTest.generateDynamicData();
			input("ConnectorName_Input", ConnectorName + BaseTest.generateDynamicData());
			selectOptionFromDivList("Type", connectorType);
			if (connectorType.equalsIgnoreCase("HTTP")) {
				driverwait(2000);
				processToHTTPconnector(ConnectorName);
				Reporter.log("Created HTTP connector: "+connectorname);
				// this.processToHTTPconnector("CSV");
			}

			else if (connectorType.equalsIgnoreCase("FILE")) {
				processToFileConnector(ConnectorName);
				Reporter.log("Created FILE connector: "+connectorname);

			}

			if (ConnectorName.contains("No_Auth"))
				BaseTest.createdConnectorNames.put("ConnectorNoAuth", connectorname);
			else if (ConnectorName.contains("Basic_Auth"))
				BaseTest.createdConnectorNames.put("ConnectorBasicAuth", connectorname);
			else if (ConnectorName.contains("Custom_Auth"))
				BaseTest.createdConnectorNames.put("ConnectorCustomAuth", connectorname);
			else if (ConnectorName.contains("FILE"))
				BaseTest.createdConnectorNames.put("ConnectorFile", connectorname);

		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating Connector");

		}
	}

	/*
	 * Description: This method used to create DataSets
	 */

	public void dataSetCreation(String ProviderType, String DatasetName, String Outputformat) {
		try {
			
	
			click("app_header");
			click("Dataset_Tab");
			click("Create_Dataset");
			input("SearchProvider", ProviderType);
			//			driverwait(2000);
			clickAndHold("Source", "Destination");
			if (ProviderType.equalsIgnoreCase("HTTP Connector")) {

				if (DatasetName.contains("No_Auth"))
					input("searchConnector", createdConnectorNames.get("ConnectorNoAuth"));
				else if (DatasetName.contains("Basic_Auth"))
					input("searchConnector", createdConnectorNames.get("ConnectorBasicAuth"));
				else if (DatasetName.contains("Custom_Auth"))
					input("searchConnector", createdConnectorNames.get("ConnectorCustomAuth"));
			} else if (ProviderType.equalsIgnoreCase("File Connector")) {
				input("searchConnector", createdConnectorNames.get("ConnectorFile"));

			}
			click("selectRadioButton");
			click("Proceed");
			//			input("SearchProvider", "");
			//			driverwait(2000);
			click("NewModule");
			datasetname = DatasetName + BaseTest.generateDynamicData();
			input("DatasetName", datasetname);
			click("Tick_Mark");

			click("OutputType_DropDown");
			click(replaceString("Templete_Type", "REPLACE_STRING", Outputformat));

			if (Outputformat.equals("GeoJSON") || Outputformat.equals("JSON") || Outputformat.equals("CSV")
					|| Outputformat.equals("Text") || Outputformat.equals("HTML") || Outputformat.equals("XML")) {

				click("SaveDataset");
				driverwait(3000);
				verifyToastNotifications("Dataset Created Successfully");
				//reports
				Reporter.log("DataSet: "+datasetname+" Created Successfully");
			}

			else {
				System.out.println("No Matching outputsFormats are found");
				//reports
				Reporter.log("No Matching outputsFormats are found");
			}


			input("SearchProvider", datasetname);
			click("Datasets_Grid");
			// Thread.sleep(4000);
			// getLocatorTypeList("Providers_Dropdown");
			List<WebElement> createdDataSet = driver.findElements(By.xpath("//*[@class = 'side-menu-item']"));
			if (createdDataSet.size() > 0) {
				System.out.println("created data is " + datasetname);
				Reporter.log("created data is " + datasetname);
			} else {
				System.out.println("data not created");
				Reporter.log("data not created");
			}


		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while creating DataSets");
		}
	}

	// *Validations*//

	/*
	 * Description: This method is used to Validate Created Connector
	 */
	public void validation_CreatedConnector() {
		try {
			click("ORCHESTRATION");
			driverwait(1000);
			click("SEARCH");
			input("SEARCH", connectorname);
			getLocatorTypeList("ConnectorGrid");
			if (getLocatorTypeList("ConnectorGrid").size() > 0) {
				for (WebElement webElement : getLocatorTypeList("ConnectorGrid")) {
					System.out.println(webElement.getText());
				}
				Reporter.log("Validated Connector Successfully ");
			} else {
				System.out.println("data not created");
			}
			// click("Back_to_Dashboard_Button");

		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while Validating DataSets");
		}
	}

	/*
	 * Description: This method is used to Validate Created Template
	 */
	public void validation_CreatedTemplate() {
		try {
			click("ORCHESTRATION");
			click("Connection_Template");

			selectOptionFromDivList("Select Type", file_Type);
			//
			//			click("CLICK_TEMPLATE_MAR_SELECT");
			//			click(replaceString("Templete_Type", "REPLACE_STRING", file_Type));
			input("Search_Input", templatename);
			getLocatorTypeList("Template_Grid");
			if (getLocatorTypeList("Template_Grid").size() > 0) {
				for (WebElement webElement : getLocatorTypeList("Template_Grid")) {
					System.out.println(webElement.getText());
				}
				Reporter.log("Validated Template Successfully ");
			} else {
				System.out.println("data not created");
			}
			driver.navigate().back();
			// driver.navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while Validating Templates");
		}
	}

	/*
	 * Description: This method is used to Validate Created DataSets
	 */
	public void validate_createdDataset() {
		try {
			click("Back_To_List_View");
			click("Proceed_Button");
			click("SEARCH");
			input("SEARCH", datasetname);

			getLocatorTypeList("ConnectorGrid");
			if (getLocatorTypeList("ConnectorGrid").size() > 0) {
				for (WebElement webElement : getLocatorTypeList("ConnectorGrid")) {
					System.out.println(webElement.getText());
				}
				Reporter.log("Validated Created DataSets Successfully ");
			} else {
				System.out.println("data not created");
			}
			driverwait(3000);
			click("Back_to_Dashboard_Button");
			click("HomePage_AdminBtn");
			click("ORCHESTRATION");


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("There are some issue while Validating Created dataSets");
		}

	}

	// public DataSetsPage() {
	// templateNameMap.put("templates", 0);
	// templateNameMap.put("connector", 0);
	// }

	/*
	 * This code can be Removed Dataset Creation - T public void ConfirmToProceed()
	 * { try { if (driver.findElements(By.xpath(
	 * "//*[@id='mat-dialog-1']//span[contains(text(),'Proceed')]")) .size() != 0) {
	 * click("ConfirmToProceed"); } } catch (Exception e) { // TODO: handle
	 * exception }
	 * 
	 * }
	 */

	public boolean selectOptionFromDivList(String dropdown, String itemtoselect) {

		// WebDriverWait wait = new WebDriverWait(driver,600);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathkey)));

		try {

			click(replaceString("DropDown_Type", "REPLACE_STRING", dropdown));
			click(replaceString("DropDownOption_To_Select", "REPLACE_STRING", itemtoselect));

			// WebElement divlistContainer = getLocatorType(dropdown);
			// // WebElement divlistContainer = driver.findElement(By.xpath(xpathkey));
			// List<WebElement> divlistitems = divlistContainer
			// .findElements(By.xpath("//div[starts-with(@id,'app_mainView_policesView_policyTypes_checkbox')]"));
			// System.out.println(divlistitems.size());
			// for (WebElement divitem : divlistitems) {
			//
			// String item = divitem.getText();
			// System.out.println(item);
			// if (item.contains(itemtoselect)) {
			// // wait.until(ExpectedConditions.visibilityOfAllElements(divlistitems));
			// divitem.click();
			// break;
			// }
			//
			// }

		} catch (Exception e) {
			Reporter.log("Item could not be selected in div list : " + dropdown);
			System.out.println("Item could not be selected in div list: " + dropdown);
			takeScreenshot("objectnotfound.jpg");
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
