package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	
@Entity
@Table(name="comment_on_publish")
public class CommentOnPublish {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "publish_Id")
	private Long publishId;
	@Column(name = "description")
	private String description;
	@Column(name = "noOfLikes")
	private Long noOfLikes;
	@Column(name = "userId")
	private Long userId;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "profilePicture")
	private String profilePicture;
	
	@Column(name = "isLikedByUser")
	private String isLikedByUser="false";

	public CommentOnPublish() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public CommentOnPublish(Long id, Long postId, String description, Long noOfLikes, Long userId, String date,
			String time, String username, String title, String profilePicture, String isLikedByUser) {
		super();
		this.id = id;
		this.publishId = postId;
		this.description = description;
		this.noOfLikes = noOfLikes;
		this.userId = userId;
		this.date = date;
		this.time = time;
		this.username = username;
		this.title = title;
		this.profilePicture = profilePicture;
		this.isLikedByUser = isLikedByUser;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getPublishId() {
		return publishId;
	}




	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public Long getNoOfLikes() {
		return noOfLikes;
	}




	public void setNoOfLikes(Long noOfLikes) {
		this.noOfLikes = noOfLikes;
	}




	public Long getUserId() {
		return userId;
	}




	public void setUserId(Long userId) {
		this.userId = userId;
	}




	public String getDate() {
		return date;
	}




	public void setDate(String date) {
		this.date = date;
	}




	public String getTime() {
		return time;
	}




	public void setTime(String time) {
		this.time = time;
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




	public String getIsLikedByUser() {
		return isLikedByUser;
	}




	public void setIsLikedByUser(String isLikedByUser) {
		this.isLikedByUser = isLikedByUser;
	}




	

}
