package com.bol.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accomplishment"/* ,catalog = "bol_start" */ ) 
public class Accomplishment {
	
	private Long id;
	private String type;
	private String name;
	private String fromDate;
	private String tillDate;
	private String certificationInstitute;
	private String description;
	private Long userId;
	
	public Accomplishment() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public Accomplishment(Long id, String type, String name, String fromDate, String tillDate,
			String certificationInstitute, String description, Long userId) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.fromDate = fromDate;
		this.tillDate = tillDate;
		this.certificationInstitute = certificationInstitute;
		this.description = description;
		this.userId = userId;
	}




	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="type",length = 255)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="name",length = 255)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Column(name="from_date")
	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	@Column(name="till_date")
	public String getTillDate() {
		return tillDate;
	}

	public void setTillDate(String tillDate) {
		this.tillDate = tillDate;
	}

	@Column(name="certification_institute",length = 255)
	public String getCertificationInstitute() {
		return certificationInstitute;
	}

	public void setCertificationInstitute(String certificationInstitute) {
		this.certificationInstitute = certificationInstitute;
	}

	@Column(name="description",length = 5000)
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
	
	

}
