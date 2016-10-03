package com.test.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TestProperties {
	private static String TEST_PROPERTIES = "test.properties";

	public static String getSearchPath() {
	    Log logger = LogFactory.getLog(TestProperties.class);
		Properties prop = new Properties();
	    InputStream input = null;
	    String res = null;

	    try {
	    	input = TestProperties.class.getClassLoader().getResourceAsStream(TEST_PROPERTIES);
	    	if(input==null){
	    		logger.debug("Unable to find " + TEST_PROPERTIES);
	    	}
	    	prop.load(input);
	        logger.info(prop.getProperty("searchString"));
	        return prop.getProperty("searchString");


	   } catch (IOException ioe) {
		   ioe.printStackTrace();
	   } finally{
		   if(input!=null){
			   try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	   }
	   return res;
	}
}
