package com.test.testng;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;
import com.test.helpers.RestClient;
import com.test.helpers.TestProperties;

/**
 * This test suite tests the valid input for searchAPI and validates the result sets
 * @author imalonzo
 *
 */
public class SearchApiTest {
	public RestClient rc;

    @Before
    public void initialize() {
        rc = RestClient.getInstance();    
    }

    /**
     * Test searchApi limit parameter
     * 	input: valid term and limit is set to 2
     * 		
     * @throws Exception
     */
	@Test
	public void testValidLimit() throws Exception {
		JSONObject json = rc.sendGet(TestProperties.getSearchPath() + "?term=jim+jones&limit=2");
		Assert.assertTrue("Return record failed for limit=2", Integer.valueOf(json.getString("resultCount")) == 2);
	}
	
    /**
     * Test searchApi country paramater
     * 	input: valid term, country and limit is set to 1
     * 		
     * @throws Exception
     */
	@Test
	public void testValidCountry() throws Exception {
		JSONObject json = rc.sendGet(TestProperties.getSearchPath() + "?term=jim+jones&country=CA&limit=1");
		JSONObject result = (JSONObject) json.getJSONArray("results").get(0);
		String actualCountry =  result.getString("country");
		Assert.assertTrue("Return record failed for country", actualCountry.equals("CAN"));
	}

    /**
     * Test searchApi media paramater
     * 	input: valid term, media as song and limit is set to 1
     * 		
     * @throws Exception
     */
	@Test
	public void testValidMedia() throws Exception {
		JSONObject json = rc.sendGet(TestProperties.getSearchPath() + "?term=jim+jones&media=music&limit=1");
		JSONObject result = (JSONObject) json.getJSONArray("results").get(0);
		String actualMedia =  result.getString("kind");
		Assert.assertTrue("Return record failed for media", actualMedia.equals("song"));
	}

    /**
     * Test searchApi term paramater
     * 	input: valid term and limit is set to 1
     * 		
     * @throws Exception
     */
	@Test
	public void testValidTerm() throws Exception {
		JSONObject json = rc.sendGet(TestProperties.getSearchPath() + "?term=jim+jones&limit=1");
		JSONObject result = (JSONObject) json.getJSONArray("results").get(0);
		String actualTerm =  result.getString("artistName");
		Assert.assertTrue("Return record failed for term", actualTerm.equals("Jim Jones"));
	}

}
