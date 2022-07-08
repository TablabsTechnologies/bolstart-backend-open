package com.bol.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skills"/* ,catalog = "bol_start" */) 
public class Skills {
	
	private Long id;
	private String typeOfSkill;
	private String skillName;

	private String skillTime;
	private Long userId;
	
	public Skills() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Skills(Long id, String typeOfSkill, String skillName, String skillTime, Long userId) {
		super();
		this.id = id;
		this.typeOfSkill = typeOfSkill;
		this.skillName = skillName;
	
		this.skillTime = skillTime;
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

	

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name="type_of_skill",length = 255)
	public String getTypeOfSkill() {
		return typeOfSkill;
	}

	
	public void setTypeOfSkill(String typeOfSkill) {
		this.typeOfSkill = typeOfSkill;
	}

	@Column(name="skill_name",length = 255)
	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}


	@Column(name="skill_time")
	public String getSkillTime() {
		return skillTime;
	}

	public void setSkillTime(String skillTime) {
		this.skillTime = skillTime;
	}


	
	
}
