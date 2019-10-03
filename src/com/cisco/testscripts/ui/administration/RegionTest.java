package com.cisco.testscripts.ui.administration;

import java.util.ArrayList;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.cisco.base.DataProviderr;
import com.cisco.base.BaseTest;

import com.cisco.commonfunctions.uiapplibrary.LoginPage;
import com.cisco.commonfunctions.uiapplibrary.RegionPage;
import com.cisco.util.ErrorUtil;

public class RegionTest extends BaseTest {

	LoginPage loginPage = null;

	String latlong = null;
	String regionName = null;
	RegionPage regionPage = new RegionPage();
	public static ArrayList<String> locs = new ArrayList<String>();
	public static boolean regionTest = false;

	@Test(dataProvider = "CreateRegion", dataProviderClass = DataProviderr.class, priority = 1)
	public void testCreateRegion(Hashtable<String, String> data, ITestContext ctx) {
		driverwait(10000);
		
		selectMenuToolBar("MENU_ICON", "Notifications");
//		selectMenuToolBar("MENU_ICON", "Regions");

		driverwait(15000);

		/*
		 * try{ lp= new LandingPage(); regionPage= lp.gotoRegionManagmentPage();
		 * if(regionPage==null){ BaseTest.takeScreenshot("NotabletoGotoRegionPage.jpg");
		 * Assert.fail(); } } catch (Exception e) {
		 * System.out.println("Exception occured in navigating to Region BaseTest");
		 * Reporter.log("Exception occured in navigating to Region BaseTest");
		 * e.getStackTrace(); BaseTest.takeScreenshot("NotabletoGotoRegionPage.jpg");
		 * ErrorUtil.addVerificationFailure(e);
		 * Assert.fail("Exception occured in navigating to Region BaseTest"); }
		 */

		// create region
		regionPage.clickRegionTab();
		regionName = data.get("RegionName") + BaseTest.generateDynamicData();
		regionPage.setRegionname(regionName);

		for (int i = 0; i < locs.size(); i++) {
			// regionPage.selectCountry("Root");
			regionPage.selectCountry(locs.get(i));

		}

		locs.add(regionName);
		regionTest = regionPage.isregionCreatedbylatlong(data.get("LatLong"), regionName);
		if (regionTest) {
			Reporter.log("Region created successfully Region name is " + regionName);
			System.out.println("Region created successfully Region name is" + regionName);
		}
	}

	@Test(dataProvider = "UpdateRegion", dataProviderClass = DataProviderr.class, priority = 2)
	public void updateRegionTest(Hashtable<String, String> data, ITestContext ctx) {
		ctx.setAttribute("testName", data.get("testName"));
		String latlong = data.get("LatLong");
		try {
			// driver.switchTo().window(winHandleBefore);
			driver.navigate().refresh();
			Thread.sleep(20000);
			selectMenuToolBar("MENU_ICON", "Regions");
			driverwait(15000);
			boolean test = regionPage.isregionUpdated(locs.get(1), latlong, locs.get(0));
			if (!test) {
				BaseTest.takeScreenshot("Regionnotupdated");
				Assert.fail("exception occured while updating Region.");
			}
		} catch (Exception e) {
			System.out.println("exception occured while updating Region.");
			Reporter.log("exception occured while updating Region.");
			BaseTest.takeScreenshot("regionnotupdated.jpg");
			ErrorUtil.addVerificationFailure(e);
			Assert.fail();
		}
	}

	@Test(dataProvider = "DeleteRegionTest", dataProviderClass = DataProviderr.class, priority = 3)
	public void deleteRegionTest(Hashtable<String, String> data, ITestContext ctx) {
		ctx.setAttribute("testName", data.get("testName"));
		// regionName = data.get("RegionName");
		/*
		 * ArrayList locss=new ArrayList(); locss.add("TestIndia131439");
		 * locss.add("Karnataka131518"); locss.add("Bangalore131612");
		 */
		ArrayList locss = locs;
		System.out.println(regionPage.getRegionname());
		boolean test = false;
		try {
			driver.navigate().refresh();
			Thread.sleep(20000);
			selectMenuToolBar("MENU_ICON", "Regions");
			driverwait(20000);

			String regionName = null;
			String country = null;
			int n = locss.size();
			for (int i = 0; i < n; i++) {
				if (i == 0) {

					regionName = locss.get(((locss.size() - 1) - i)).toString();
					country = locss.get(0).toString();
					selectCountry(country);
					driverwait(10000);
					click(replaceString("CLICK_DIV_TEXT", "REPLACE_STRING",
							locss.get(((locss.size() - 1) - i) - 1).toString()));
					Thread.sleep(5000);
				}
				if (i == 1) {
					regionName = locss.get(((locss.size() - 1))).toString();
					click(replaceString("CLICK_DIV_TEXT", "REPLACE_STRING",
							locss.get(((locss.size() - 1))).toString()));

				}
				if (i == 2) {
					regionName = country;
					// click(replaceString("CLICK_DIV_TEXT","REPLACE_STRING",regionName));
				}

				Thread.sleep(5000);
				test = regionPage.isregionDeleted(regionName, country);
				if (i == 0) {
					locss.remove(((locss.size() - 1) - i));
				}
				if (i == 1) {
					locss.remove(((locss.size() - 1)));
				}
			}
			/*
			 * click(replaceString("CLICK_DIV_TEXT","REPLACE_STRING",country));
			 * Thread.sleep(5000); test = regionPage.isregionDeleted(regionName,country);
			 */
			if (!test) {
				BaseTest.takeScreenshot("Regionnotdeleted");
				Assert.fail();
			}
			driver.close();
		} catch (Exception e) {
			System.out.println("exception occured while deleting Region.");
			Reporter.log("exception occured while deleting Region.");
			BaseTest.takeScreenshot("regionnotdeleted.jpg");
			ErrorUtil.addVerificationFailure(e);
			Assert.fail();
		}

	}

