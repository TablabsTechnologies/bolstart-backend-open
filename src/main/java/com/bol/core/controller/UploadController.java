package com.bol.core.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.JobPost;
import com.bol.core.dto.Users;
import com.bol.core.repository.JobPostRepository;
import com.bol.core.utils.AmazonClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@Api(value = "user controller",description = "used to register ,update and fetch user details")
@RequestMapping("/jobpost")
public class UploadController {
	
	private final Logger log = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	JobPostRepository jobPostRepository;
	
	@Autowired
	AmazonClient amazonClient;
	
	 @PostMapping("/uploadImage")
	 public ResponseEntity<String> saveTimline(@RequestParam(value = "timlineImage", required=false) MultipartFile file,@RequestParam("id") Long id) throws IOException{
		 
	
		 JobPost jobPost = jobPostRepository.findById(id);
		 if(jobPost==null)
		 {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("user not available");
			 jsonobjectFormat.setSuccess(false);
	
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
		 try {
			 amazonClient=new AmazonClient();
		  if(file!=null){
			  
			  jobPost.setImage(amazonClient.uploadFile(file));
		
		  }
		  JobPost post =   jobPostRepository.save(jobPost);	
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("timline image  saved successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(post);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	}catch(Exception e)
		{
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to save Timline Image");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			}
	 }

}
