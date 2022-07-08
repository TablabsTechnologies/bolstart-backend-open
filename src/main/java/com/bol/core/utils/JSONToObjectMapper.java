package com.bol.core.utils;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.bol.core.dto.SMSObjectResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONToObjectMapper {
	
	public SMSObjectResponse jsonToObjectMapper(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();//"{'name' : 'mkyong'}"
		SMSObjectResponse obj = mapper.readValue(json, SMSObjectResponse.class);
		return obj;
	}
	
	public String objectToJson(SMSObjectResponse smsObjectResponse) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		 String jsonString = mapper.writeValueAsString(smsObjectResponse);
		 return jsonString;
	}
	
	
	
}
