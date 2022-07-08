package com.bol.core.utils;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CometChat {

	String appId="340656f35ffb3a9";    //"335250835b34dc7";
    String apiKey= "f387f4b91e8426c2ad1b27c0b1bb19eb8fd5dcb7";   //"f111c7c783adc819cb2145036507823f335bce02";
	
	public void addFriend(String uid,List<String> accepted) throws JSONException
	{
		   RestTemplate restTemplate = new RestTemplate();
		   HttpHeaders httpHeaders = new HttpHeaders();
		   httpHeaders.set("appId", appId);
		   httpHeaders.set("apiKey", apiKey);
		   httpHeaders.set("Content-Type", "application/json");
		   httpHeaders.set("Accept", "application/json");
		   
		   JSONObject json = new JSONObject();
		   json.put("accepted",accepted);
		   HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(),httpHeaders);
		   ResponseEntity<String> response = restTemplate.exchange("https://api-us.cometchat.io/v2.0/users/"+uid+"/friends",HttpMethod.POST,httpEntity,String.class);
		   System.out.println(response);
	}
	
	public void deleteFriend(String uid,List<String> accepted) throws JSONException
	{
		 RestTemplate restTemplate = new RestTemplate();
		   HttpHeaders httpHeaders = new HttpHeaders();
		   httpHeaders.set("appId", appId);
		   httpHeaders.set("apiKey", apiKey);
		   httpHeaders.set("Content-Type", "application/json");
		   httpHeaders.set("Accept", "application/json");
		   JSONObject json = new JSONObject();
		   json.put("friends",accepted);
		
		
		   ResponseEntity<String> response= restTemplate.exchange("https://api-us.cometchat.io/v2.0/users/"+uid+"/friends",HttpMethod.DELETE,new HttpEntity<>(json.toString(),httpHeaders),String.class);
		  System.out.println(response);
	}
	
	public boolean updateUser(String uid,String name,String avatar) throws JSONException
	{
		 RestTemplate restTemplate = new RestTemplate();
		   HttpHeaders httpHeaders = new HttpHeaders();
		   httpHeaders.set("appId", appId);
		   httpHeaders.set("apiKey", apiKey);
		   httpHeaders.set("Content-Type", "application/json");
		   httpHeaders.set("Accept", "application/json");
		   JSONObject json = new JSONObject();
		   json.put("name",name);
		   json.put("avatar",avatar);
		   try {
			ResponseEntity<String> response = restTemplate.exchange(
					"https://api-us.cometchat.io/v2.0/users/" + uid + "", HttpMethod.PUT,
					new HttpEntity<>(json.toString(), httpHeaders), String.class);
			System.out.println(response);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean checkGst(String gst)
	{
		 RestTemplate restTemplate = new RestTemplate();
		   HttpHeaders httpHeaders = new HttpHeaders();
		   httpHeaders.set("Content-Type", "application/json");
		   httpHeaders.set("Accept", "application/json");
		 try {
		  restTemplate.exchange("https://cleartax.in/f/compliance-report/"+gst+"/",HttpMethod.GET,new HttpEntity<>(httpHeaders),String.class);
		  return true;
		 }catch(Exception e)
		 {
			 return false;
		 }
	}
	
	public void getUser(String uid)
	{
		 RestTemplate restTemplate = new RestTemplate();
		   HttpHeaders httpHeaders = new HttpHeaders();
		   httpHeaders.set("appId", appId);
		   httpHeaders.set("apiKey", apiKey);
		   httpHeaders.set("Content-Type", "application/json");
		   httpHeaders.set("Accept", "application/json");
		   ResponseEntity<String> response= restTemplate.exchange("https://api-us.cometchat.io/v2.0/users/"+uid+"",HttpMethod.GET,new HttpEntity<>(httpHeaders),String.class);
		  System.out.println(response);
	}
	
}
