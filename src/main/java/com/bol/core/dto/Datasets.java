package com.bol.core.dto;

import java.util.ArrayList;

public class Datasets {

	private String label;
	private ArrayList<String> data;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public ArrayList<String> getData() {
		return data;
	}
	public void setData(ArrayList<String> data) {
		this.data = data;
	}
	public Datasets(String label, ArrayList<String> data) {
		super();
		this.label = label;
		this.data = data;
	}
	public Datasets() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
