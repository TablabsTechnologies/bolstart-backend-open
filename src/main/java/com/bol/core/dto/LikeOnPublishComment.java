package com.bol.core.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Like_On_Publish_Comment")
public class LikeOnPublishComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="user_id")
	private Long userId;

	@Column(name="publish_comment_id")
	private Long publishCommentId;

	@Column(name="is_liked")
	private String isLiked="false";
	
	@Column(name="date")
	private String date;

	@Column(name="time")
	private String time;
	
	public LikeOnPublishComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LikeOnPublishComment(Long id, Long userId, Long publishCommentId, String isLiked, String date, String time) {
		super();
		this.id = id;
		this.userId = userId;
		this.publishCommentId = publishCommentId;
		this.isLiked = isLiked;
		this.date = date;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPublishCommentId() {
		return publishCommentId;
	}

	public void setPublishCommentId(Long publishCommentId) {
		this.publishCommentId = publishCommentId;
	}

	public String getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(String isLiked) {
		this.isLiked = isLiked;
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

	
}
