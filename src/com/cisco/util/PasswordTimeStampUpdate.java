package com.cisco.util;


import java.io.InputStream;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.cisco.base.BaseTest;


public class PasswordTimeStampUpdate extends BaseTest {
	static Properties prop = new Properties();

	public  String UpdatePasswordTimeStamp(String Username) throws Exception {
		
		
		InputStream input = null;
		String tenantId = "";
		String userName = "";
		
			/*File file = new File(System.getProperty("user.dir")+"//src//com//cisco//config//application.properties");
			if(!file.exists()) {
				System.err.println("File Not found in the current directory : application.properties");
				System.exit(1);
			}
			input = new FileInputStream(file);
			prop.load(input);*/
			tenantId = setConfig("tenant");
			//userName = setConfig("userName");
			userName = Username;
			
			if(tenantId == null || tenantId.isEmpty() && userName == null || userName.isEmpty()) {
				System.err.println("Please provide tenant:\n");
				System.exit(1);
			}
			
			System.out.println("Started.....");

		
			final String tenant = tenantId;
			//UserService us = new UserService(prop);
			Map<String, String> propMap = new HashMap<String, String>();
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -125);
			System.out.println("passwordTimeStamp-> "+c.getTime());
			propMap.put("passwordTimestamp", String.valueOf(c.getTime().getTime()));
			try {
				updatePropertiesInLDAP(userName, tenant, propMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
				Map<String, String> map = getUserDetailsByUid(userName, tenant);
				System.out.println("passwordTimestamp-> "+map.get("passwordTimestamp"));
				System.out.println(map);
				
			
		System.out.println("Completed.....");
			
  		return map.get("passwordTimestamp");
  	}
  	public boolean updatePropertiesInLDAP(String uid, String tenandId, Map<String, String> propMap)
			throws Exception {
		Hashtable<String, String> jndiEnv = null;
		DirContext dctx = null;
		boolean flag = false;
		try {
			jndiEnv = getLdapEnv();
			dctx = new InitialDirContext(jndiEnv);
			// Create a container set of attributes
			final Attributes container = new BasicAttributes();
			if(propMap != null && propMap.size() > 0) {
				propMap.forEach((k,v)-> {  
					// Add property
					final Attribute property = new BasicAttribute(k, v);
					// Add these to the container
					container.put(property);
				} );
			}
			// Update the entry;
			dctx.modifyAttributes(getUserDN(uid,tenandId), DirContext.REPLACE_ATTRIBUTE, container);
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dctx) {
				try {
					dctx.close();
				} catch (final NamingException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	private String getUserDN(final String userName,final String tenantId) {
		//String userDN = new StringBuffer().append("uid=").append(userName).append(",ou=users,ou="+tenantId+","+prop.getProperty("user.mgnt.ldap.admin.dn2")).toString();
		String userDN = new StringBuffer().append("uid=").append(userName).append(",ou=users,ou="+tenantId+","+setConfig("user.mgnt.ldap.admin.dn2")).toString();
		return userDN;
	}
	public Map<String, String> getUserDetailsByUid(String uid, String tenantId) throws Exception {

		Hashtable<String, String> env = null;
		DirContext dctx = null;
		Map<String, String> map = null;
		try {

			env = getLdapEnv();

			dctx = new InitialDirContext(env);

			//String base = "ou=users,ou="+tenantId+","+prop.getProperty("user.mgnt.ldap.admin.dn2");
			String base = "ou=users,ou="+tenantId+","+setConfig("user.mgnt.ldap.admin.dn2");

			SearchControls sc = new SearchControls();
			
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String filter = "uid"+"="+uid;

			NamingEnumeration<SearchResult> results = dctx.search(base, filter, sc);

			while (results.hasMore()) {
				SearchResult sr = results.next();
				Attributes attrs = sr.getAttributes();
				if(attrs != null && attrs.size() > 0) {
				 NamingEnumeration<String> attributeIDs = attrs.getIDs();
			      if(attributeIDs != null) {
			    	  map = new HashMap<String, String>();
				      while(attributeIDs.hasMore()) {
				    	  String attrID = attributeIDs.next();
				    	  Attribute attribute = attrs.get(attrID);
				    	  if(attribute != null && !attribute.getID().equals("userPassword")) 
				    		  map.put(attribute.getID(), attribute.get().toString());
				      }
			      }
				}
			}
		} 
		catch (Exception e) {
			//logger.error("UserManagementServiceImpl:getUserEmailIdByUid() " + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally {
			try {
			  if(dctx != null) {	
				dctx.close();
			  }
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}

		return map;
	}
	
	private Hashtable<String, String> getLdapEnv() {
		Hashtable<String, String> env = new Hashtable<String, String>();
		String sp = "com.sun.jndi.ldap.LdapCtxFactory";
		env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

		// String ldapUrl = "ldap://10.106.9.228:389/dc=cisco,dc=com";
		//String ldapUrl = prop.getProperty("user.mgnt.ldap.ldapUrl");
		String ldapUrl = setConfig("user.mgnt.ldap.ldapUrl");
		env.put(Context.PROVIDER_URL, ldapUrl);
		// LDAP Read doesn't required authentication
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		//env.put(Context.SECURITY_PRINCIPAL, prop.getProperty("user.mgnt.ldap.admin.dn1").concat(","+prop.getProperty("user.mgnt.ldap.admin.dn2")));
		env.put(Context.SECURITY_PRINCIPAL, setConfig("user.mgnt.ldap.admin.dn1").concat(","+setConfig("user.mgnt.ldap.admin.dn2")));
		//env.put(Context.SECURITY_CREDENTIALS, prop.getProperty("user.mgnt.ldap.admin.password"));
		//env.put(Context.SECURITY_CREDENTIALS, new String(Base64.getDecoder().decode(prop.getProperty("user.mgnt.ldap.admin.password"))));
		env.put(Context.SECURITY_CREDENTIALS, new String(Base64.getDecoder().decode(setConfig("user.mgnt.ldap.admin.password"))));
		return env;
	}
	
	

}
