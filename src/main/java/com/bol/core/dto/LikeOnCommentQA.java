package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "like_on_comment_qa" /* ,catalog = "bol_start" */ ) 
public class LikeOnCommentQA {

	private Long id;
	private Long commentQAId;
	private Long userId;
	private String isLiked="false";
	private String date;
	private String time;
	
	public LikeOnCommentQA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LikeOnCommentQA(Long id, Long commentQAId, Long userId, String isLiked) {
		super();
		this.id = id;
		this.commentQAId = commentQAId;
		this.userId = userId;
		this.isLiked = isLiked;
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

	@Column(name="comment_qa_id")
	public Long getCommentQAId() {
		return commentQAId;
	}

	public void setCommentQAId(Long commentQAId) {
		this.commentQAId = commentQAId;
	}

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	@Column(name="is_liked",length = 255)
	public String getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(String isLiked) {
		this.isLiked = isLiked;
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
	
	
	
	
}
