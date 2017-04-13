package com.hart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.testng.Assert;

public class RestValidator {
	
	private RestResponse response;

	RestValidator(RestResponse response) {
		this.response = response;
	}

	public RestValidator expectCode(int expectedCode) {
		Assert.assertEquals(response.getResponseCode(), expectedCode, "Incorrect Response Code");
		return this;
	}

	public RestValidator expectMessage(String message) {
		Assert.assertEquals(response.getResponseMessage(), message, "Incorrect Response Message");
		return this;
	}

	public RestValidator expectHeader(String headerName, String headerValue) {
		Assert.assertEquals(response.getHeader(headerName), headerValue, "Incorrect header - " + headerName);
		return this;
	}

	public RestValidator expectHeaders(HashMap<String, String> headers) {
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			Assert.assertEquals(response.getHeader(key), headers.get(key), "Incorrect header - " + key);
		}
		return this;
	}
	
	public RestValidator expectInBody(String content) {
		Assert.assertTrue(response.getResponseBody().contains(content), "Body doesnt contain string : " + content);
		return this;
	}
	
	public RestValidator expectListSize(int size) {
		Assert.assertTrue(response.getListSize() >= size, "List size is less than " + size);
		return this;
	}
	
	public RestValidator expectListItem(String itemName) {
		JSONArray arr = response.getListItem();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < arr.length(); i++) {
			result.add(arr.getJSONObject(i).getString("name"));
		}
			Assert.assertTrue(result.contains(itemName), "List item: " + itemName + "is missing");
		return this;
	}

	public RestValidator printBody(){
		System.out.println(response.getResponseBody());
		return this;
	}
	
	public RestResponse getResponse(){
		return response;
	}

}
