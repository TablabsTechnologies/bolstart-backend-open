package com.bol.core.dto;

public class MemberResponse {
	
	String userRole;
	String userName;
	Long userId;
	String userProfilePiture;
	String headLine;
	String company_name;
	Long memberId;
	Long company_id;
	
	
	
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getHeadLine() {
		return headLine;
	}
	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}
	
	public String getUserProfilePiture() {
		return userProfilePiture;
	}
	public void setUserProfilePiture(String userProfilePiture) {
		this.userProfilePiture = userProfilePiture;
	}
	
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	public MemberResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberResponse(String userRole, String userName, Long userId, String userProfilePiture, String headLine,
			String company_name, Long company_id) {
		super();
		this.userRole = userRole;
		this.userName = userName;
		this.userId = userId;
		this.userProfilePiture = userProfilePiture;
		this.headLine = headLine;
		this.company_name = company_name;
		this.company_id = company_id;
	}
	@Override
	public String toString() {
		return "MemberResponse [userRole=" + userRole + ", userName=" + userName + ", userId=" + userId
				+ ", userProfilePiture=" + userProfilePiture + ", headLine=" + headLine + ", company_name="
				+ company_name + ", company_id=" + company_id + "]";
	}

	
	
	
	

}
