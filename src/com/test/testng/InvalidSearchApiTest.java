package com.test.testng;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;
import com.test.helpers.RestClient;
import com.test.helpers.TestProperties;

/**
 * This test suite tests the invalid input for searchAPI and validates that HTTP 500 is not returned
 *   An expected http status code should be returned.
 *   
 * @author imalonzo
 *
 */
public class InvalidSearchApiTest {
	public RestClient rc;

    @Before
    public void initialize() {
        rc = RestClient.getInstance();    
    }

    /**
     * Test searchApi invalid limit parameter
     * 	input: valid term and INVALID  limit; i.e, limit=foo
     * 		
     * @throws Exception
     */
	@Test
	public void testInValidLimit() throws Exception {
		JSONObject json = null;
		try {
			json = rc.sendGet(TestProperties.getSearchPath() + "?term=jim+jones&limit=foo");
		}
		catch(Exception e) {
			Assert.assertNotNull("Exception is thrown with invalid limit parameter",null);
		}
	}
	
    /**
     * Test searchApi invalid country paramater
     * 	input: valid term and INVALID country parameter
     * 		check that HTTP status is returned properly
     * @throws Exception
     */
	@Test
	public void testInValidCountry() {
		JSONObject json = null;
		String result = null;
		try {
			json = rc.sendGet(TestProperties.getSearchPath() + "?term=jim+jones&country=FOO");
			result = json.getString("status");
		} catch (JSONException je) {
			je.printStackTrace();
		}
		Assert.assertTrue("Return record failed for country", result.equals("400"));
	}

    /**
     * Test searchApi invalid media paramater
     * 	input: valid term, INVALID media as FOO 
     * 		
     * @throws Exception
     */
	@Test
	public void testInValidMedia() throws Exception {
		JSONObject json = null;
		String result = null;
		try {
			json = rc.sendGet(TestProperties.getSearchPath() + "?term=jim+jones&country=FOO");
			result = json.getString("status");
		} catch (JSONException je) {
			je.printStackTrace();
		}
		Assert.assertTrue("Return record failed for media", result.equals("400"));
	}

    /**
     * Test searchApi invalid term paramater
     * 	input: INVALID term; i.e, blank and limit is set to 1
     * 		
     * @throws Exception
     */
	@Test
	public void testInValidTerm() throws Exception {
		JSONObject json = rc.sendGet(TestProperties.getSearchPath() + "?term=&limit=1");
		Assert.assertTrue("Return record failed for term", Integer.valueOf(json.getString("resultCount")) == 0);
	}

}
