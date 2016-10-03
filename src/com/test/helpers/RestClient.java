package com.test.helpers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * RestClient - singleton class responsible for HTTP crud operation
 * 	sendGet - 
 * 		u - request url
 * 		JSONObject - JSON object result
 * 
 *  TODO:
 *  	sendPost()
 *  	sendPut()
 * 
 * 
 * @author imalonzo@gmail.com
 *
 */
public class RestClient {
    private static Log logger = LogFactory.getLog(RestClient.class);
	private volatile static RestClient instance;
	private String url;
	private final String USER_AGENT = "Mozilla/5.0"; // refactor this. Possibly put it on property files !! 

	private RestClient()  { }

	// returns singleton instance
	public static RestClient getInstance() {
		if (instance == null) {
			synchronized(RestClient.class) {
				if (instance == null) {
					instance = new RestClient();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 
	 * @param url - request URL
	 * @return JSONObject result
	 */
	@SuppressWarnings("deprecation")
	public JSONObject sendGet(String url)  {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);
		
		JSONObject jsonResult = null;
		HttpResponse response = null;

		StringBuffer result = new StringBuffer();
		try {
			response = client.execute(request);

			logger.info("\nSending 'GET' request to URL : " + url);
			logger.info("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				logger.debug("LINE: " + line);
				result.append(line);
			}
			jsonResult = new JSONObject(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsonResult == null) {
			jsonResult = new JSONObject();
			try {
				jsonResult.put("status", response.getStatusLine().getStatusCode());
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		return jsonResult;
	}
	
	// main
	public static void main(String[] args) throws JSONException {
		RestClient rc = RestClient.getInstance();
		JSONObject myObject = rc.sendGet("https://itunes.apple.com/search?term=jim+jones&country=US&limit=1");
		System.out.println(myObject.getString("resultCount"));
		JSONObject aa = (JSONObject) myObject.getJSONArray("results").get(0);
		System.out.println(aa.get("kind"));
		System.out.println(aa.get("country"));
	}
}