	// @Test(dataProvider="CreateRegion",dataProviderClass=DataProviderr.class)
	public void testCreateRegion(Hashtable<String, String> data) {
		driverwait(10000);
		selectMenuToolBar("MENU_ICON", "Regions");

		driverwait(15000);

		/*
		 * try{ lp= new LandingPage(); regionPage= lp.gotoRegionManagmentPage();
		 * if(regionPage==null){ BaseTest.takeScreenshot("NotabletoGotoRegionPage.jpg");
		 * Assert.fail(); } } catch (Exception e) {
		 * System.out.println("Exception occured in navigating to Region BaseTest");
		 * Reporter.log("Exception occured in navigating to Region BaseTest");
		 * e.getStackTrace(); BaseTest.takeScreenshot("NotabletoGotoRegionPage.jpg");
		 * ErrorUtil.addVerificationFailure(e);
		 * Assert.fail("Exception occured in navigating to Region BaseTest"); }
		 */

		// create region
		latlong = data.get("LatLong");
		regionName = data.get("RegionName") + BaseTest.generateDynamicData();
		regionPage.setRegionname(regionName);
		System.out.println("Region created successfully Rgion name is" + regionName);

		regionPage.isregionCreatedbylatlong(latlong, regionName);
		Reporter.log("Region created successfully Rgion name is " + regionName);

	}

	@Test(dataProvider = "createregionnegativeTest", dataProviderClass = DataProviderr.class)
	public void createRegionNegativeTest(Hashtable<String, String> data, ITestContext ctx) {
		selectMenuToolBar("MENU_ICON", "Regions");
		ctx.setAttribute("testName", data.get("testName"));
		driverwait(5000);

		latlong = data.get("LatLong");
		regionName = data.get("RegionName") + BaseTest.generateDynamicData();
		;
		boolean negativeCreatetest = regionPage.isregioncreated(latlong, regionName, data.get("country"));
		if (!negativeCreatetest) {

			Assert.fail();
		}
	}

	// @Test(dataProvider="CreateRegion",dataProviderClass=DataProviderr.class,dependsOnMethods="updateRegionTest")
	public void deleteRegionTest(Hashtable<String, String> data) {

		regionName = data.get("RegionName");
		System.out.println(regionPage.getRegionname());

		try {
			boolean test = regionPage.isregionDeleted(regionPage.getRegionname(), data.get("country"));
			if (!test) {
				BaseTest.takeScreenshot("Regionnotdeleted");
				Assert.fail();
			}
		} catch (Exception e) {
			System.out.println("exception occured while deleting Region.");
			Reporter.log("exception occured while deleting Region.");
			BaseTest.takeScreenshot("regionnotdeleted.jpg");
			ErrorUtil.addVerificationFailure(e);
			Assert.fail();
		}

	}

	// @Test(dataProvider="UpdateRegion",
	// dataProviderClass=DataProviderr.class,dependsOnMethods="testCreateRegion")
	public void updateRegionTest(Hashtable<String, String> data) {

		latlong = data.get("LatLong");
		try {
			boolean test = regionPage.isregionUpdated(regionPage.getRegionname(), latlong, data.get("country"));
			if (!test) {
				BaseTest.takeScreenshot("Regionnotupdated");
				Assert.fail("exception occured while updating Region.");
			}
		} catch (Exception e) {
			System.out.println("exception occured while updating Region.");
			Reporter.log("exception occured while updating Region.");
			BaseTest.takeScreenshot("regionnotupdated.jpg");
			ErrorUtil.addVerificationFailure(e);
			Assert.fail();
		}
	}

	public static void selectCountry(String country) {
		click(replaceString("SELECT_REGION", "REPLACE_STRING", country));
	}

}
