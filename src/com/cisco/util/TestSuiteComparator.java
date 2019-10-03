package com.cisco.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.testng.ISuite;
import org.testng.xml.XmlSuite;

public class TestSuiteComparator implements Comparator<ISuite> {

    public List<String> xmlNames;

    public TestSuiteComparator(List<XmlSuite> parentXmlSuites) {
        for (XmlSuite parentXmlSuite : parentXmlSuites) {
            List<XmlSuite> childXmlSuites = parentXmlSuite.getChildSuites();
            xmlNames = new ArrayList<String>();
            xmlNames.add(parentXmlSuite.getFileName());
            for (XmlSuite xmlsuite : childXmlSuites) {
                xmlNames.add(xmlsuite.getFileName());
            }
        }
    }

    @Override
    public int compare(ISuite suite1, ISuite suite2) {
        String suite1Name = suite1.getXmlSuite().getFileName();
        String suite2Name = suite2.getXmlSuite().getFileName();
        return xmlNames.indexOf(suite1Name) - xmlNames.indexOf(suite2Name);
    }
}