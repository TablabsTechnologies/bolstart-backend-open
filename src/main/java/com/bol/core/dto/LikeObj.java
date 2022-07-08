package com.bol.core.dto;

public class LikeObj {

	
	private Long userId;
	private String username;
	private String title;
	private String profilePicture;
	
	
	
	public LikeObj(Long userId, String username, String title,String profilePicture) {
		super();
		
		this.userId = userId;
		this.username = username;
		this.title = title;
		this.profilePicture=profilePicture;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	

}
