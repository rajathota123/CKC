package com.cisco.util;




import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cisco.util.WritingResponseInXml;


public class JAXB {
	

public static Unmarshaller readXmlFile(File file){
	//readingResponseFromXml rd= new readingResponseFromXml();
	
	

	      try {

	        //File file = new File(System.getProperty("user.dir")+"//..//.."+"//RestAPIAutomationUsing_Java//APIAutomationTesting//responseData//"+ "env" +".xml");
	       // JAXBContext jaxbContext = JAXBContext.newInstance(WritingResponseInXml.class);
	        
	        JAXBContext jaxbContext = JAXBContext.newInstance(WritingResponseInXml.class);
	        Unmarshaller jaxbunMarshaller = jaxbContext.createUnmarshaller();
	        
	       

	        // output pretty printed
	        //jaxbunMarshaller.getProperty(arg0)

	       // jaxbunMarshaller.unmarshal(file);
	        
	        

	    
	        //jaxbMarshaller.marshal(wrx, System.out);
 
	        
	        return jaxbunMarshaller;
	     
	          } catch (JAXBException e) {
	        e.printStackTrace();
	        return null;
	          }
		
  
	    }

}
