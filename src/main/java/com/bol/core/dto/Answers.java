package com.bol.core.dto;

import java.util.List;

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
@Table(name = "answers" /* ,catalog = "bol_start" */) 
public class Answers {
	
	private Long id;
	private Long questionId;
	private String answerDescription;
	private String image;
	private Double rating;
	private Double convincingRate;
	private Long noOfLikes;
	private String date;
	private String time;
	private Long userId;
	private String userName;
	private String title;
	private String profilePicture;
	private List<CommentsOnQA> commentsOnQaList;
	private String isLiked="false";

	
	
	public Answers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Answers(Long id, Long questionId, String answerDescription,Double convincingRate, String image, Double rating, Long noOfLikes,
			String date, String time) {
		super();
		this.id = id;
		this.questionId = questionId;
		this.answerDescription = answerDescription;
		this.image = image;
		this.rating = rating;
		this.noOfLikes = noOfLikes;
		this.date = date;
		this.convincingRate=convincingRate;
		this.time = time;
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

	@Column(name="question_id")
	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	@Column(name="answer_description",length = 5000)
	public String getAnswerDescription() {
		return answerDescription;
	}

	public void setAnswerDescription(String answerDescription) {
		this.answerDescription = answerDescription;
	}

	@Column(name="image",length = 255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="rating")
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
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
	@Column(name="convincing_rate")
	public Double getConvincingRate() {
		return convincingRate;
	}

	public void setConvincingRate(Double convincingRate) {
		this.convincingRate = convincingRate;
	}
	
	

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public List<CommentsOnQA> getCommentsOnQaList() {
		return commentsOnQaList;
	}

	public void setCommentsOnQaList(List<CommentsOnQA> commentsOnQaList) {
		this.commentsOnQaList = commentsOnQaList;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public String getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(String isLiked) {
		this.isLiked = isLiked;
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
