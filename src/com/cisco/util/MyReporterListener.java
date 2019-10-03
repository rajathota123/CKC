package com.cisco.util;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;




import org.testng.IInvokedMethod;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.TestResult;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import com.cisco.base.BaseTest;



public class MyReporterListener extends MyReporterListenerAdapter {
static String tenantName;
    private static final Logger L = Logger.getLogger(MyReporterListener.class);

    // ~ Instance fields ------------------------------------------------------

    private PrintWriter m_out;

    private int m_row;

    private   Integer m_testIndex;

    private int m_methodIndex;

    private Scanner scanner;
     public static java.util.Date now;

     public static boolean tResultCount;
    // ~ Methods --------------------------------------------------------------
    
    public void setTenant(String tenantName){
    	this.tenantName=tenantName;
    }
    public String getTenant(){
    	if(tenantName==null){
    		tenantName ="WSO2-APIManager";
    		};
    	return tenantName;
    }

    /** Creates summary of the run */
    @Override
    public void generateReport( List<XmlSuite> xml, List<ISuite> suites, String outdir) {
        try {
            m_out = createWriter(System.getProperty("user.dir")+"//tenants//"+getTenant()+"//Test_emailable_report");
        } catch (IOException e) {
            L.error("output file", e);
            return;
        }

        startHtml(m_out);
        
        generateSuiteSummaryReport(suites);
        generateMethodSummaryReport(suites);
        generateMethodDetailReport(suites);
        endHtml(m_out);
        m_out.flush();
        m_out.close();
    }

    protected PrintWriter createWriter(String outdir) throws IOException {
    	
        now = new Date();
       if( new File(outdir).exists()){
    	   new File(outdir).delete();
       }
        new File(outdir).mkdirs();
       /* return  new PrintWriter(new BufferedWriter(new FileWriter(new File(
                outdir, "emailable-FON-report"+ DateFunctions.dateToDayAndTimeForFileName(now)+ ".html"))));*/
        
        return  new PrintWriter(new BufferedWriter(new FileWriter(new File(
                outdir, "Automation_Test_Report"+  ".html"))));
    }

    

   

