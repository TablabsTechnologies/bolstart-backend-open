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
@Table(name = "chart" /* ,catalog = "bol_start" */) 
public class Chart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@JsonIgnore
	@Column(name="type")
	private String type;
	
	@JsonIgnore
	@Column(name="labels")
	private String str_lable;
	
//	@JsonIgnore
//	@Transient
//	private ArrayList<String> labels;
	
	@JsonIgnore
	@Column(name="label")
	private String label;
    
	@JsonIgnore
	@Column(name="data")
	private String data;
	
	@Transient
	private ArrayList<Datasets> datasets;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="company_id")
	private Long companyId;
	
	@JsonIgnore
	@Column(name="chart_date")
	private String chart_date;

	@JsonIgnore
	@Column(name="week")
	private String year_in_week;
	
	public String getYear_in_week() {
		return year_in_week;
	}

	public void setYear_in_week(String year_in_week) {
		this.year_in_week = year_in_week;
	}

	public String getChart_date() {
		return chart_date;
	}

	public void setChart_date(String chart_date) {
		this.chart_date = chart_date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStr_lable() {
		return str_lable;
	}

	public void setStr_lable(String str_lable) {
		this.str_lable = str_lable;
	}

//	public ArrayList<String> getLabels() {
//		return labels;
//	}
//
//	public void setLabels(ArrayList<String> labels) {
//		this.labels = labels;
//	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ArrayList<Datasets> getDatasets() {
		return datasets;
	}

	public void setDatasets(ArrayList<Datasets> datasets) {
		this.datasets = datasets;
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

	
	
	public Chart(Long id, String type, String str_lable, ArrayList<String> labels, String label, String data,
			ArrayList<Datasets> datasets, Long userId, Long companyId) {
		super();
		this.id = id;
		this.type = type;
		this.str_lable = str_lable;
//		this.labels = labels;
		this.label = label;
		this.data = data;
		this.datasets = datasets;
		this.userId = userId;
		this.companyId = companyId;
	}

	public Chart() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
}
