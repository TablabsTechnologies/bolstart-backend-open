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
@Table(name = "questions" /* ,catalog = "bol_start" */) 
public class Questions {
	
	private Long id;
	private Long userId;
	private String category;
	private String content;
	private String description;
	private String image;

	private String date;
	private String time;
	private String userName;
	private String title;
	private String profilePicture;
	private List<Answers> answerList;
	
	public Questions() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Questions(Long id, Long userId, String category, String content, String description, String image,
			String date, String time) {
		super();
		this.id = id;
		this.userId = userId;
		this.category = category;
		this.content = content;
		this.description = description;
		this.image = image;
		this.date = date;
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

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="category",length = 255)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name="content",length = 5000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	public List<Answers> getAnswerList() {
		return answerList;
	}



	public void setAnswerList(List<Answers> answerList) {
		this.answerList = answerList;
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
