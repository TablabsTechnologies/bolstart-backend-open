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
@Table(name = "like_dislike_on_publish" /* ,catalog = "bol_start" */ ) 
public class LikeDislikeOnPublish {
	
	private Long id;
	private Long userId;
	private Long publishId;
	private String isLiked="false";
	private String isDisliked="false";
	private String date;
	private String time;
	private Long noOflikes;
	private Long noOfDislikes;
	
	
	public LikeDislikeOnPublish() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LikeDislikeOnPublish(Long id, Long userId, Long publishId, String isLiked, String isDisliked) {
		super();
		this.id = id;
		this.userId = userId;
		this.publishId = publishId;
		this.isLiked = isLiked;
		this.isDisliked = isDisliked;
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

	@Column(name="publish_id")
	public Long getPublishId() {
		return publishId;
	}

	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}

	@Column(name="is_liked",length = 255)
	public String getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(String isLiked) {
		this.isLiked = isLiked;
	}

	@Column(name="is_disliked",length = 255)
	public String getIsDisliked() {
		return isDisliked;
	}

	public void setIsDisliked(String isDisliked) {
		this.isDisliked = isDisliked;
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
	public Long getNoOflikes() {
		return noOflikes;
	}

	public void setNoOflikes(Long noOflikes) {
		this.noOflikes = noOflikes;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public Long getNoOfDislikes() {
		return noOfDislikes;
	}

	public void setNoOfDislikes(Long noOfDislikes) {
		this.noOfDislikes = noOfDislikes;
	}
	
	

	
}

