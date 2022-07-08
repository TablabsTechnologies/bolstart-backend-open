package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "comments_on_post"/* ,catalog = "bol_start" */) 
public class CommentsOnPost {
	
	private Long id;
	private Long postId;
	private String description;
	private Long noOfLikes;
	private Long userId;
	private String date;
	private String time;
	private String username;
	private String title;
	private String profilePicture;
	private String isLikedByUser="false";
	
	

	public CommentsOnPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentsOnPost(Long id, Long postId,String date,String time, String description, Long noOfLikes, Long userId) {
		super();
		this.id = id;
		this.postId = postId;
		this.description = description;
		this.noOfLikes = noOfLikes;
		this.userId = userId;
		this.date=date;
		this.time=time;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",unique = true,nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="post_id")
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	@Column(name="description",length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="no_of_likes")
	public Long getNoOfLikes() {
		return noOfLikes;
	}

	public void setNoOfLikes(Long noOfLikes) {
		this.noOfLikes = noOfLikes;
	}

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public String getIsLikedByUser() {
		return isLikedByUser;
	}

	public void setIsLikedByUser(String isLikedByUser) {
		this.isLikedByUser = isLikedByUser;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	
	
	

}
