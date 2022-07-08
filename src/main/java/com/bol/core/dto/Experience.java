package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "experience"/* ,catalog = "bol_start" */) 
public class Experience implements Comparable<Experience>{
	
	private Long id;
	private Long companyId;
	private String companyName;
	private String companyLogo;
	private String title;
	private String description;
	private String jobLocation;
	private String startYear;
	private String startMonth;
	private String endYear;
	private String endMonth;
	private boolean isWorking;
	private Long userId;
	
	
	public Experience(Long id, Long companyId, String title, String description, String jobLocation, String startYear,
			 String startMonth,String endYear, String endMonth,boolean isWorking, Long userId) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.title = title;
		this.description = description;
		this.jobLocation = jobLocation;
		this.startYear = startYear;
		this.startMonth=startMonth;
		this.endYear = endYear;
		this.endMonth=endMonth;
		this.isWorking=isWorking;
		this.userId = userId;
	}

	public Experience() {
		super();
		// TODO Auto-generated constructor stub
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
	
	@Column(name="company_id")
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="title",length = 255)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="description",length = 5000)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="job_location",length = 255)
	public String getJobLocation() {
		return jobLocation;
	}
	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}
	

	
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="start_year",length = 255)
	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	@Column(name="start_month",length = 255)
	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	@Column(name="end_year",length = 255)
	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	@Column(name="end_month",length = 255)
	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	@Column(name="is_working",length = 255)
	public boolean getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	@Column(name="company_name",length = 255)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name="company_logo",length = 255)
	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	@Override
	public int compareTo(Experience exp) {
		// TODO Auto-generated method stub
		int year1=Integer.parseInt(this.getStartYear());
		int year2=Integer.parseInt(exp.getStartYear());
		return year2-year1;
	}

	@Override
	public String toString() {
		return "Experience [id=" + id + ", companyId=" + companyId + ", companyName=" + companyName + ", companyLogo="
				+ companyLogo + ", title=" + title + ", description=" + description + ", jobLocation=" + jobLocation
				+ ", startYear=" + startYear + ", startMonth=" + startMonth + ", endYear=" + endYear + ", endMonth="
				+ endMonth + ", isWorking=" + isWorking + ", userId=" + userId + "]";
	}
	
	
	
	

}
