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
@Table(name = "company"/* ,catalog = "bol_start" */ ) 
public class Company {
	
	private Long id;
	private String name;
	private String city;
	private String state;
	private String country;
	private String contactNumber;
	private String contactEmail;
	private String isVerified;
	private String websiteLink;
	private String logo;
	private Long user_id;
	private String description;
	private String industry;
	
	private String service;
	private String profile_url;
	private String stages;
	private String creationDate;
	private String isActive;
	
	
	private ArrayList<String> services;
	
	private String company_type;
	
	
	@Column(name="isActive")
	public String getIsActive() {
		return isActive;
	}



	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}



	@Column(name="creationDate")
	public String getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}



	@Column(name="company_type")
	public String getCompany_type() {
		return company_type;
	}



	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}



	@Transient
    public ArrayList<String> getServices() {
		return services;
	}



	public void setServices(ArrayList<String> services) {
		this.services = services;
	}


    @JsonIgnore
	@Column(name="service")
	public String getService() {
		return service;
	}



	public void setService(String service) {
		this.service = service;
	}



	public String getStages() {
		return stages;
	}



	public void setStages(String stages) {
		this.stages = stages;
	}



	public Long getUser_id() {
		return user_id;
	}



	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}


	@Column(name="description",length = 50000)
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getIndustry() {
		return industry;
	}



	public void setIndustry(String industry) {
		this.industry = industry;
	}



	public String getProfile_url() {
		return profile_url;
	}



	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}



	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Company(Long id, String name, String address, String city, String state, String country, String zipCode,
			String contactNumber, String contactEmail, String registrationType, String gst, String pan, String cin,
			String isVerified, String websiteLink, String logo) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.state = state;
		this.country = country;
		this.contactNumber = contactNumber;
		this.contactEmail = contactEmail;
		this.isVerified = isVerified;
		this.websiteLink = websiteLink;
		this.logo = logo;
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



	@Column(name="name",length = 255)
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	



	@Column(name="city",length = 255)
	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}


	@Column(name="state",length = 255)
	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	@Column(name="country",length = 255)
	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}







	@Column(name="contact_number",length = 255)
	public String getContactNumber() {
		return contactNumber;
	}



	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}



	@Column(name="contact_email",length = 255)
	public String getContactEmail() {
		return contactEmail;
	}



	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	
	@Column(name="is_verified",length = 255)
	public String getIsVerified() {
		return isVerified;
	}



	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}


	@Column(name="website_link",length = 255)
	public String getWebsiteLink() {
		return websiteLink;
	}



	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}


	@Column(name="logo",length = 1500)
	public String getLogo() {
		return logo;
	}



	public void setLogo(String logo) {
		this.logo = logo;
	}



	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", contactNumber=" + contactNumber + ", contactEmail=" + contactEmail + ", isVerified=" + isVerified
				+ ", websiteLink=" + websiteLink + ", logo=" + logo + ", user_id=" + user_id + ", description="
				+ description + ", industry=" + industry + ", service=" + service + ", profile_url=" + profile_url
				+ ", stages=" + stages + ", services=" + services + ", company_type=" + company_type + "]";
	}
	
	

}
