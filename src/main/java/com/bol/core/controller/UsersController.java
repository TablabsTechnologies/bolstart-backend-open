package com.bol.core.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;*/
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
//import com.pharmerz.Appcations.UploadObjectSingleOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.Accomplishment;
import com.bol.core.dto.Cities;
import com.bol.core.dto.Company;
import com.bol.core.dto.CompanyEmployee;
import com.bol.core.dto.Connections;
import com.bol.core.dto.Countries;
import com.bol.core.dto.Education;
import com.bol.core.dto.Experience;
import com.bol.core.dto.Skills;
import com.bol.core.dto.States;
import com.bol.core.dto.Users;
import com.bol.core.repository.AccomplishmentRepository;
import com.bol.core.repository.CitiesRepository;
import com.bol.core.repository.CommentOnPostRepository;
import com.bol.core.repository.CompanyEmployeeRepository;
import com.bol.core.repository.CompanyRepository;
import com.bol.core.repository.ConnectionsRepository;
import com.bol.core.repository.CountriesRepository;
import com.bol.core.repository.EducationRepository;
import com.bol.core.repository.ExperienceRepository;
import com.bol.core.repository.InstitutionRepository;
import com.bol.core.repository.LikeDislikeOnPostRepository;
import com.bol.core.repository.LikeOnPostCommentRepository;
import com.bol.core.repository.PostRepository;
import com.bol.core.repository.SkillsRepository;
import com.bol.core.repository.StatesRepository;
import com.bol.core.repository.UsersRepository;
import com.bol.core.utils.AmazonClient;
import com.bol.core.utils.AmazonEmailSms;
import com.bol.core.utils.CometChat;
import com.bol.core.utils.MimeTypes;
import com.bol.core.utils.PostMarkupEmail;
import com.bol.core.utils.SendEmailNotifications;
import com.bol.core.utils.SendOTPSmsNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "user controller",description = "used to register ,update and fetch user details")
@RequestMapping("/api")
public class UsersController {
	
	private final Logger log = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	MimeTypes mimeType;
	
	
	@Autowired
	SendEmailNotifications sendEmailNotifications;
	
	@Autowired
	AmazonEmailSms AmazonEmailSms;
	
	@Autowired
	UsersRepository  usersRepository;

	@Autowired
	PostRepository PostRepository;
	
	@Autowired
	LikeDislikeOnPostRepository likeDislikeOnPostRepository;
	
	@Autowired
	CommentOnPostRepository commentOnPostRepository;
	
	@Autowired
	LikeOnPostCommentRepository LikeOnPostCommentRepository;
	
	@Autowired
	SendOTPSmsNotifications sendOTPSmsNotifications;
	
	@Autowired
	EducationRepository EducationRepository;
	
	@Autowired
	AccomplishmentRepository AccomplishmentRepository;
	
	@Autowired
	ConnectionsRepository ConnectionsRepository;
	
	@Autowired
	CometChat CometChat;
	
	@Autowired
	ExperienceRepository ExperienceRepository;
	
	@Autowired
	SkillsRepository SkillsRepository;
	
	@Autowired
	CitiesRepository CitiesRepository;
	
	@Autowired
	StatesRepository StatesRepository;
	
	@Autowired
	CountriesRepository CountriesRepository;
	
	@Autowired
	InstitutionRepository InstitutionRepository;
	
	@Autowired
	CometChat cometChat;
	
	@Autowired
	PostMarkupEmail PostMarkupEmail;
	
