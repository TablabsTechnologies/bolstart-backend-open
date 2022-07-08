package com.bol.core.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "publish" /* ,catalog = "bol_start" */ ) 
public class Publish implements Comparable<Publish>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="userId")
	private Long userId;
	
	@Column(name="description",length = 5000)
	private String description;
	
	
	@Column(name="image")
	private String image;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="noOfLikes")
	private Long noOfLikes;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="noOfDislikes")
	private Long noOfDislikes;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="date")
	private String date;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="time")
	private String time;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="userName")
	private String userName;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="title")
	private String title;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="profilePicture")
	private String profilePicture;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	private List<CommentOnPublish>  commentsOnPublishtList;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="isLikedByUser")
	private String isLikedByUser="false";
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="isDislikedByUser")
	private String isDislikedByUser="false";
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="emailId")
	private String emailId;
	
	@Column(name="group_id")
	private Long groupId;
	
	@Column(name="company_id")
	private Long companyId;
	
	@Transient
	ArrayList<Chart_Response> chartUrls;
	
	@JsonIgnore
	@Column(name="chart_url",length = 5000)
	String chart_url;
	
	
	
	public ArrayList<Chart_Response> getChartUrls() {
		return chartUrls;
	}
	public void setChartUrls(ArrayList<Chart_Response> chartUrls) {
		this.chartUrls = chartUrls;
	}
	public String getChart_url() {
		return chart_url;
	}
	public void setChart_url(String chart_url) {
		this.chart_url = chart_url;
	}
	public List<CommentOnPublish> getCommentsOnPublishtList() {
		return commentsOnPublishtList;
	}
	public void setCommentsOnPublishtList(List<CommentOnPublish> commentsOnPublishtList) {
		this.commentsOnPublishtList = commentsOnPublishtList;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getNoOfLikes() {
		return noOfLikes;
	}
	public void setNoOfLikes(Long noOfLikes) {
		this.noOfLikes = noOfLikes;
	}
	public Long getNoOfDislikes() {
		return noOfDislikes;
	}
	public void setNoOfDislikes(Long noOfDislikes) {
		this.noOfDislikes = noOfDislikes;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
//	public List<CommentsOnPost> getCommentsOnPostList() {
//		return commentsOnPostList;
//	}
//	public void setCommentsOnPostList(List<CommentsOnPost> commentsOnPostList) {
//		this.commentsOnPostList = commentsOnPostList;
//	}
	public String getIsLikedByUser() {
		return isLikedByUser;
	}
	public void setIsLikedByUser(String isLikedByUser) {
		this.isLikedByUser = isLikedByUser;
	}
	public String getIsDislikedByUser() {
		return isDislikedByUser;
	}
	public void setIsDislikedByUser(String isDislikedByUser) {
		this.isDislikedByUser = isDislikedByUser;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Publish(Long userId, String description, String image, Long noOfLikes, Long noOfDislikes,
			String date, String time, String userName, String title, String profilePicture,
			 String isLikedByUser, String isDislikedByUser, String emailId) {
		super();
//		this.id = id;
		this.userId = userId;
		this.description = description;
		this.image = image;
		this.noOfLikes = noOfLikes;
		this.noOfDislikes = noOfDislikes;
		this.date = date;
		this.time = time;
		this.userName = userName;
		this.title = title;
		this.profilePicture = profilePicture;
//		this.commentsOnPostList = commentsOnPostList;
		this.isLikedByUser = isLikedByUser;
		this.isDislikedByUser = isDislikedByUser;
		this.emailId = emailId;
	}
	public Publish() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int compareTo(Publish pub) {
		return (this.getDate()+""+this.getTime()).compareTo((pub.getDate()+pub.getTime()));
		
	}
	@Override
	public String toString() {
		return "Publish [id=" + id + ", userId=" + userId + ", description=" + description + ", image=" + image
				+ ", noOfLikes=" + noOfLikes + ", noOfDislikes=" + noOfDislikes + ", date=" + date + ", time=" + time
				+ ", userName=" + userName + ", title=" + title + ", profilePicture=" + profilePicture
				+ ", commentsOnPublishtList=" + commentsOnPublishtList + ", isLikedByUser=" + isLikedByUser
				+ ", isDislikedByUser=" + isDislikedByUser + ", emailId=" + emailId + ", groupId=" + groupId
				+ ", companyId=" + companyId + ", chartUrls=" + chartUrls + ", chart_url=" + chart_url + "]";
	}
	
	
	
	

}
