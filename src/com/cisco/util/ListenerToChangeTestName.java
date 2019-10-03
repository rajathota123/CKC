package com.cisco.util;

import java.lang.reflect.Field;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.TestResult;

public class ListenerToChangeTestName extends TestListenerAdapter {
	
  @Override
    public void onTestSuccess(ITestResult tr) {
        setTestNameInXml(tr);
        super.onTestSuccess(tr);
    }
 @Override
    public void onTestFailure(ITestResult tr) {
        setTestNameInXml(tr);
        super.onTestFailure(tr);
    }


 @Override
 public void onTestSkipped(ITestResult tr) {
     setTestNameInXml(tr);
     super.onTestSkipped(tr);
 }
    private void setTestNameInXml(ITestResult tr) {
        try
        {
            ITestContext ctx = tr.getTestContext();
            Set<String> attribs = ctx.getAttributeNames();

            for ( String x : attribs ) {
                if (x.contains("testName")) {

                    Field method = TestResult.class.getDeclaredField("m_method");
                    method.setAccessible(true);
                    method.set(tr, tr.getMethod().clone());
                    Field methodName = BaseTestMethod.class.getDeclaredField("m_methodName");
                    methodName.setAccessible(true);
                    methodName.set(tr.getMethod(), ctx.getAttribute( x ));

                    break;
                }
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}