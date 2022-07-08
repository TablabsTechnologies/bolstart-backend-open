package com.bol.core.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "post" /* ,catalog = "bol_start" */ ) 
public class Post {
	
	private Long id;
	private Long userId;
	private String description;
	private String image;
	private Long noOfLikes;
	private Long noOfDislikes;
	private String date;
	private String time;
	private String userName;
	private String title;
	private String profilePicture;
	private List<CommentsOnPost>  commentsOnPostList;
	private String isLikedByUser="false";
	private String isDislikedByUser="false";
	
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(Long id, Long userId,String date,String time, String description, String image, Long noOfLikes, Long noOfDislikes) {
		super();
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.image = image;
		this.noOfLikes = noOfLikes;
		this.noOfDislikes = noOfDislikes;
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

	@Column(name="description",length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="image",length = 255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
	@Column(name="no_of_dislikes")
	public Long getNoOfDislikes() {
		return noOfDislikes;
	}

	public void setNoOfDislikes(Long noOfDislikes) {
		this.noOfDislikes = noOfDislikes;
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
	public List<CommentsOnPost> getCommentsOnPostList() {
		return commentsOnPostList;
	}

	public void setCommentsOnPostList(List<CommentsOnPost> commentsOnPostList) {
		this.commentsOnPostList = commentsOnPostList;
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
	public String getIsDislikedByUser() {
		return isDislikedByUser;
	}

	public void setIsDislikedByUser(String isDislikedByUser) {
		this.isDislikedByUser = isDislikedByUser;
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
