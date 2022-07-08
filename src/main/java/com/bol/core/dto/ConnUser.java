package com.bol.core.dto;

public class ConnUser {

	private Long userId;
	private String firstName;
	private String lastName;
	private String headline;
	private String bio;
	private String profilePic;
	
	private String isFollowed;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
	

	
	public String getIsFollowed() {
		return isFollowed;
	}

	public void setIsFollowed(String isFollowed) {
		this.isFollowed = isFollowed;
	}

	public ConnUser() {
		// TODO Auto-generated constructor stub
	}

	public ConnUser(Long userId, String firstName, String lastName, String headline, String bio, String profilePic,String isFollowed) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.headline = headline;
		this.bio = bio;
		this.profilePic = profilePic;
		this.isFollowed=isFollowed;

	}

	@Override
	public String toString() {
		return "ConnUser [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", headline="
				+ headline + ", bio=" + bio + ", profilePic=" + profilePic + ", isFollowed=" + isFollowed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnUser other = (ConnUser) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	

}