    /**
     * Creates a table showing the highlights of each test method with links to
     * the method details
     */
    protected void generateMethodSummaryReport(List<ISuite> suites) {
        m_methodIndex = 0;
        startResultSummaryTable("methodOverview");
        m_out.print("<h2>  Detail Test Execution  Report</h2>");
         int testIndex = 1;
       for (ISuite suite : suites) {
            if (suites.size() > 1) {
                //titleRow(suite.getName(), 5);
            }
           
            
            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
                ITestContext testContext = r2.getTestContext();
                String testName = testContext.getName();
               
          
                m_testIndex = testIndex;
               
               resultSummary(suite, testContext.getFailedConfigurations(),
                        testName, "failed", " (configuration methods)");
               resultSummary(suite, testContext.getFailedTests(), testName,
                        "failed", "");
                resultSummary(suite, testContext.getSkippedConfigurations(),
                        testName, "skipped", " (configuration methods)");
                resultSummary(suite, testContext.getSkippedTests(), testName,
                        "skipped", "");
                resultSummary(suite, testContext.getPassedTests(), testName,
                        "passed", "");
                //getShortException1(testContext.getFailedTests());
               
                testIndex++;
            }
        }
        m_out.println("</table>");
    }

    /** Creates a section showing known results for each method */
    protected void generateMethodDetailReport(List<ISuite> suites) {
        m_methodIndex = 0;
        for (ISuite suite : suites) {
        	tResultCount =true;
            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
         
                ITestContext testContext = r2.getTestContext();
                
                try {
                	
             	   
             	  // System.out.println("testResult  "+testContext.getFailedTests().size());
             	 	
             	
             	  if(testContext.getFailedTests().size() > 0 ){
           			tResultCount =true;
           	   }
             	  else{
           		       tResultCount =false;
             	   }
           	   
          
                   try{
                   	File file = new File(System.getProperty("user.dir")+"//tenants//"+tenantName+"//mainbody.txt");
                     	
                     	BufferedWriter writer = new BufferedWriter(new FileWriter(file));          
                 	if(tResultCount){         	
                 	
                 		writer.write("TEST FAILED");      	
                 	}    	
               
                 	else{
                 		writer.write("TEST PASSED");          		
                 	}
                 	writer.close();
                   }
                   catch(Exception e){
                   	e.printStackTrace();
                   }

           	
               
                } catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
           	
                if (r.values().size() > 0) {
                 m_out.println("<h1>" +testContext.getName() + "</h1>");
                }
                resultDetail(testContext.getFailedConfigurations());
                resultDetail(testContext.getFailedTests());
                resultDetail(testContext.getSkippedConfigurations());
                resultDetail(testContext.getSkippedTests());
                resultDetail(testContext.getPassedTests());
            }
        }
    }

    /**
     * @param tests
     */
    private void resultSummary(ISuite suite, IResultMap tests, String testname,
            String style, String details) {
        if (tests.getAllResults().size() > 0) {
            StringBuffer buff = new StringBuffer();
            String lastClassName = "";
            int mq = 0;
            int cq = 0;         
            
           // for (ITestNGMethod method : getMethodSet(tests, suite)) {
            for (ITestResult testResult : tests.getAllResults()) {
              
            	ITestNGMethod method = testResult.getMethod();
            	  
            	m_methodIndex++;
            	Set<ITestResult> resultSet = tests.getResults(method);
                m_row += 1;
                String cname = method.getTestClass().getName();
                
              //  m_methodIndex += 1;
               // ITestClass testClass = method.getTestClass();
                //String className = testClass.getName();
                
            if (mq == 0) {
                    String id = (m_testIndex == null ? null : "t"
                            + Integer.toString(m_testIndex));
                   titleRow(testname + " &#8214; " + style + details, 5, id);
                   // m_testIndex = null;
               }
                buff.setLength(0);
                if (!cname.equalsIgnoreCase(lastClassName)) {
                    /*if (mq > 0) {
                        cq += 1;
                        m_out.print("<tr class=\"" + style
                                + (cq % 2 == 0 ? "even" : "odd") + "\">"
                                + "<td");
                        if (mq > 1) {
                            m_out.print(" rowspan=\"" + mq + "\"");
                        }
                        m_out.println( buff);
                    }*/
                   // mq = 0;
                      
                   // lastClassName = className;
                }
                
                	
               // Set<ITestResult> resultSet = tests.getResults(method);
                long end = Long.MIN_VALUE;
                long start = Long.MAX_VALUE; 
      
                               	
                    if (testResult.getEndMillis() > end) {
                        end = testResult.getEndMillis();
                    }
                    if (testResult.getStartMillis() < start) {
                        start = testResult.getStartMillis();
                    }
                    mq += 1;
                    if (mq > 1) {
                        buff.append("<tr class=\"" + style
                                + (cq % 2 == 0 ? "odd" : "even") + "\">");
                    }
                    String description = method.getDescription();
                   
                    String testInstanceName = resultSet                    		
                            .toArray(new ITestResult[] {})[0].getTestName();
                    buff.append("<td><a href=\"#m"
                            + m_methodIndex
                            + "\">"
                            + qualifiedName(method)
                            + " "
                            + (description != null && description.length() > 0 ? "(\""
                                    + description + "\")"
                                    : "")
                            + "</a>"+ "</td>");
                            //+ (null == testInstanceName ? "" : "<br>("
                            //        + testInstanceName + ")") + "</td>"
                           // + "<td class=\"numi\">" + resultSet.size() + "</td>"
                           // + "<td>" + start + "</td>" + "<td class=\"numi\">"
                           // + (end - start) + "</td>" + "</tr>");
                
                    buff.append("<td>");
                    
                    List<String> msgs = Reporter.getOutput(testResult);
                    boolean hasReporterOutput = msgs.size() > 0;
                    Throwable exception = testResult.getThrowable();
                    //ITestClass testClass = method.getTestClass();
                    boolean hasThrowable = exception != null;
                    for (String line : msgs) {
                    	buff.append(line + "<br/>");
                    } 	
                    
                    if (hasThrowable) {
                        boolean wantsMinimalOutput = testResult.getStatus() == ITestResult.SUCCESS;
                        String str = Utils.stackTrace(exception, true)[0];
                        scanner = new Scanner(str);
                        buff.append("<div class=\"stacktrace\">");
                        buff.append(Utils.stackTrace(exception, true)[0]);
                        buff.append("</div>");
                        while(scanner.hasNext()){
                        String firstLine = scanner.next();
                        
                       // buff.append(firstLine); 
                        }
                      //buff.append(Utils.stackTrace(exception, true)[0]);
                    }
                  
                    buff.append( "</td>");
                     
                   
                if (mq > 0) {
                    cq += 1;
                   m_out.print("<tr class=\"" + style
                            + (cq % 2 == 0 ? "even" : "odd") + "\">" );
                   
                    if (mq > 1) {
                      //  m_out.print(" rowspan=\"" + mq + "\"");
                    }
                    buff.append( "</tr>");
                    m_out.print(buff);
                
                
                }
            }
        }
        }
        
   // }

    /** Starts and defines columns result summary table */
    private void startResultSummaryTable(String style) {
        tableStart(style, "  summary    ");
       /* m_out.println("<tr><th>Class</th>"
                + "<th>Method</th><th># of<br/>Scenarios</th><th>Start</th><th>Time<br/>(ms)</th></tr>");*/
       //My modification
        
        m_out.println( "<th> TestName </th><th>  Test message   </th>");//<th>Start</th><th>Time<br/>(ms)</th></tr>");
        m_row = 0;
    }

    private String qualifiedName(ITestNGMethod method) {
        StringBuilder addon = new StringBuilder();
        String[] groups = method.getGroups();
        int length = groups.length;
        if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
            addon.append("(");
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    addon.append(", ");
                }
                addon.append(groups[i]);
            }
            addon.append(")");
        }

        return "<b>" + method.getMethodName() + "</b> " + addon;
    }

    private void resultDetail(IResultMap tests) {
        for (ITestResult result : tests.getAllResults()) {
            ITestNGMethod method = result.getMethod();
            m_methodIndex++;
                        String cname = method.getTestClass().getName();
           m_out.println("<h2 id=\"m" + m_methodIndex + "\">" + cname + ":"
                    + method.getMethodName() + "</h2>");
            Set<ITestResult> resultSet = tests.getResults(method);
            generateForResult(result, method, resultSet.size());
           // m_out.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");

        }
    }

    /**
     * Write the first line of the stack trace
     * 
     * @param tests
     */
    private void getShortException(IResultMap tests) {

        for (ITestResult result : tests.getAllResults()) {
            m_methodIndex++;
            Throwable exception = result.getThrowable();
            List<String> msgs = Reporter.getOutput(result);
            boolean hasReporterOutput = msgs.size() > 0;
            boolean hasThrowable = exception != null;
            if (hasThrowable) {
                boolean wantsMinimalOutput = result.getStatus() == ITestResult.SUCCESS;
                if (hasReporterOutput) {
                    m_out.print("<h3>"
                            + (wantsMinimalOutput ? "Expected Exception"
                                    : "Failure") + "</h3>");
                }

                // Getting first line of the stack trace
                String str = Utils.stackTrace(exception, true)[0];
                scanner = new Scanner(str);
                String firstLine = scanner.nextLine();
                m_out.println(firstLine);
            }
        }
    }

    /**
     * Write all parameters
     * 
     * @param tests
     */
    private void getParameters(IResultMap tests) {

        for (ITestResult result : tests.getAllResults()) {
            m_methodIndex++;
            Object[] parameters = result.getParameters();
            boolean hasParameters = parameters != null && parameters.length > 0;
            if (hasParameters) {

                for (Object p : parameters) {
                    m_out.println(Utils.escapeHtml(Utils.toString(p)) + " | ");
                }
            }

        }
    }

    private void generateForResult(ITestResult ans, ITestNGMethod method,
            int resultSetSize) {
        Object[] parameters = ans.getParameters();
        boolean hasParameters = parameters != null && parameters.length > 0;
        if (hasParameters) {
            tableStart("result", null);
            m_out.print("<tr class=\"param\">");
           // for (int x = 1; x <= parameters.length; x++) {
                m_out.print("<th>Param." + 1 + "</th>");
          //  }
            m_out.println("</tr>");
            m_out.print("<tr class=\"param stripe\">");
           // for (Object p : parameters) {
                m_out.println("<td>" + Utils.escapeHtml(Utils.toString(parameters))
                        + "</td>");
           // }
            m_out.println("</tr>");
        }
        List<String> msgs = Reporter.getOutput(ans);
        boolean hasReporterOutput = msgs.size() > 0;
        Throwable exception = ans.getThrowable();
        boolean hasThrowable = exception != null;
        if (hasReporterOutput || hasThrowable) {
            if (hasParameters) {
                m_out.print("<tr><td");
                if (parameters.length > 1) {
                    m_out.print(" colspan=\"" + parameters.length + "\"");
                }
                m_out.println(">");
            } else {
                m_out.println("<div>");
            }
            if (hasReporterOutput) {
                if (hasThrowable) {
                    m_out.println("<h3>Test Messages</h3>");
                }
                for (String line : msgs) {
                    m_out.println(line + "<br/>");
                }
            }
            if (hasThrowable) {
                boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
                if (hasReporterOutput) {
                    m_out.println("<h3>"
                            + (wantsMinimalOutput ? "Expected Exception"
                                    : "Failure") + "</h3>");
                }
                generateExceptionReport(exception, method);
            }
            if (hasParameters) {
                m_out.println("</td></tr>");
            } else {
                m_out.println("</div>");
            }
        }
        if (hasParameters) {
            m_out.println("</table>");
        }
    }

    protected void generateExceptionReport(Throwable exception,
            ITestNGMethod method) {
        m_out.print("<div class=\"stacktrace\">");
        m_out.print(Utils.stackTrace(exception, true)[0]);
        m_out.println("</div>");
         }

    /**
     * Since the methods will be sorted chronologically, we want to return the
     * ITestNGMethod from the invoked methods.
     */
    private Collection<ITestNGMethod> getMethodSet(IResultMap tests,
            ISuite suite) {
        List<IInvokedMethod> r = Lists.newArrayList();
        List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
        for (IInvokedMethod im : invokedMethods) {
            if (tests.getAllMethods().contains(im.getTestMethod())) {
                r.add(im);
            }
        }
        Arrays.sort(r.toArray(new IInvokedMethod[r.size()]), new TestSorter());
        List<ITestNGMethod> result = Lists.newArrayList();

        // Add all the invoked methods
        for (IInvokedMethod m : r) {
            result.add(m.getTestMethod());
        }

        // Add all the methods that weren't invoked (e.g. skipped) that we
        // haven't added yet
        for (ITestNGMethod m : tests.getAllMethods()) {
            if (!result.contains(m)) {
                result.add(m);
            }
        }
        return result;
    }

    @SuppressWarnings("unused")
    public void generateSuiteSummaryReport(List<ISuite> suites) {
        tableStart("testOverview", null);
        
        m_out.print("<h2>  Test Summary of all the suites: </h2>");
        m_out.print("<tr"
                + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
                + "><td style=\"text-align:center;padding-right:2em\">Time Stamp:<td href=\"#t"
                + m_testIndex + "\">"  +now+ "</td>" + "</td>");
       
     // tableColumnStart("Error messages");
        // tableColumnStart("Parameters");
        //tableColumnStart("Start<br/>Time");
        //tableColumnStart("End<br/>Time");
        
       // tableColumnStart("Included<br/>Groups");
        //tableColumnStart("Excluded<br/>Groups");
        m_out.println("</tr>");
        NumberFormat formatter = new DecimalFormat("#,##0.0");
        int qty_tests = 0;
        int total;
        int qty_pass_m = 0;
        int qty_pass_s = 0;
        int qty_skip = 0;
        int qty_fail = 0;
        int qty_total = 0;
        long time_start = Long.MAX_VALUE;
        long time_end = Long.MIN_VALUE;
        m_testIndex = 1;
        
        for (ISuite suite : suites) {
        	Map<String, ISuiteResult> tests = suite.getResults();
            if (suites.size() > 1) {
                
            if(!tests.isEmpty()){
            	titleRow(suite.getName(), 2);
            m_out.print("<tr>"); 
           
            tableColumnStart("  TestScenario Name  ");
           //  tableColumnStart("Methods<br/>Passed");
           // tableColumnStart("Scenarios<br/>Passed");
            
            tableColumnStart("  #  Passed  ");
            tableColumnStart("  # failed   ");
            tableColumnStart("  # skipped  ");
            tableColumnStart("  # Total tests  ");
           tableColumnStart("Total<br/>Time");
            m_out.println("</tr>");
            }
            } 
            
            for (ISuiteResult r : tests.values()) {
            	qty_total=0;
               
                ITestContext overview = r.getTestContext();
                startSummaryRow(overview.getName());

                int q = getMethodSet(overview.getPassedConfigurations(), suite).size();
                qty_pass_m += q;
                //summaryCell(q, Integer.MAX_VALUE);
              q = overview.getPassedTests().size();
                qty_pass_s += q;
                qty_total +=q;
                qty_tests +=q;
                summaryCell(q, Integer.MAX_VALUE);
                
                q = overview.getFailedTests().size();
                qty_fail += q;
                qty_total +=q;
                qty_tests +=q;
                summaryCell(q, Integer.MAX_VALUE);
                q = overview.getSkippedTests().size();
                qty_skip += q;
                qty_total +=q;
                
                summaryCell(q, Integer.MAX_VALUE);
              //  System.out.println("sdfasdf"+qty_pass_s+qty_fail+qty_skip);
            
             summaryCell(qty_total, Integer.MAX_VALUE);

                summaryCell(
                        formatter.format((overview.getEndDate().getTime() - overview
                                .getStartDate().getTime()) / 1000.)
                                + " seconds", true);

                // NEW
                // Insert error found
              // m_out.print("<td class=\"numi" + (true ? "" : "_attn") + "\">");
               //getShortException(overview.getFailedTests());
               // getShortException(overview.getSkippedTests());
                //m_out.println("</td>");
                qty_tests +=qty_total;
            }
            
        }
        
      /*m_out.print("<tr"
                + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
                + "><td style=\"text-align:center;padding-right:2em;font-weight:bold\"> # Total:<td href=\"#t"
                + qty_total + "\">"  +qty_tests + "</td>" + "</td>");*/
        
    } 

    private void summaryCell(String[] val) {
        StringBuffer b = new StringBuffer();
        for (String v : val) {
            b.append(v + " ");
        }
        summaryCell(b.toString(), true);
    }

    private void summaryCell(String v, boolean isgood) {
        m_out.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
                + "</td>");
    }

    private void startSummaryRow(String label) {
        m_row += 1;
             
        m_out.print("<tr"
                + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
                + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t"
                + m_testIndex + "\">" + label + "</a>" + "</td>");
        m_testIndex++;
    }
   
    private void summaryCell(int v, int maxexpected) {
        summaryCell(String.valueOf(v), v <= maxexpected);
    }

    private void tableStart(String cssclass, String id) {
        m_out.println("<table cellspacing=\"0\" cellpadding=\"0\""
                + (cssclass != null ? " class=\"" + cssclass + "\""
                        : " style=\"padding-bottom:2em\"")
                + (id != null ? " id=\"" + id + "\"" : "") + ">");
        m_row = 0;
    }

    private void tableColumnStart(String label) {
        m_out.print("<th>" + label + "</th>");
    }

    private void titleRow(String label, int cq) {
        titleRow(label, cq, null);
    }
    

    private void titleRow(String label, int cq, String id) {
        m_out.print("<tr ");
        if (id != null) {
            m_out.print(" id=\"" + id + "\"");
        }
        m_out.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
        m_row = 0;
    }

    /** Starts HTML stream 
     * @param <var>*/
    protected <var> void startHtml(PrintWriter out) {
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");

        out.println("<h1 style=\"text-align:center;\"> Automation Execution Report For Tenant "+getTenant().toUpperCase()+" </h1>");
        out.println("<title>API Automation - TestNG ReportDE API Automation Report</title>");
        out.println("<style type=\"text/css\">");
        out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
        out.println("td,th {text-align: left;border:1px solid #009;padding:.50em .5em;}");
        out.println(".result th {vertical-align:bottom}");
        out.println(".param th {padding-left:1em;padding-right:1em}");
        out.println(".param td {padding-left:.5em;padding-right:2em}");
        out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
        out.println(".numi,.numi_attn {text-align:right}");
        out.println(".total td {font-weight:bold}");
        out.println(".passedodd td {background-color: #0A0}");
        out.println(".passedeven td {background-color: #0A0}");
        out.println(".skippedodd td {background-color: #CCC}");
        out.println(".skippedodd td {background-color: #DDD}");
        out.println(".failedodd td,.numi_attn {background-color:  #ff6666}");
        out.println(".failedeven td,.stripe .numi_attn {background-color:  #ff6666}");
        out.println(".stacktrace {white-space:pre;font-family:monospace}");
        out.println(".totop {font-size:85%;text-align:left;border-bottom:2px solid #000}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
    }

    /** Finishes HTML stream */
    protected void endHtml(PrintWriter out) {
       // out.println("<center> Report customized by [hectorfb@gmail.com] </center>");
        out.println("</body></html>");
    }

    // ~ Inner Classes --------------------------------------------------------
    /** Arranges methods by classname and method name */
    private class TestSorter implements Comparator<IInvokedMethod> {
        // ~ Methods
        // -------------------------------------------------------------

        /** Arranges methods by classname and method name */
        @Override
        public int compare(IInvokedMethod o1, IInvokedMethod o2) {
            // System.out.println("Comparing " + o1.getMethodName() + " " +
            // o1.getDate()
            // + " and " + o2.getMethodName() + " " + o2.getDate());
            return (int) (o1.getDate() - o2.getDate());
            // int r = ((T) o1).getTestClass().getName().compareTo(((T)
            // o2).getTestClass().getName());
            // if (r == 0) {
            // r = ((T) o1).getMethodName().compareTo(((T) o2).getMethodName());
            // }
            // return r;
        }
    }
}


//NEW
/*summaryCell(
        DateFunctions.dateToDayAndTime(overview.getStartDate()),
        true);
m_out.println("</td>");
summaryCell(
        DateFunctions.dateToDayAndTime(overview.getEndDate()),
        true);
m_out.println("</td>");
*/