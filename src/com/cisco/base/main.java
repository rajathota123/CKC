package com.cisco.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import com.cisco.util.MyReporterListener;
import com.google.common.collect.Lists;

public class main {
	public static void main(String[] args) throws URISyntaxException, IOException {
//TestListenerAdapter tla = new TestListenerAdapter();
//Scanner sc = new Scanner(System.in);
//	String testsuite= sc.nextLine();

		/*** read the ignore city list,if any tenant exist dont execute the test **/

		String csvFile = System.getProperty("user.dir") + "//ignore_city_list.csv";
		BufferedReader br = null;
		String line = "";

		String[] ignoreTenant = { "" };

		br = new BufferedReader(new FileReader(csvFile));

		while ((line = br.readLine()) != null) {
			ignoreTenant = line.split(",");
		}
		// read the domainForTenant and read tenant name and tests to be executed.
		File tdomainFile = new File(System.getProperty("user.dir") + "//domainsForTenant.txt");
		System.out.println(tdomainFile);
		BufferedReader br1 = new BufferedReader(new FileReader(tdomainFile));
		String st = "";
		String tenantName = "";

		String[] tName = { "" };
		String[] testName = { "" };
		String[] reportNames = { "" };

		while ((st = br1.readLine()) != null) {
			System.out.println(st);
			if (st.contains("#")) {
				tName = st.split("#");
				BaseTest.domainList.removeAll(BaseTest.domainList);
				BaseTest.reportTypeList.removeAll(BaseTest.reportTypeList);

				tenantName = tName[0];

				MyReporterListener myrep = new MyReporterListener();
				myrep.setTenant(tenantName);

				BaseTest.tenantName = tenantName;
				String[] parmasplit1 = { "" };
				String[] parmasplit = { "" };

				if (tName.length > 1) {
					String[] testNameParam = tName[1].split(",");
					System.out.println(testNameParam.length);
					for (int i = 0; testNameParam.length > i; i++) {
						System.out.println(testNameParam[i]);
						if (testNameParam[i].contains("(")) {
							parmasplit1 = testNameParam[i].split("\\(");
							BaseTest.domainList.add(parmasplit1[0]);
							parmasplit = parmasplit1[1].split("\\)");
							reportNames = parmasplit[0].split("-");
							for (String reportName1 : reportNames) {
								BaseTest.reportTypeList.add(reportName1);
								System.out.println("tests....." + reportName1);
							}
						} else {
							for (String testName1 : testNameParam) {
								BaseTest.domainList.add(testName1);
								System.out.println("tests....." + testName1);
							}

						}

					}
				}

				try {
					int j = 0;
					for (String t : ignoreTenant) {

						if (t.contains(tenantName)) {

							j++;

						}
					}
					if (j > 0) {
						Reporter.log("The tenant is not executed.. ");
					} else {
						TestNG testng = new TestNG();
						List<String> suites = Lists.newArrayList();
						suites.add(System.getProperty("user.dir") + "//common//testsuites//BatchTestSuite.xml");
						testng.setTestSuites(suites);
						testng.run();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
