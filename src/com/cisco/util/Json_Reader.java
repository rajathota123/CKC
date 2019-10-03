package com.cisco.util;

import java.io.File;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cisco.base.BaseTest;

import org.json.simple.parser.JSONParser;



public class Json_Reader extends BaseTest{

	
public static JSONArray readData(String domain){
	try {		
	JSONParser parser = new JSONParser();

  
	JSONObject obj = (JSONObject) parser.parse(BaseTest.getJsonFile(domain));

  JSONArray jarray  = (JSONArray) obj.get("data");
    return jarray ; 

	}
	catch(Exception e){
		e.getStackTrace();
	}
	return null;
	}


public static JSONObject readDataObject(String domain){
	try {		
	JSONParser parser = new JSONParser();

  
	JSONObject obj = (JSONObject) parser.parse(BaseTest.getJsonFile(domain));

  
    return obj ; 

	}
	catch(Exception e){
		e.getStackTrace();
	}
	return null;
	}

}