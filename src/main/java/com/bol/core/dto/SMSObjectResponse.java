package com.bol.core.dto;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Service
@JsonIgnoreProperties
public class SMSObjectResponse {

	/*private String Status;
	private String Details;
	
	public SMSObjectResponse() {
		super();
	}
	
	public SMSObjectResponse(String Status, String Details) {
		super();
		this.Status = Status;
		this.Details = Details;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String Status) {
		this.Status = Status;
	}
	public String getDetails() {
		return Details;
	}
	public void setDetails(String Details) {
		this.Details = Details;
	}
	*/
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Details")
	private String details;
	
	
	
	
	
/*	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "SMSObjectResponse [status=" + status + ", details=" + details + "]";
	}
	
	
	
	
	
}
