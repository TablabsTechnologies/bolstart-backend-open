package com.bol.core.dto;

public class Chart_Response {

	String chartName;
	String chartUrl;
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	public String getChartUrl() {
		return chartUrl;
	}
	public void setChartUrl(String chartUrl) {
		this.chartUrl = chartUrl;
	}
	public Chart_Response(String chartName, String chartUrl) {
		super();
		this.chartName = chartName;
		this.chartUrl = chartUrl;
	}
	public Chart_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