	@Autowired
	AmazonClient amazonClient;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	CompanyEmployeeRepository companyEmployeeRepository;
	
	
	@PostMapping("/authLogin/emailId")
	public ResponseEntity<String> checkAuthUsingEmailId(@RequestParam("emailId")String emailId,@RequestParam(value="password",required = false)String password,@RequestParam(value="os",required = false)String os,@RequestParam(value="deviceId",required=false)String deviceId
			,@RequestParam(value="isgoogleLogged",required = false)boolean isGoogleLogged,@RequestParam(value="isFbLogged",required = false)boolean isFbLogged) throws JsonProcessingException{
		
		Users userDetailsObj=null;
		Users details=null;
		try{
			
			if(isGoogleLogged==true)
			{
			userDetailsObj=usersRepository.findByEmailIdAndIsGoogleLogged(emailId);
			
			if(userDetailsObj.getCompany_id()!=null) {
				
				Company companyObj=companyRepository.findOne(userDetailsObj.getCompany_id());
				
				if(companyObj!=null) {
					if(companyObj.getName()!=null) {
					userDetailsObj.setCompanyName(companyObj.getName());
					}
				
					if(companyObj.getLogo()!=null) {
						userDetailsObj.setCompnayLogo(companyObj.getLogo());
					}
				}
				
				
				
			}
			
			}else if(isFbLogged==true)
			{
				userDetailsObj=usersRepository.findByEmailIdAndIsFbLogged(emailId);
				
				if(userDetailsObj.getCompany_id()!=null) {
					
					Company companyObj=companyRepository.findOne(userDetailsObj.getCompany_id());
					
					if(companyObj!=null) {
						if(companyObj.getName()!=null) {
						userDetailsObj.setCompanyName(companyObj.getName());
						}
					
						if(companyObj.getLogo()!=null) {
							userDetailsObj.setCompnayLogo(companyObj.getLogo());
						}
					}
					
					
					
				}
			
			}
			else if(emailId!=null&& password!=null){
				userDetailsObj=usersRepository.findByEmailIdAndPassword(emailId, password);
				
				if(userDetailsObj.getCompany_id()!=null) {
					
					Company companyObj=companyRepository.findOne(userDetailsObj.getCompany_id());
					
					if(companyObj!=null) {
						if(companyObj.getName()!=null) {
						userDetailsObj.setCompanyName(companyObj.getName());
						}
					
						if(companyObj.getLogo()!=null) {
							userDetailsObj.setCompnayLogo(companyObj.getLogo());
						}
					}
					
					
					
				}
				
		
				}
			if(userDetailsObj!=null)
			{
				details=userDetailsObj;
				if(os!=null ||deviceId!=null)
				{
					if(deviceId!=null)
						userDetailsObj.setDeviceId(deviceId);
					
					if(os!=null)
						userDetailsObj.setOs(os);
					
					details= usersRepository.save(userDetailsObj);
				}
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication successful");
				 jsonobjectFormat.setSuccess(true); 
				 jsonobjectFormat.setData(details); 
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication not successful");
				 jsonobjectFormat.setSuccess(false); 
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		}catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User Authentication not successful");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper();  
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
		
	}
	
	
	
	  @PostMapping("/authLogin/mailSned") public ResponseEntity<String>
	  sendMailForRegistration(@RequestParam("toemailId")String toemailId, @RequestParam("subject")String subject,
			 @RequestParam("message")String message ) throws JsonProcessingException{
	  
	  
	  try { 
		 // PostMarkupEmail.SendMailForRegistration(toemailId,subject ,message); 
		  boolean flag= AmazonEmailSms.sendMailUsingAws(toemailId,subject, message);
		  if(flag)
		  {
			  JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			  jsonobjectFormat.setMessage("email sent successfully");
			  jsonobjectFormat.setSuccess(true);
			  jsonobjectFormat.setData(flag);
			  ObjectMapper Obj = new ObjectMapper();
			  String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			  return ResponseEntity.ok().body(customExceptionStr); 
		  }else {
			  JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			  jsonobjectFormat.setMessage("unable to send mail");
			  jsonobjectFormat.setSuccess(false);
			  ObjectMapper Obj = new ObjectMapper();
			  String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			  return ResponseEntity.ok().body(customExceptionStr);
		  }
	  }catch(Exception e) {
		  JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		  jsonobjectFormat.setMessage("unable to send mail");
		  jsonobjectFormat.setSuccess(false);
		  ObjectMapper Obj = new ObjectMapper();
		  String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		  return ResponseEntity.ok().body(customExceptionStr);
	  }
	  
  }
	 
	
	@PostMapping("/authLogin/mobileNumber")
	public ResponseEntity<String> checkAuthUsingMobileNumber(@RequestParam("mobileNumber")String mobileNumber,@RequestParam(value="password",required = false)String password,@RequestParam(value="os",required = false)String os,
			@RequestParam(value="deviceId",required = false)String deviceId) throws JsonProcessingException{
		Users details=null;
		try{
			Users userDetailsObj= usersRepository.findByMobileNumberAndPassword(mobileNumber, password);
			
          if(userDetailsObj.getCompany_id()!=null) {
				
				Company companyObj=companyRepository.findOne(userDetailsObj.getCompany_id());
				
				if(companyObj!=null) {
					if(companyObj.getName()!=null) {
					userDetailsObj.setCompanyName(companyObj.getName());
					}
				
					if(companyObj.getLogo()!=null) {
						userDetailsObj.setCompnayLogo(companyObj.getLogo());
					}
				}
				
				
				
			}
			
			details=userDetailsObj;
			if(userDetailsObj!=null){
				if(os!=null ||deviceId!=null)
				{
					if(deviceId!=null)
						userDetailsObj.setDeviceId(deviceId);
					
					if(os!=null)
						userDetailsObj.setOs(os);
					
					details= usersRepository.save(userDetailsObj);
				}
			
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication successful");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(details);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication not successful");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		}catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User Authentication not successful");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	
	
	@GetMapping("/user/states")
	public ResponseEntity<String> getStatesBasedOnCountry(@RequestParam("countryId")Long countryId) throws JsonProcessingException{
		List<States> states=StatesRepository.findByCountryId(countryId);
		Collections.sort(states);
		 
		if(states!=null && !states.isEmpty())
		{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("got states successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(states);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("no states found");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		
	}
	
	@GetMapping("/user/cities/filter")
	public ResponseEntity<String> getCityWithFilter(@RequestParam("cityName")String cityName) throws JsonProcessingException{
		List<Cities> cities=CitiesRepository.findByNameContainsIgnoreCase(cityName);
		if(cities!=null && !cities.isEmpty())
		{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("got cities successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(cities);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("no cities found");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		
	}
	
	@GetMapping("/user/cities")
	public ResponseEntity<String> getCitiesByState(@RequestParam("stateId")Long stateId) throws JsonProcessingException{
		List<Cities> cities=CitiesRepository.findByStateId(stateId);
		if(cities!=null && !cities.isEmpty())
		{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("got cities successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(cities);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("no cities found");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		
	}
	
	@GetMapping("/user/countries")
	public ResponseEntity<String> getAllCountries() throws JsonProcessingException{
		List<Countries> countries=(List<Countries>) CountriesRepository.findAll();
		if(countries!=null && !countries.isEmpty())
		{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("got countries successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(countries);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("no countries found");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		
	}
	

	@GetMapping("/user/uid")
	public ResponseEntity<String> getUserUsingUid(@RequestParam("uid")String uid) throws JsonProcessingException{
		try {
				cometChat.getUser(uid);
		
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("got countries successfully");
				 jsonobjectFormat.setSuccess(true);
				
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}catch(Exception e){
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("no countries found");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		
	}
	
	
	  @PostMapping("/aws/mail")
	  public ResponseEntity<String> sendEmailFromAws(@RequestParam("toemailId")String toemailId, @RequestParam("subject")String subject,
				 @RequestParam("message")String message )
	  throws Exception{ 
		  boolean flag= AmazonEmailSms.sendMailUsingAws(toemailId,subject, message);
	  if(flag)
	  {
		  JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		  jsonobjectFormat.setMessage("email sent successfully");
		  jsonobjectFormat.setSuccess(true); 
		  jsonobjectFormat.setData(flag);
		  ObjectMapper Obj = new ObjectMapper(); 
		  String customExceptionStr =Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		  return ResponseEntity.ok().body(customExceptionStr);
	  }else {
		  JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		  jsonobjectFormat.setMessage("unable to send email");
		  jsonobjectFormat.setSuccess(false); 
		  ObjectMapper Obj = new ObjectMapper(); 
		  String customExceptionStr =Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		  return ResponseEntity.ok().body(customExceptionStr);
	  }
	  
	  }
	 
	 
	
	
	
	/*
	 * 
	 * Registration API for Users
	 * 
	 */
	@Transactional
	@PostMapping("/user")
	public ResponseEntity<String> registerUser(@RequestBody Users reqUser) throws JsonProcessingException {
		log.debug("REST request to register user : {}", reqUser);
		
		
		List<Users> listOfUserValidation= usersRepository.findByEmailIdOrMobileNumber(reqUser.getEmailId(),(reqUser.getMobileNumber()));
		
		if(listOfUserValidation.size() > 0){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Email Id and Mobile number should be unique");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
		
		Users userEmailValidation= usersRepository.findByEmailId(reqUser.getEmailId());
		Users userMobileValidation= usersRepository.findByMobileNumber(reqUser.getMobileNumber());
		
		try{
			if(userEmailValidation!=null){
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Email Id already registered");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		}catch(NullPointerException e){
			
		}
		
		try{
			if(userMobileValidation!=null){
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Mobile number already registered");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		}catch(NullPointerException e){
			
		}
				reqUser.setIsEmailVerified("false");
				
				List<Users> users=(List<Users>) usersRepository.findAll();
				if(users.size()<1000)
				{
					reqUser.setIsPriority("true");
				}else {
					reqUser.setIsPriority("false");
				}
				if(reqUser.getProfilePicture()==null)
				{
					reqUser.setProfilePicture("https://bolstartimages.s3.ap-south-1.amazonaws.com/profile/default/profile_avater.png");
				}
				if(reqUser.getTimlineImage()==null)
				{
					reqUser.setTimlineImage("url(https://bolstartimages.s3.ap-south-1.amazonaws.com/profile/default/timline.jpg)");
				}
				
		 Users result =  usersRepository.save(reqUser);
				
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("registration successfull");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		
	}
	
	
	@Transactional
	@PutMapping("/user")
	public ResponseEntity<String> updateUser(@RequestBody Users reqUser) throws JsonProcessingException, JSONException  {
		log.debug("REST request to update Attendance : {}", reqUser);
		Users temp = usersRepository.findById(reqUser.getId());
		if (temp == null) {
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("In Valid Id");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 	
		}
		
		if(reqUser.getAccomplishment()!=null)
		{
			List<Accomplishment> accomplishment=reqUser.getAccomplishment();
			for(Accomplishment acc:accomplishment)
			{
				acc.setUserId(reqUser.getId());
				AccomplishmentRepository.save(acc);
			}
		}
		if(reqUser.getEducation()!=null)
		{
			List<Education> education=reqUser.getEducation();
			for(Education ed:education)
			{
			ed.setUserId(reqUser.getId());
			EducationRepository.save(ed);
			}
		}
		
		if(reqUser.getExperience()!=null)
		{
			List<Experience> experience=reqUser.getExperience();
			for(Experience exp:experience)
			{
			exp.setUserId(reqUser.getId());
			ExperienceRepository.save(exp);
			}
		}
	
		
		if(reqUser.getSkill()!=null)
		{
			List<Skills> skill=reqUser.getSkill();
			for(Skills sk:skill)
			{
			sk.setUserId(reqUser.getId());
			SkillsRepository.save(sk);
			}
			
			
		}
		
		
		
		if(reqUser.getAdhaarCard()!=null){
			temp.setAdhaarCard(reqUser.getAdhaarCard());
		}
		
		if(reqUser.getMonth()!=null){
			temp.setMonth(reqUser.getMonth());
		}
		
		if(reqUser.getDay()!=null){
			temp.setDay(reqUser.getDay());
		}
		
		if(reqUser.getGst()!=null){
			temp.setGst(reqUser.getGst());
		}
		
		if(reqUser.getState()!=null){
			temp.setState(reqUser.getState());
		}
		
		if(reqUser.getLocation()!=null){
			temp.setLocation(reqUser.getLocation());
		}
		
		if(reqUser.getBio()!=null){
			temp.setBio(reqUser.getBio());
		}
		
		
		if(reqUser.getTimlineImage()!=null){
			temp.setTimlineImage(reqUser.getTimlineImage());
		}
		
		if(reqUser.getCin()!=null){
			temp.setCin(reqUser.getCin());
		}
		
		if(reqUser.getIsMobileVerified()!=null){
			temp.setIsMobileVerified(reqUser.getIsMobileVerified());
		}
		
		if(reqUser.getIsEmailVerified()!=null){
			temp.setIsEmailVerified(reqUser.getIsEmailVerified());
		}
		if(reqUser.getEmailId()!=null){
			temp.setEmailId(reqUser.getEmailId());
		}
		
		
		
		if(reqUser.getHeadline()!=null){
			temp.setHeadline(reqUser.getHeadline());
		}

		if(reqUser.getCountry()!=null){
			temp.setCountry(reqUser.getCountry());
		}
	

		
		
		if(reqUser.getMobileNumber()!=null){
			temp.setMobileNumber(reqUser.getMobileNumber());
		}
			
		if(reqUser.getPassword()!=null){
			temp.setPassword(reqUser.getPassword());
		}
		
		if(reqUser.getTypeOfUser()!=null){
			temp.setTypeOfUser(reqUser.getTypeOfUser());
		}
		
		if(reqUser.getIsEmailVerified()!=null){
			temp.setIsEmailVerified(reqUser.getIsEmailVerified());
		}
		
		if(reqUser.getIsMobileVerified()!=null){
			temp.setIsMobileVerified(reqUser.getIsMobileVerified());
		}
		if(reqUser.getIndustryName()!=null){
			temp.setIndustryName(reqUser.getIndustryName());
		}
		
		if(reqUser.getUid()!=null){
			temp.setUid(reqUser.getUid());
		}
		
		
		if(reqUser.getFirstName()!=null||reqUser.getLastName()!=null ||reqUser.getProfilePicture()!=null)
		{

			if(reqUser.getFirstName()!=null){
				temp.setFirstName(reqUser.getFirstName());
			}
			if(reqUser.getLastName()!=null){
				temp.setLastName(reqUser.getLastName());
			}
			if(reqUser.getProfilePicture()!=null){
				temp.setProfilePicture(reqUser.getProfilePicture());
			}
			if(temp.getUid()!=null&& temp.getIsPriority().equalsIgnoreCase("true"))
			{
				boolean flag=cometChat.updateUser(temp.getUid(),temp.getFirstName()+" "+temp.getLastName(),temp.getProfilePicture());		
				if(flag)
				{
					
				}else {
					Users result2 =  usersRepository.save(temp);
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 jsonobjectFormat.setMessage("user details updated successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(result2);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}
			}
		}
		 Users result2 =  usersRepository.save(temp);
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("user details updated successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result2);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	}

	 @PostMapping("/profilePicture/users")
	 public ResponseEntity<String> saveProfilePicture(@RequestParam(value = "profilePicture", required=false) MultipartFile file,@RequestParam("userId") Long userId) throws IOException{
		 Users userMaster = usersRepository.findById(userId);
		 if(userMaster==null)
		 {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("user not available");
			 jsonobjectFormat.setSuccess(false);
	
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
		try
		 {
		 amazonClient=new AmazonClient();
		 
		  
		  if(file!=null){
			  userMaster.setProfilePicture(amazonClient.uploadFile(file));
		  }
		  
		  	Users user =   usersRepository.save(userMaster);
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("profile pic saved successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(user);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }catch(Exception e)
			{
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to saved profile picture");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			}
	 }
	 
	 @PostMapping("/users/timlineImage")
	 public ResponseEntity<String> saveTimlineImage(@RequestParam(value = "timlineImage", required=false) MultipartFile file,@RequestParam("userId") Long userId) throws IOException{
		 
		 Users userMaster = usersRepository.findById(userId);
		 if(userMaster==null)
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
			  userMaster.setTimlineImage(amazonClient.uploadFile(file));
		  }
		  Users user =   usersRepository.save(userMaster);	
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("timline image  saved successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(user);
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
	 
	@GetMapping("/user")
	public ResponseEntity<String> getAllUsers(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=30;
		 }
   	 if(page!=null){
		 }else{
			 page=0;
		 }
   	 
		Pageable pageable =new PageRequest(page, size);

		List<Users> userList=(List<Users>) usersRepository.findAll();
		
		System.out.println(" userList "+userList.size());
			for(Users user:userList)
			{
				
				  List<Accomplishment> acc=AccomplishmentRepository.findByUserId(user.getId());
				  if(acc!=null && !acc.isEmpty()) user.setAccomplishment(acc);
				  				  
				  List<Education> ed=EducationRepository.findByUserId(user.getId());
				  
				  if(ed!=null && !ed.isEmpty()) {
				  
					  Collections.sort(ed); 
				  
				  }
				  
				  if(ed!=null && !ed.isEmpty()) user.setEducation(ed);
				  
				  List<Experience> exp=ExperienceRepository.findByUserId(user.getId());
				  if(exp!=null) {
					  Collections.sort(exp);
				  }
				  if(exp!=null) user.setExperience(exp);
				  
				 
				  List<Skills> skill=SkillsRepository.findByUserId(user.getId());
				  if(skill!=null) user.setSkill(skill);		
			}	
			
			Page<Users> result=new PageImpl<>(userList, pageable, userList.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all users succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	}
	
	
	
	@GetMapping("/user/userType")
	public ResponseEntity<String> getUsersBasedOnUserType(@RequestParam("userType")Long userType) throws JsonProcessingException {
	
   
		log.debug("REST request to get user based on user type : {}", userType);
		List<Users> userList =  usersRepository.findByTypeOfUser(userType);
		if(userList!=null && !userList.isEmpty())
		{
			for(Users user:userList)
			{
				List<Accomplishment> acc=AccomplishmentRepository.findByUserId(user.getId());
				if(acc!=null && !acc.isEmpty())
					user.setAccomplishment(acc);
				
				
				
				List<Education> ed=EducationRepository.findByUserId(user.getId());
				if(ed!=null && !ed.isEmpty()) {
					Collections.sort(ed);
				}
				if(ed!=null && !ed.isEmpty())
					user.setEducation(ed);
				
				List<Experience> exp=ExperienceRepository.findByUserId(user.getId());
				if(exp!=null) {
					Collections.sort(exp);
				}
				if(exp!=null)
					user.setExperience(exp);
				
				List<Skills> skill=SkillsRepository.findByUserId(user.getId());
				if(skill!=null)
					user.setSkill(skill);
				
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("user details fetched successfully");
		 jsonobjectFormat.setData(userList);
		 jsonobjectFormat.setSuccess(true);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }else {
	 		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("user not found");
			 
			 jsonobjectFormat.setSuccess(false);
	        ObjectMapper Obj = new ObjectMapper(); 
	        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
	}
	
	
	@PutMapping("/user/skills")
	public ResponseEntity<String> updateSkills(@RequestBody Skills skill) throws JsonProcessingException {
	
   
		log.debug("REST request to get update skills : {}", skill);
		
		Skills result=null;
		if(skill!=null && skill.getId()!=null)
		{
			Skills temp=SkillsRepository.findById(skill.getId());
			if(skill.getSkillName()!=null)
				temp.setSkillName(skill.getSkillName());
			if(skill.getSkillTime()!=null)
				temp.setSkillTime(skill.getSkillTime());
			if(skill.getTypeOfSkill()!=null)
				temp.setTypeOfSkill(skill.getTypeOfSkill());
			 result=SkillsRepository.save(temp);
			
		}else {
			result=SkillsRepository.save(skill);
		}
		
		if(result!=null)
		{
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("skill details updated successfully");
		 jsonobjectFormat.setData(result);
		 jsonobjectFormat.setSuccess(true);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }else {
	 		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to update skill details");
			 
			 jsonobjectFormat.setSuccess(false);
	        ObjectMapper Obj = new ObjectMapper(); 
	        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
	}
	
	
	@PutMapping("/user/accomplishment")
	public ResponseEntity<String> updateAccomplishments(@RequestBody Accomplishment accomp) throws JsonProcessingException {
	
   
		log.debug("REST request to get update accomplishments : {}", accomp);
		
		Accomplishment result=null;
		if(accomp.getId()!=null)
		{
			Accomplishment temp=AccomplishmentRepository.findById(accomp.getId());
			if(accomp.getCertificationInstitute()!=null)
				temp.setCertificationInstitute(accomp.getCertificationInstitute());
			if(accomp.getDescription()!=null)
				temp.setDescription(accomp.getDescription());
			if(accomp.getFromDate()!=null)
				temp.setFromDate(accomp.getFromDate());
			if(accomp.getName()!=null)
				temp.setName(accomp.getName());
			if(accomp.getTillDate()!=null)
				temp.setTillDate(accomp.getTillDate());
			if(accomp.getType()!=null)
				temp.setType(accomp.getType());
			result=AccomplishmentRepository.save(temp);
			
		}else {
			result=AccomplishmentRepository.save(accomp);
		}
		
		if(result!=null )
		{
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("accomplishment details updated successfully");
		 jsonobjectFormat.setData(result);
		 jsonobjectFormat.setSuccess(true);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }else {
	 		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to update accomplishment details");
			 
			 jsonobjectFormat.setSuccess(false);
	        ObjectMapper Obj = new ObjectMapper(); 
	        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
	}
	
	
	@PutMapping("/user/education")
	public ResponseEntity<String> updateEducation(@RequestBody Education edu) throws JsonProcessingException {
	
   
		log.debug("REST request to get update Education : {}", edu);
		
		Education result=null;
		if(edu.getId()!=null)
		{
			Education  temp=EducationRepository.findById(edu.getId());
			if(edu.getActivities()!=null)
				temp.setActivities(edu.getActivities());
			if(edu.getDegree()!=null)
				temp.setDegree(edu.getDegree());
			if(edu.getEndDate()!=null)
				temp.setEndDate(edu.getEndDate());
			if(edu.getGrade()!=null)
				temp.setGrade(edu.getGrade());
			if(edu.getInstitutionId()!=null)
				temp.setInstitutionId(edu.getInstitutionId());
			if(edu.getInstitutionLogo()!=null)
				temp.setInstitutionLogo(edu.getInstitutionLogo());
			if(edu.getInstitutionName()!=null)
				temp.setInstitutionName(edu.getInstitutionName());
			if(edu.getSocieties()!=null)
				temp.setSocieties(edu.getSocieties());
			if(edu.getStartDate()!=null)
				temp.setStartDate(edu.getStartDate());
			if(edu.getStream()!=null)
				temp.setStream(edu.getStream());
		
			
			result=EducationRepository.save(temp);
			
		}else {
			result=EducationRepository.save(edu);
		}
		
		if(result!=null )
		{
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Education details updated successfully");
		 jsonobjectFormat.setData(result);
		 jsonobjectFormat.setSuccess(true);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }else {
	 		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to update Education details");
			 
			 jsonobjectFormat.setSuccess(false);
	        ObjectMapper Obj = new ObjectMapper(); 
	        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
	}
	
	@PutMapping("/user/experience")
	public ResponseEntity<String> updateExperience(@RequestBody Experience exp) throws JsonProcessingException {
	
   
		log.debug("REST request to get update Experience : {}", exp);
		
		Experience result=null;
		if(exp.getId()!=null)
		{
			Experience  temp=ExperienceRepository.findById(exp.getId());
			if(exp.getCompanyId()!=null)
				temp.setCompanyId(exp.getCompanyId());
			if(exp.getCompanyLogo()!=null)
				temp.setCompanyLogo(exp.getCompanyLogo());
			if(exp.getCompanyName()!=null)
				temp.setCompanyName(exp.getCompanyName());
			if(exp.getDescription()!=null)
				temp.setDescription(exp.getDescription());
			if(exp.getEndMonth()!=null)
				temp.setEndMonth(exp.getEndMonth());
			if(exp.getEndYear()!=null)
				temp.setEndYear(exp.getEndYear());
//			if(exp.getIsWorking()!=null)
//				temp.setIsWorking(exp.getIsWorking());
			if(exp.getJobLocation()!=null)
				temp.setJobLocation(exp.getJobLocation());
			if(exp.getStartMonth()!=null)
				temp.setStartMonth(exp.getStartMonth());
			if(exp.getStartYear()!=null)
				temp.setStartYear(exp.getStartYear());
			if(exp.getTitle()!=null)
				temp.setTitle(exp.getTitle());	
			result=ExperienceRepository.save(temp);
			
		}else {
			result=ExperienceRepository.save(exp);
		}
		
		if(result!=null ){
		 //delete record from 'company_employee' if record exists
		  try{
			  List<CompanyEmployee> companyEmployeeList= companyEmployeeRepository.findByUserIdAndCompanyId(result.getUserId(), result.getCompanyId());
			  for(CompanyEmployee companyEmployee:companyEmployeeList){
				  companyEmployeeRepository.delete(companyEmployee);
			  }
		  }catch(Exception e){
			  System.out.println("Unable to delete company employee record "+result.getUserId()+" company id "+result.getCompanyId() +" due to "+e.getMessage());
		  }
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Experience details updated successfully");
		 jsonobjectFormat.setData(result);
		 jsonobjectFormat.setSuccess(true);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }else {
	 		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to update Experience details");
			 
			 jsonobjectFormat.setSuccess(false);
	        ObjectMapper Obj = new ObjectMapper(); 
	        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
	}
	

	@Transactional
	@DeleteMapping("/user/skils/delete")
	public ResponseEntity<String> deleteSkillsForUser(@RequestParam("skillId") Long skillId) throws  JsonProcessingException {
		log.debug("REST request to delete skills for user : {}", skillId);
		
			try{
				 SkillsRepository.deleteById(skillId);
				 
			
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record was not deleted successfully");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		
		}
	}
	
	@Transactional
	@DeleteMapping("/user/accomplishment/delete")
	public ResponseEntity<String> deleteAccomplishmentForUser(@RequestParam("accomplishmentId") Long accomplishmentId) throws  JsonProcessingException {
		log.debug("REST request to delete skills for user : {}", accomplishmentId);
		
			try{
				 AccomplishmentRepository.deleteById(accomplishmentId);
				 
			
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record was not deleted successfully");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		
		}
	}
	
	@Transactional
	@DeleteMapping("/user/education/delete")
	public ResponseEntity<String> deleteEducationForUser(@RequestParam("educationId") Long educationId) throws  JsonProcessingException {
		log.debug("REST request to delete skills for user : {}", educationId);
		
			try{
				 EducationRepository.deleteById(educationId);
				 
			
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record was not deleted successfully");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		
		}
	}
	
	
	@Transactional
	@DeleteMapping("/user/experienec/delete")
	public ResponseEntity<String> deleteExperienceForUser(@RequestParam("experienceId") Long experienceId) throws  JsonProcessingException {
		log.debug("REST request to delete skills for user : {}", experienceId);
		
			try{
				 ExperienceRepository.deleteById(experienceId);
				 
			
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record was not deleted successfully");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		
		}
	}
	
	
	@GetMapping("/user/id")
	public ResponseEntity<String> getParticularUserById(@RequestParam("id")Long id,@RequestParam("userId")Long userId) throws JsonProcessingException {
	
   	
		log.debug("REST request to get particular user : {}", id);
		Users user =  usersRepository.findById(id);
		if(user!=null)
		{
			
			    if(user.getCompany_id()!=null) {
			    	
			    	Company companyObj=companyRepository.findOne(user.getCompany_id());
			    	
			    	if(companyObj!=null) {
			    		
			    		if(companyObj.getName()!=null) {
			    			user.setCompanyName(companyObj.getName());
			    		}
			    		
			    		if(companyObj.getLogo()!=null) {
			    			user.setCompnayLogo(companyObj.getLogo());
			    		}
			    	}
			    	
			    }
			
				List<Accomplishment> acc=AccomplishmentRepository.findByUserId(user.getId());
				if(acc!=null && !acc.isEmpty())
					user.setAccomplishment(acc);
				
				
				
				List<Education> ed=EducationRepository.findByUserId(user.getId());
				if(ed!=null && !ed.isEmpty()) {
					Collections.sort(ed);
				}
				
				if(ed!=null && !ed.isEmpty())
					user.setEducation(ed);
				
				List<Experience> exp=ExperienceRepository.findByUserId(user.getId());
				if(exp!=null) {
				 Collections.sort(exp);	
				}
				
				if(exp!=null)
					user.setExperience(exp);
				
				List<Skills> skill=SkillsRepository.findByUserId(user.getId());
				if(skill!=null)
					user.setSkill(skill);

				List<Connections> conn=ConnectionsRepository.findByUserId(user.getId());
				user.setConnectionCount(conn.size());
				if(user.getId()!=userId)
				{
					Connections userCon=ConnectionsRepository.findByConnectedUserIdAndUserId(userId,user.getId());
					if(userCon!=null)
					{
						user.setIsConnected(userCon.getIsConnected());
						user.setIsFollowed(userCon.getIsFollowBack());
					
					}
					Connections conCon=ConnectionsRepository.findByUserIdAndConnectedUserId(userId,user.getId());
					if(conCon!=null)
					{
						user.setIsConnected(conCon.getIsConnected());
						user.setIsFollowed(conCon.getIsFollowed());
					
					}
				}
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("user details fetched successfully");
		 jsonobjectFormat.setData(user);
		 jsonobjectFormat.setSuccess(true);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }else {
	 		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("user not found");
			 
			 jsonobjectFormat.setSuccess(false);
	        ObjectMapper Obj = new ObjectMapper(); 
	        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
	}

	
	/*
	 * 
	 * Delete user api for users
	 * 
	 * Currently we are not using this api
	 * 
	 */
	
	@Transactional
	@DeleteMapping("/user")
	public ResponseEntity<String> deleteUser(@RequestParam("id") Long id) throws  JsonProcessingException {
		log.debug("REST request to delete user : {}", id);
		
			try{
				
				LikeOnPostCommentRepository.deleteByUserId(id);
				likeDislikeOnPostRepository.deleteByUserId(id);
				commentOnPostRepository.deleteByUserId(id);
				PostRepository.deleteByUserId(id);
				Connections connection=ConnectionsRepository.findUser(id);
				if(connection!=null)
				{
						ConnectionsRepository.deleteById(connection.getId());
						
						Users user=usersRepository.findById(connection.getUserId());
						Users user2=usersRepository.findById(connection.getConnectedUserId());
						List<String> accepted=new ArrayList<>();
						if(user2.getUid()!=null && user.getUid()!=null)
						{
							accepted.add(user2.getUid());
							cometChat.deleteFriend(user.getUid(), accepted);
						}	
				}
				AccomplishmentRepository.deleteByUserId(id);
				SkillsRepository.deleteByUserId(id);
				EducationRepository.deleteByUserId(id);
				ExperienceRepository.deleteByUserId(id);
				 usersRepository.deleteById(id);
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record was not deleted successfully");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		
		}
	}
	
	
	 
	 
	 @PostMapping("/user/forgotPassword")
	 public ResponseEntity<String> forgotPassword(@RequestParam(value = "mobileNumber", required=false) String mobileNumber) throws JsonProcessingException{
		
			  Users userMaster= usersRepository.findByMobileNumber(mobileNumber);
		if(userMaster!=null){
			  JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Process Initiated successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(userMaster);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else{
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("please enter valid Mobile Number");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
		 
	 }
	 
	 @PostMapping("/user/SendEmail")
	 public ResponseEntity<String> sendEmailToUser(@RequestParam("emailId") String emailId,@RequestParam("subject") String subject,@RequestParam("message") String message ) throws JsonProcessingException{
		try{
		 sendEmailNotifications.sendSimpleMessage(emailId, subject, message);
	 		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Email sent successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 	 
		}catch(Exception e){
			e.printStackTrace();
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Something went wrong unable to send verification mail");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 }
	 
	 @PostMapping("/user/SendOTP")
	 public ResponseEntity<String> sendOtpToMobileNumber(@RequestParam(value = "mobileNumber", required=true) String mobileNumber,@PageableDefault(page=0,size = Integer.MAX_VALUE) Pageable pageable) throws JsonProcessingException{
		try{
			/* String response=sendOTPSmsNotifications.sendOtp(mobileNumber, pageable); */
			String otp=getRandomNumberString();
			//PostMarkupEmail.SendMailForForgotPassword(mobileNumber,"OTP verification","your one time password (OTP) for email verfication is : "+otp);
			boolean flag=AmazonEmailSms.sendAwsMailForForgotPassword(mobileNumber,"OTP verification","your one time password (OTP) for email verfication is : "+otp);
			if(flag)
			{
			 	 Users userMaster= usersRepository.findByEmailId(mobileNumber);
				 userMaster.setOtp(otp);
				 usersRepository.save(userMaster);
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("OTP sent  succcessfully");
				 jsonobjectFormat.setSuccess(true);
				
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else {
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Something went wrong unable to send OTP");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
	 	 
		}catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Something went wrong unable to send OTP");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 }
	 
	 @PostMapping("/user/afterRegistration")
	 public ResponseEntity<String> sendMailAfterRegistration(@RequestParam(value = "emailId", required=true) String emailId,@RequestParam("subject")String subject) throws JsonProcessingException{
		try{
			/* String response=sendOTPSmsNotifications.sendOtp(mobileNumber, pageable); */
			//PostMarkupEmail.SendMailAfterRegistration(emailId, subject);
			boolean flag=AmazonEmailSms.sendAwsMailAfterRegistration(emailId, subject);
			if(flag)
			{
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("email sent  succcessfully");
			 jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(flag);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			}else {
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Something went wrong unable to send mail");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
	 	 
		}catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Something went wrong unable to send mail");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 }
	 
	 @PostMapping("/user/verifyOtp")
	 public ResponseEntity<String> verifyOtpForMobile(@RequestParam(value = "mobileNumber", required=true) String mobileNumber,@RequestParam("otp") String otp,@RequestParam(value = "otpKey", required=false) String otpKey,@PageableDefault(page=0,size = Integer.MAX_VALUE) Pageable pageable) throws JsonProcessingException{
		 try{
				/*
				 * String response= sendOTPSmsNotifications.verifyOtpEnteredByUser(mobileNumber,
				 * otp, otpKey, pageable);
				 */
			
			Users userMaster= usersRepository.findByEmailId(mobileNumber);
			if(userMaster.getOtp().equals(otp))
			{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("OTP verification successfull");
				 jsonobjectFormat.setSuccess(true);
			
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
		
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("please enter valid otp");
		 jsonobjectFormat.setSuccess(true);
			/* jsonobjectFormat.setData(response); */
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				/* jsonobjectFormat.setMessage("please enter valid otp"); */
			 jsonobjectFormat.setMessage("unable to verify otp");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		 
	 } 
	 
	 @PostMapping("/user/resetPassword")
	 public ResponseEntity<String> resetPasswordUsingMobileNumber(@RequestParam("mobileNumber") String mobileNumber,@RequestParam("password") String password) throws JsonProcessingException{
		

		 if(mobileNumber!=null){
				/* Users userMaster= usersRepository.findByMobileNumber(mobileNumber); */
			 Users userMaster= usersRepository.findByEmailId(mobileNumber);
			 if(userMaster.getId()!=null){
				 //user found and using email Id to send notifications
				 userMaster.setPassword(password);
				  usersRepository.save(userMaster);
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("reset password successfull");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(userMaster);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
		 }
		 
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Please enter valid credentials to reset password...");
		 jsonobjectFormat.setSuccess(false);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 }
	 
	 
	 public static String getRandomNumberString() {
		    // It will generate 6 digit random Number.
		    // from 0 to 999999
		    Random rnd = new Random();
		    int number = rnd.nextInt(999999);

		    // this will convert any number sequence into 6 character.
		    return String.format("%06d", number);
		}
	 
	 @GetMapping("/user/logout")
		public void logout(Model model,HttpServletRequest request,@RequestParam("id") Long id) {
			request.getSession().setAttribute("deviceId", null);
			request.getSession().removeAttribute("deviceId");
			request.getSession().invalidate();
			Users userMaster=  usersRepository.findById(id);
			try{
			if(userMaster.getId()!=null){
				userMaster.setDeviceId("");
				 usersRepository.save(userMaster);
			}
			}catch(Exception e){
				
			}
			
		}
	 
	 
	
	 int getNumberOfDaysInCurrentMonth() throws ParseException{
			LocalDate currentdate = LocalDate.now();
			YearMonth yearMonth = YearMonth.of( currentdate.getYear(),  currentdate.getMonth().getValue() );  // January of 2015.
			LocalDate last = yearMonth.atEndOfMonth();
			Date date = Date.from(last.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        String dateInString = "01-"+(month+1)+"-"+year;
	        Date startDate = sdf.parse(dateInString);
	        int days= calculateDuration(startDate,date);
	        return days;
	 }
	 
		public static int calculateDuration(Date startDate, Date endDate)
	    {
	      Calendar startCal = Calendar.getInstance();
	      startCal.setTime(startDate);

	      Calendar endCal = Calendar.getInstance();
	      endCal.setTime(endDate);

	      int workDays = 0;

	      if (startCal.getTimeInMillis() > endCal.getTimeInMillis())
	      {
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	      }

	      while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
	    	    if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY &&
	    	        startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {

	    	        workDays++;
	    	    }

	    	    startCal.add(Calendar.DAY_OF_MONTH, 1);
	    	}
	      
	      return workDays;
	    }
		
		
		public static boolean calculateIfTaskIsFromThisMonth(Date startDate, Date endDate,Date task)
	    {
	      Calendar startCal = Calendar.getInstance();
	      startCal.setTime(startDate);

	      Calendar core = Calendar.getInstance();
	      core.setTime(task);

	      
	      Calendar endCal = Calendar.getInstance();
	      endCal.setTime(endDate);

	      int workDays = 0;

	      if (startCal.getTimeInMillis() > endCal.getTimeInMillis())
	      {
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	      }

	      while ((startCal.getTimeInMillis() <= core.getTimeInMillis()) && (core.getTimeInMillis() <= endCal.getTimeInMillis() )) {
	    	   return true;
	    	}
	      
	      return false;
	    }
		
		@GetMapping("/user/seach/byName")
		public ResponseEntity<String> getUserByName(@RequestParam("name") String name,@RequestParam("userId") Long userId) throws JsonProcessingException{
			try {
			List<Users> usersList=usersRepository.findByNames(name);
		
			if((usersList!=null && !usersList.isEmpty()))
			{
				Users user=usersRepository.findById(userId);
				if(usersList.contains(user))
					usersList.remove(user);
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("got users successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(usersList);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Data not found");
				 jsonobjectFormat.setSuccess(false);
			
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			}catch(Exception e) {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Data not found");
				 jsonobjectFormat.setSuccess(false);
			
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}	
		}
		
		@GetMapping("/user/seach/byEmailId")
		public ResponseEntity<String> getUserByName(@RequestParam("emailId") String emailId) throws JsonProcessingException{
			try {
			Users user=usersRepository.findByEmailId(emailId);
		
			if((user!=null)){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("got users successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(user);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Data not found");
				 jsonobjectFormat.setSuccess(false);
			
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			}catch(Exception e) {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Data not found");
				 jsonobjectFormat.setSuccess(false);
			
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}	
		}
		
		
		@GetMapping("/city/getAll")
		public ResponseEntity<String> getAllCity() throws JsonProcessingException {

			try {
				List<Cities> city = (List<Cities>) CitiesRepository.findAll();

		
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("Data fetch successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData(city);
			
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
		
			
			} catch (Exception e) {
				// TODO: handle exception

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
