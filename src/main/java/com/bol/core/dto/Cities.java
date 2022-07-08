package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cities" /* ,catalog = "bol_start" */)
public class Cities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",unique = true,nullable = false)
	private Long id;
	
	@Column(name="name",length = 255)
	private String name;
	
	@Column(name="state_id")
	private Long stateId;
	
	@Column(name="state_code",length = 255)
	private String stateCode;
	
	@Column(name="country_id")
	private Long countryId;
	
	@Column(name="country_code",length = 255)
	private String countryCode;
	
	public Cities() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Cities(Long id, String name, Long stateId, String stateCode, Long countryId, String countryCode) {
		super();
		this.id = id;
		this.name = name;
		this.stateId = stateId;
		this.stateCode = stateCode;
		this.countryId = countryId;
		this.countryCode = countryCode;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	
}
