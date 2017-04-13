package com.hart.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class RestExecutor {

	public static String userId = "";
	public static String accessToken = "";
	public static final Logger logger=Logger.getLogger(RestExecutor.class);
	static {
		PropertyConfigurator.configure("resources/log4j.properties");
	}
	
	public RestValidator postLogin(String baseUrl, String username, String password) {
		logger.info("Verifying Post Login");
		JSONObject innerObject = new JSONObject();
		innerObject.put("email", username);
		innerObject.put("password", password);
		RestResponse resResponse = new RestResponse();
		try {
			HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/token")
				.header("content-type", "application/json")
				.body(innerObject.toString())
				.asJson();
			userId = response.getBody().getObject().getJSONObject("data").getString("user_id");
			accessToken = response.getBody().getObject().getJSONObject("data").getString("x-access-token");
			resResponse.setResponseCode(response.getStatus());
			logger.info("User Id: " + userId);
			logger.info("Access Token: " + accessToken);
		} catch(Exception e) {
			logger.info("Post Login Error: " + e.getMessage());
		}
		return new RestValidator(resResponse);
	}
	
	public RestValidator get(String baseUrl, String paramStringUrl) {
		logger.info("Verifying Get Param");
		RestResponse resResponse = new RestResponse();
		try {
			HttpResponse<JsonNode> response = Unirest.get(baseUrl + "/user/" + userId + paramStringUrl)
					.header("x-access-token", accessToken)
					.asJson();
			resResponse.setResponseCode(response.getStatus());
			JSONArray arr = response.getBody().getObject().getJSONObject("data").getJSONArray("items");
			resResponse.setListItem(arr);
			resResponse.setListSize(arr.length());
			logger.info("List Size: " + arr.length());			
		} catch(Exception e) {
			logger.info("Get Param Error: " + e.getMessage());
		}
		return new RestValidator(resResponse);
	}

}
