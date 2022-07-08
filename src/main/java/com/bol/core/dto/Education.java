package com.bol.core.dto;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "education" /* ,catalog = "bol_start" */ ) 
public class Education implements Comparable<Education>{
	
	private Long id;
	private Long institutionId;
	private String institutionName;
	private Long userId;
	private String degree;
	private String grade;
	private String stream;
	private String activities;
	private String societies;
	private String startDate;
	private String endDate;
	private String institutionLogo;
	
	public Education() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Education(Long id, Long institutionId,String institutionName, Long userId, String degree, String grade, String stream,
			String activities, String societies, String startDate, String endDate,String institutionLogo) {
		super();
		this.id = id;
		this.institutionId = institutionId;
		this.institutionName=institutionName;
		this.userId = userId;
		this.degree = degree;
		this.grade = grade;
		this.stream = stream;
		this.activities = activities;
		this.societies = societies;
		this.startDate = startDate;
		this.endDate = endDate;
		this.institutionLogo=institutionLogo;
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

	@Column(name="institution_id")
	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="degree",length = 255)
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name="grade",length = 255)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name="stream",length = 255)
	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	@Column(name="activities",length = 255)
	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}

	@Column(name="societies",length = 255)
	public String getSocieties() {
		return societies;
	}

	public void setSocieties(String societies) {
		this.societies = societies;
	}

	@Column(name="start_date")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Column(name="end_date")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Column(name="institution_name",length = 255)
	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	@Column(name="institution_logo",length = 255)
	public String getInstitutionLogo() {
		return institutionLogo;
	}

	public void setInstitutionLogo(String institutionLogo) {
		this.institutionLogo = institutionLogo;
	}
	
	
	
	
	@Override
	public int compareTo(Education edu) {
		// TODO Auto-generated method stub
		int startdate1=Integer.parseInt(this.startDate);
		int startdate2=Integer.parseInt(edu.startDate);
		return startdate2-startdate1;
	}
	
	

}
