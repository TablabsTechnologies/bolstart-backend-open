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
@Table(name = "comments_on_qa" /* ,catalog = "bol_start" */) 
public class CommentsOnQA {
	
	private Long id;
	private Long userId;
	private Long answerId;
	private String description;
	private Long noOfLikes;
	private String date;
	private String time;
	private String userName;
	private String title;
	private String profilePicture;
	private String isLikeByUser="false";
	
	public CommentsOnQA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentsOnQA(Long id, Long userId,String date,String time, Long answerId, String description, Long noOfLikes) {
		super();
		this.id = id;
		this.userId = userId;
		this.answerId = answerId;
		this.description = description;
		this.noOfLikes = noOfLikes;
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

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="answer_id")
	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
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
	public String getIsLikeByUser() {
		return isLikeByUser;
	}

	public void setIsLikeByUser(String isLikeByUser) {
		this.isLikeByUser = isLikeByUser;
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
