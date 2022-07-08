package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries" /* ,catalog = "bol_start" */ )
public class Countries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",unique = true,nullable = false)
	private Long id;
	
	@Column(name="name",length = 255)
	private String name;
	
	@Column(name="capital",length = 255)
	private String capital;
	
	@Column(name="phonecode",length = 255)
	private String phonecode;
	
	@Column(name="currency",length = 255)
	private String currency;
	
	public Countries() {
		// TODO Auto-generated constructor stub
	}

	public Countries(Long id, String name, String capital, String phonecode, String currency) {
		super();
		this.id = id;
		this.name = name;
		this.capital = capital;
		this.phonecode = phonecode;
		this.currency = currency;
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

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	

}
