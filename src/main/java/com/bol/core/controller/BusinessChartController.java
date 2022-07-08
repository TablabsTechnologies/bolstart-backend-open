package com.bol.core.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.Chart;
import com.bol.core.dto.Chart_Response;
import com.bol.core.repository.ChartRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Chart controller",description = "Make Business Chart")
@RequestMapping("/api")
public class BusinessChartController {
	
	@Autowired
	ChartRepository chartRepository;
	
	@PostMapping("/chart/create")
	ResponseEntity<String> createChart(@RequestBody Chart chartEntity) throws JsonProcessingException{
		
//		try {
//			
			Calendar calender=Calendar.getInstance();
//			int currentWeek=calender.getWeeksInWeekYear();
			int currentYear=calender.getWeekYear();
			
			Date currentDate=new Date();
			
			int currentWeek=calender.get(Calendar.WEEK_OF_YEAR);
			System.out.println("");
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			
			String strCurrentDate=dateFormat.format(currentDate);
			
			String[] weeks=new String[10];//= {"week1","week2","week3","week4","week5","week6","week7","week8","week9","week10"};
			
			int increase=9;
			int real=0;
			while(increase>=0) {
				weeks[real]=
						"week_"+
				(calender.get(Calendar.WEEK_OF_YEAR)-increase)+
				"-"+
				currentYear;
				System.out.println(" calender.get(Calendar.WEEK_OF_YEAR)-increase "+(calender.get(Calendar.WEEK_OF_YEAR)-increase));
				increase--;
				real++;
			}
			
//			for(int j=10;j>=1;j--) {
//				weeks[j]="week_"+(currentWeek-j)+"-"+currentYear;
//			}
			String weeksString="";
			String comma="'";
			for(int j=0;j<10;j++) {
				weeksString+="'"+weeks[j]+"'"+",";
			}
		
			String weeksFinalString=weeksString.substring(0,weeksString.length()-1);
			
			System.out.println(" weeksFinalString "+weeksFinalString);
			
			ArrayList<Chart_Response> chartResponseList=new ArrayList<Chart_Response>();
			if(chartEntity!=null) {				
			
				if(chartEntity.getDatasets()!=null && chartEntity.getDatasets().get(0).getData()!=null && chartEntity.getDatasets().get(0).getData().size()!=0) {
					Chart_Response response=new Chart_Response();
					String dataValuesChartString="";
					for(String dataValues:chartEntity.getDatasets().get(0).getData()) {
						dataValuesChartString=dataValuesChartString+dataValues+",";
					}
					System.out.println(" dataValuesChartString "+dataValuesChartString);

					
					String chartDataString="";
					if(dataValuesChartString.length()>1) {
						chartDataString=dataValuesChartString.substring(0, dataValuesChartString.length()-1);
					}
					
					Chart chartObject=new Chart();
					
					chartObject.setType("bar");
					chartObject.setLabel(chartEntity.getDatasets().get(0).getLabel());
					chartObject.setData(chartDataString);
					chartObject.setUserId(chartEntity.getUserId());
					chartObject.setCompanyId(chartEntity.getCompanyId());
					chartObject.setYear_in_week("week_"+currentWeek+"-"+currentYear);
					chartObject.setChart_date(strCurrentDate);
					
					List<Chart> chartObj1=chartRepository.findByCompanyIdAndLabelAndWeekMultipleValue(chartEntity.getCompanyId(),chartEntity.getDatasets().get(0).getLabel(),"week_"+currentWeek+"-"+currentYear);
					
					
					
					if(chartObj1==null) {
					chartRepository.save(chartObject);
					}else if(chartObj1!=null) {
						
						for(Chart chartValue:chartObj1) {
							
							chartRepository.delete(chartValue);
							
						}
						chartRepository.save(chartObject);
					}
					
					Calendar cal = Calendar.getInstance();
					
					System.out.println(" calendar "+cal.get(Calendar.YEAR));
					
					System.out.println(" chartDataString "+chartDataString);
					
					 ArrayList<Chart> charOlderDatatList=null;
//					if(chartDataString.length()>1) {
			           charOlderDatatList=chartRepository.findByCompanyIdAndLabel(chartEntity.getCompanyId(),chartEntity.getDatasets().get(0).getLabel());
//					}
					System.out.println(" charOlderDatatList "+charOlderDatatList.size());
					String chartDataStringNew="";
					
                    for(int chartWeek=0;chartWeek<weeks.length;chartWeek++) {              	
                    	Chart chartObj=chartRepository.findByCompanyIdAndLabelAndWeek(chartEntity.getCompanyId(),chartEntity.getDatasets().get(0).getLabel(),weeks[chartWeek]);
                    	
                    	if(chartObj!=null && chartObj.getData()!=null && chartObj.getYear_in_week()!=null) {
                    		chartDataStringNew=chartDataStringNew+chartObj.getData()+",";
                    	}else {
                    		chartDataStringNew=chartDataStringNew+0+",";
                    	}
                    	
					}
					
					System.out.println(" chartDataStringNew "+chartDataStringNew);
					
					String chartValueString="";
					if(chartDataStringNew.length()>1) {
						
						chartValueString=chartDataStringNew.substring(0, chartDataStringNew.length()-1);
						
					}
					String colorName="";
					switch(chartEntity.getDatasets().get(0).getLabel()) {
					
					case  "product launch": colorName="Yellow";
					                        break;
					                        
					case  "customers registered": colorName="Blue";
                                                break;                        
					
					case  "Weekly Revenue": colorName="Green";
                                            break;   
                                            
					case  "Weekly expenses": colorName="Red";
                                         break;                          
                    default: colorName="";
                            
					}
					
					String urlLinkforChart="https://quickchart.io/chart?c={type:'bar',data:{labels:["+weeksFinalString+"],datasets:[{label:"+"'"+chartEntity.getDatasets().get(0).getLabel()+"'"+",data:["+chartValueString+"],backgroundColor:"+"'"+colorName+"'"+"}]}}";
					response.setChartName(chartEntity.getDatasets().get(0).getLabel());
					response.setChartUrl(urlLinkforChart);
					
					chartResponseList.add(response);
					
					
				}else {
					
					 
					
				}
				
			}
		
			
			if(chartResponseList!=null && chartResponseList.size()!=0) {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Chart URL Generated");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(chartResponseList);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Unable to Generate Chart URL");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
//		} catch (Exception e) {
//			// TODO: handle exception
//			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 jsonobjectFormat.setMessage("Unable to Generate Chart URL");
//			 jsonobjectFormat.setSuccess(false);
//			 jsonobjectFormat.setData("");
//	         ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//		}
		
	}
	
	@GetMapping("/getWeek")
	public String weekTest(String strDate) throws ParseException {
		
		SimpleDateFormat fmt=new SimpleDateFormat("dd/MM/yyyy");
		
		
		
		Date date=fmt.parse(strDate);
		
		Calendar cal=Calendar.getInstance();
		
		Date date123=new Date();
		
		cal.setTime(date);
		
		return "Current Week :- "+cal.get(Calendar.WEEK_OF_YEAR)+" Current Year :- "+cal.get(Calendar.DAY_OF_YEAR)+" Current/Todays Date :- "+fmt.format(date);
	}

}
