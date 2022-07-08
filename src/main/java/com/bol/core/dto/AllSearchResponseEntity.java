package com.bol.core.dto;

public class AllSearchResponseEntity {
	
	Long id;
	String profile;
	String username;
	String headline;
	Long companyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public AllSearchResponseEntity(Long id, String profile, String username, String headline, Long companyId) {
		super();
		this.id = id;
		this.profile = profile;
		this.username = username;
		this.headline = headline;
		this.companyId = companyId;
	}
	public AllSearchResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
