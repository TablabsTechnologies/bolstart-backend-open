package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_employee")
public class CompanyEmployee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="user_id")
	private Long userId;
	@Column(name="company_id")
	private Long companyId;
	@Column(name="start_year")
	private String startYear;
	@Column(name="start_month")
	private String startMonth;
	@Column(name="is_working")
	private boolean isWorking;
	@Column(name="title")
	private String title;
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
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public boolean isWorking() {
		return isWorking;
	}
	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public CompanyEmployee(Long id, Long userId, Long companyId, String startYear, String startMonth, boolean isWorking,
			String title) {
		super();
		this.id = id;
		this.userId = userId;
		this.companyId = companyId;
		this.startYear = startYear;
		this.startMonth = startMonth;
		this.isWorking = isWorking;
		this.title = title;
	}
	public CompanyEmployee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
