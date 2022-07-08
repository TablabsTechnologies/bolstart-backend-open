package com.bol.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.JobPost;
import com.bol.core.dto.JobTitles;
import com.bol.core.repository.JobTitleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@Api(value = "Job Title",description = "used to give title to jobs")
@RequestMapping("/jobtitle")
public class JobTitlesController {
	
	@Autowired
	JobTitleRepository jobTitleRepository;
	
	@GetMapping("/job/getAll")
	public ResponseEntity<String> getAllJobTitles() throws JsonProcessingException{
			
		try {
			 List<JobTitles> jobTitles= (List<JobTitles>) jobTitleRepository.findAll();

		
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(jobTitles);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);

		}catch (Exception e) {
			// TODO: handle exception
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("No Data Found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	
	@PostMapping("/save")
	public ResponseEntity<String> saveJobPost(@RequestBody JobTitles jobTitles) throws JsonProcessingException {
		
		try {
			JobTitles jobTitles1	= jobTitleRepository.save(jobTitles);

			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(jobTitles1);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("No Data Found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
		
		
	}
 
	@GetMapping("/get/byJobTitle")
	public ResponseEntity<String> getByJobTitle(@RequestParam String title) throws JsonProcessingException {

		try {

			JobTitles jobTitlesEntity = jobTitleRepository.findByTitle(title);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobTitlesEntity);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("No Data Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}
}
