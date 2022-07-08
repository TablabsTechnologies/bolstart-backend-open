package com.bol.core.dto;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "addressbok" /* ,catalog = "bol_start" */) 
public class AddressBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="company_id")
	private Long companyId;
	
	@Transient
	private ArrayList<AddressBookMemberEntity> emailId;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public ArrayList<AddressBookMemberEntity> getEmailId() {
		return emailId;
	}

	public void setEmailId(ArrayList<AddressBookMemberEntity> emailId) {
		this.emailId = emailId;
	}

	public AddressBook(Long id, String groupName, Long companyId, ArrayList<AddressBookMemberEntity> emailId) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.companyId = companyId;
		this.emailId = emailId;
	}

	public AddressBook() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AddressBook [id=" + id + ", groupName=" + groupName + ", companyId=" + companyId + ", emailId="
				+ emailId + "]";
	}
	
	
	
	
	
	
	

}

