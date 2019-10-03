package com.cisco.util;

import java.io.File;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class WritingResponseInXml {

	//public class Customer {


//public class Customer {

    String name;
    int count;
    HashMap<String,String> envvalues = new     HashMap<String,String>();
    

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

  

    @XmlElement
    public void setenvValues(HashMap<String,String> envvalues) {
        this.envvalues = envvalues;
    }


    
    public HashMap<String,String> getenvValues() {
        return envvalues;
    }

    
}




/*package com.mkyong.core;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBExample {
    public static void main(String[] args) {

      Customer customer = new Customer();
      customer.setId(100);
      customer.setName("mkyong");
      customer.setAge(29);

      try {

        File file = new File("C:\\file.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(customer, file);
        jaxbMarshaller.marshal(customer, System.out);

          } catch (JAXBException e) {
        e.printStackTrace();
          }

    }
}*/