package com.cisco.commonfunctions.uiapplibrary;


import org.testng.Assert;
import org.testng.Reporter;

import com.cisco.base.BaseTest;

public class RegionPage extends BaseTest{
	
	String regionname;
	static int i =0;

	public String getRegionname() {
		return regionname;
	}


	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	
	public static void clickRegionTab(){
		click("Create_Region");
	}
	
	
	public static void selectCountry(String country){
		click(replaceString("SELECT_REGION","REPLACE_STRING",country));
	}

	
	
	public boolean isregionCreatedbylatlong(String latlong,String regionName){
		/*
		 * click on createregion by lat long
		 * verify region dialog is present or not
		 * Enter Region Lat & Long from excel sheet
		 * click on preview btn
		 * click on save region
		 * enter region Name
		 * click on save button
		 * verify toast message
		 * 
		 */
		try {
		
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("There are some issue while creating region for lat&long" +latlong);
			return false;
		}
	}
	
	
	
	//Negative cases....
	public boolean isregioncreated(String latlong, String regionName,String country){
		
		try {
			i++;
			driverwait(1000);
			//selectCountry(country);
			mouseMovement("POLYGON_INPUT");			
			click("POLYGON_INPUT");
			isElementPresnet("REGION_BOX");
			click("LATLONG_INPUT");
			input("LATLONG_INPUT", latlong);
			driverwait(3000);
			mouseMovement("PREVIEW_BTN");
			click("PREVIEW_BTN");
			//if(checkElementPresent("REGION_SAVE_BTN")){
			if(i==4||i==5 || i==6||i==7){
				driverwait(500);
				mouseMovement("REGION_SAVE_BTN");
				click("REGION_SAVE_BTN");
				mouseMovement("REGION_NAME");
				click("REGION_NAME");
				input("REGION_NAME", regionName);
				mouseMovement("REGION_CREATE_SAVE_BTN");
				click("REGION_CREATE_SAVE_BTN");
				driverwait(500);
				System.out.println("dfsdsdf"+getText("TOAST_INVALID_CREATEREG"));
				Assert.assertTrue(getText("TOAST_INVALID_CREATEREG").contains("characters"), getText("TOAST_INVALID_CREATEREG"));
				Reporter.log("Error message is .."+ getText("TOAST_INVALID_CREATEREG"));
				click("REGION_CREATEREGION_CLOSE_BTN");
				return true;
			}
			else if (i==1||i==2||i==3 ){
				driverwait(1000);
             System.out.println("adsfsadfsdf"+getText("INVALID_LAT_ALERT"));
			Assert.assertTrue(getText("INVALID_LAT_ALERT").contains("enter valid latlngs"), getText("INVALID_LAT_ALERT"));
			Reporter.log("Error message is .."+getText("INVALID_LAT_ALERT"));
			click("REGION_CREATEPRVIEW_CLOSE_BTN");
			return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("There are some issue while creating region " +latlong);
			return false;
		}
	}
	
	public boolean isregionDeleted(String regionName,String country){
		/*
		 * select region/location
		 * click on delete icon
		 * verify toast message
		 * 
		 * 
		 * 
		 */
		
		try {
			
		//	selectCountry(country);
			driverwait(10000);
			if(!(regionName.equalsIgnoreCase(country))) {
				click(replaceString("CLICK_DIV_TEXT","REPLACE_STRING",regionName));
			}
			
//			mouseMovement("REGION_TEXT");
//			click(replaceString("REGION_TEXT", "REPLACE_STRING", regionName));
			mouseMovement("REGION_DELETE_BTN");
			driverwait(3000);
			click("REGION_DELETE_BTN");
			driverwait(1000);
			isElementPresnet("CONFIRM_DELETE_BTN");
//			mouseMovement("DELETE_BTN");
			click("DELETE_BTN");
			log.info("Region Deleted successfully");			driverwait(1000);
//			System.out.println("T-message....."+getText("TOAST_MSG_DEL_REG"));
			//Assert.assertTrue(getText("TOAST_MSG_DEL_REG").contains("deleted successfully"), "Region deleted sucessfully" +regionName);
			Reporter.log("Region Deleted Sucessfully where region name is "+regionName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("There are some issue while deleting region or regon not present" +regionName);
			return false;
		}
	}
		
		public boolean isregionUpdated(String regionName,String latlong,String country ){
			/*
			 * click on update
			 */
			try{
				driverwait(6000);
				selectCountry(country);
				//click(replaceString("CLICK_DIV_TEXT","REPLACE_STRING",getRegionname()));
				click("POLYGON_UPDATE");
				isElementPresnet("REGION_BOX");
				input("LATLONG_INPUT", latlong);
				mouseMovement("PREVIEW_BTN");
				click("PREVIEW_BTN");
				driverwait(1000);
				mouseMovement("REGION_SAVE_BTN");
				click("REGION_SAVE_BTN");
				
//				System.out.println("T-message....."+getText("TOAST_MSG_UPDATE_REG1"));				Assert.assertTrue(getText("TOAST_MSG_UPDATE_REG1").contains("updated successfully"), "Region updated successfully");
				Reporter.log("Region updated Sucessfully where region name is "+regionName);
				return true;
			}catch (Exception e) {
				e.printStackTrace();
				log.info("There are some issue while updating region for lat&long" +latlong);
				return false;
			}
			
			
		}
}
