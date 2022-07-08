package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "states" /* ,catalog = "bol_start" */)
public class States implements Comparable<States> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",unique = true,nullable = false)
	private Long id;
	
	@Column(name="name",length = 255)
	private String name;
	
	@Column(name="country_id")
	private Long countryId;
	
	@Column(name="country_code",length = 255)
	private String countryCode;
	
	public States() {
		// TODO Auto-generated constructor stub
	}

	public States(Long id, String name, Long countryId, String countryCode) {
		super();
		this.id = id;
		this.name = name;
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
	
	
	@Override
	public int compareTo(States o) {
		return (getName().compareTo(o.getName()));
	}
	

}
