package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "members"/* ,catalog = "bol_start" */ ) 
public class Members {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",nullable = false,unique = true)
	private Long id;
	@Column(name="user_role")
	private String userRole;
	@Column(name="user_id")
	private Long userId;
	@Column(name="company_id")
	private Long companyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
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
	public Members(Long id, String userRole, Long userId, Long companyId) {
		super();
		this.id = id;
		this.userRole = userRole;
		this.userId = userId;
		this.companyId = companyId;
	}
	public Members() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Members [id=" + id + ", userRole=" + userRole + ", userId=" + userId + ", companyId=" + companyId + "]";
	}
	
	
	

}
