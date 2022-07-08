package com.bol.core.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bol.core.repository.ConnectionsRepository;
import org.springframework.web.bind.annotation.RestController;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.Company;
import com.bol.core.dto.ConnUser;
import com.bol.core.dto.Connections;
import com.bol.core.dto.Users;
import com.bol.core.repository.CompanyRepository;
import com.bol.core.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Chart controller",description = "Investor Portal")
@RequestMapping("/api")
public class InvestorPortalController {
	
	@Autowired
	UsersRepository usersRepository;

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	ConnectionsRepository connectionRepository;
	
	@GetMapping("/investorportal/getcompany/byconnection")
	ResponseEntity<String> getAllCompanyByConnection(@RequestParam("userid") Long userid) throws JsonProcessingException{
		
		try {
		
		List<Connections> conn=connectionRepository.findByUserId(userid);
		System.out.println(conn);
		List<Company> companyList=new ArrayList<Company>();
		if(conn!=null && !conn.isEmpty()){
			
				for(Connections con:conn)
				{
					if(con.getConnectedUserId()!=null){
						Users connectedUser=usersRepository.findById(con.getUserId());
						 if(connectedUser.getCompany_id()!=null) {
							 Company com=companyRepository.findById(connectedUser.getCompany_id());
							 companyList.add(com);
							 }
						 
					}
					
					if(con.getUserId()!=null){
						Users connectedUser=usersRepository.findById(con.getConnectedUserId());
						 if(connectedUser.getCompany_id()!=null) {
							 Company com=companyRepository.findById(connectedUser.getCompany_id());
							 companyList.add(com);
							 }
					}
				}
				
			 if(companyList!=null && companyList.size()!=0) {
					HashSet<Company> companyHashSet=new HashSet<Company>();
					for(Company companyListData:companyList) {
						companyHashSet.add(companyListData);
					}	 
					
					ArrayList<Company> companyList1=new ArrayList<Company>(companyHashSet);
					
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 jsonobjectFormat.setMessage("Company fetch Successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(companyList1);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 System.out.println("Else Part");
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("No companies linked");
				 jsonobjectFormat.setSuccess(false);
			
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				 
			 }
		}else {
			 
			 System.out.println(" Second Else Part");
			 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Company not found");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
		
		}catch(Exception e) {
			e.printStackTrace();
			 System.out.println("Exception");
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Exception Occured "+e.getMessage());
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
	}
}
