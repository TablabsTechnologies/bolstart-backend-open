package com.bol.core.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.AddressBook;
import com.bol.core.dto.AddressBookMemberEntity;

import com.bol.core.dto.Chart;
import com.bol.core.dto.Chart_Response;
import com.bol.core.dto.Company;
import com.bol.core.dto.JobPost;
import com.bol.core.dto.JobTitles;
import com.bol.core.dto.Post;
import com.bol.core.dto.Skills;
import com.bol.core.dto.Users;
import com.bol.core.exception.CustomParameterizedException;
import com.bol.core.repository.CompanyRepository;
import com.bol.core.repository.JobPostRepository;
import com.bol.core.repository.JobTitleRepository;
import com.bol.core.repository.UsersRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@Api(value = "Job Post",description = "used to post jobs")
@RequestMapping("/jobpost")
public class JobPostController {

	@Autowired
	JobPostRepository jobPostRepository;
	
	@Autowired
	JobTitleRepository jobTitleRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	UsersRepository usersRepository;
	
	

	

	

	@GetMapping("/jobpost/isadminoruser")
	public ResponseEntity<String> getIsUserOrAdmin(@RequestParam("userRole") String userRole/* ,@RequestParam( value = "recruiter", required = false) long recruiter*/ /*,@RequestBody JobPost jobPost*/) throws JsonProcessingException{
			
		try {
			Users users;
			JobPost jobPost;
			//List<JobPost> jobpost=jobPostRepository.findByIsUserOrAdmin(userRole);
//			1=admin
//			0=user
	
			List<JobPost> IsUserOrAdmin = new ArrayList<>();
			
			if (userRole.equalsIgnoreCase("user")) {
	
				
				List<JobPost> jobPost1 = (List<JobPost>) jobPostRepository.findAll();
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data fetch successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(jobPost1);
				 
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
			else {
				
				List<Users> userDetail = usersRepository.findByIsUserOrAdmin(userRole);
				

				userDetail
						.forEach(val -> IsUserOrAdmin.addAll(this.jobPostRepository.findByRecruiter(val.getId())));
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data fetch successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(IsUserOrAdmin);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}

			


			 
			
			
			
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

	@GetMapping("/job/getAll")
	public ResponseEntity<String> getAllJobPosts() throws JsonProcessingException {

		try {
			List<JobPost> jobPost = (List<JobPost>) jobPostRepository.findAll();

			// System.out.println("companyList "+companyList.size());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPost);
		
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


			
	
	@PostMapping("/save")
	public ResponseEntity<String> saveJobPost(@RequestBody JobPost jobPost) throws JsonProcessingException {

		try {
			
			JobPost jobPost1 = jobPostRepository.save(jobPost);
			jobPost1.setCompany(companyRepository.findById(jobPost.getCompanyId()));
			jobPost1.setUsers(usersRepository.findById(jobPost.getRecruiter()));
			jobPost1.setJobTitle(jobTitleRepository.findById(jobPost.getJobtitleId()));
					
			
//			if(jobPost.getJobtitleId()==0 ) {
//			jobPost1.setJobTitle(jobTitleRepository.save(jobPost.getJobTitle()));
//			
//			}
			

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPost1);

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
	

	
	
	 @PostMapping("/savewithemailnotification")
	   public ResponseEntity<String> saveJobPostWithEmail(String TO, String SUBJECT, String[] mailbody,@RequestBody JobPost jobPost/*, @RequestParam( value = "companyId", required = false) long jobtitleId*/) throws JsonProcessingException {

		try {
			
			 final String FROM = "reachout@bolstart.com";
		       
		        final String FROMNAME = "Bolstart Notifications";
		        
		        //String customMailBody="hgbnm";
		        String imageTagFinalString="";
		        String customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
		        		"<html xmlns=\\\"http://www.w3.org/1999/xhtml\\\" xmlns:v=\\\"urn:schemas-microsoft-com:vml\\\" xmlns:o=\\\"urn:schemas-microsoft-com:office:office\\\">\r\n" + 
		        		" <head>\r\n" + 
		        		"   					  \r\n" + 
		        		"   					  <meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\">\r\n" + 
		        		"   					    <meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\"> \r\n" + 
		        		"   					    <meta name=\\\"x-apple-disable-message-reformatting\\\">\r\n" + 
		        		"   					    <!--[if !mso]><!--><meta http-equiv=\\\"X-UA-Compatible\\\" content=\\\"IE=edge\\\"><!--<![endif]--> \r\n" + 
		        		"   					    <title>Bolstart</title>\r\n" + 
		        		"   					   \r\n" + 
		        		"   					      <style type=\\\"text/css\\\">\r\n" + 
		        		"   					        table, td { color: #000000; } a { color: #0000ee; text-decoration: underline; } @media (max-width: 480px) { #u_content_text_2 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_5 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_4 .v-container-padding-padding { padding: 10px 10px 0px !important; } #u_content_text_3 .v-container-padding-padding { padding: 10px 10px 0px !important; } } \r\n" + 
		        		"   					  \"@media only screen and (min-width: 620px) {\r\n" + 
		        		"   					    .u-row {\r\n" + 
		        		"   					      width: 600px !important;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					    .u-row .u-col { \r\n" + 
		        		"   					      vertical-align: top;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					    .u-row .u-col-25 {\r\n" + 
		        		"   					      width: 230px !important;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					    .u-row .u-col-33p33 { \r\n" + 
		        		"   					      width: 199.98px !important; \r\n" + 
		        		"   					    }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					    .u-row .u-col-50 {\r\n" + 
		        		"   					      width: 300px !important;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					    .u-row .u-col-66p67 {\r\n" + 
		        		"   					      width: 400.02px !important;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					    .u-row .u-col-100 { \r\n" + 
		        		"   					      width: 600px !important;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					  }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					  @media (max-width: 620px) {\r\n" + 
		        		"   					    .u-row-container { \r\n" + 
		        		"   					      max-width: 100% !important; \r\n" + 
		        		"   					      padding-left: 0px !important;\r\n" + 
		        		"   					      padding-right: 0px !important;\r\n" + 
		        		"   					    } \r\n" + 
		        		"   					    .u-row .u-col {\r\n" + 
		        		"   					      min-width: 320px !important; \r\n" + 
		        		"   					      max-width: 100% !important; \r\n" + 
		        		"   					      display: block !important;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					    .u-row {\r\n" + 
		        		"   					      width: calc(100% - 40px) !important; \r\n" + 
		        		"   					    }\r\n" + 
		        		"   					    .u-col {\r\n" + 
		        		"   					      width: 100% !important;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					    .u-col > div {\r\n" + 
		        		"   					      margin: 0 auto;\r\n" + 
		        		"   					    }\r\n" + 
		        		"   					  } \r\n" + 
		        		"   					  body {\r\n" + 
		        		"   					    margin: 0;\r\n" + 
		        		"   					    padding: 0;\r\n" + 
		        		"   					  }\r\n" + 
		        		"   					   \r\n" + 
		        		"   					  table, \r\n" + 
		        		"   					  tr, \r\n" + 
		        		"   					  td { \r\n" + 
		        		"   					    vertical-align: top; \r\n" + 
		        		"   					    border-collapse: collapse; \r\n" + 
		        		"   					  } \r\n" + 
		        		"   					   \r\n" + 
		        		"   					  p { \r\n" + 
		        		"   					    margin: 0;\r\n" + 
		        		"   					  } \r\n" + 
		        		"   					   \r\n" + 
		        		"   					  .ie-container table, \r\n" + 
		        		"   					  .mso-container table { \r\n" + 
		        		"   					    table-layout: fixed; \r\n" + 
		        		"   					  }\r\n" + 
		        		"					  * { \r\n" + 
		        		"   					    line-height: inherit; \r\n" + 
		        		"   					  } \r\n" + 
		        		"   					  a[x-apple-data-detectors='true'] { \r\n" + 
		        		"   					    color: inherit !important; \r\n" + 
		        		"   					    text-decoration: none !important; \r\n" + 
		        		"   					  }\r\n" + 
		        		"					  @media (max-width: 480px) { \r\n" + 
		        		"   					    .hide-desktop {\r\n" + 
		        		"   					      display: block !important; \r\n" + 
		        		"   					    }\r\n" + 
		        		"						table.hide-desktop {\r\n" + 
		        		"   					      display: table !important; \r\n" + 
		        		"   					    } \r\n" + 
		        		"   					  }\r\n" + 
		        		"   					      \r\n" + 
		        		"						  </style>\r\n" + 
		        		"						  </head> \r\n" + 
		        		"						  <body>" + 
		        		"						<h4>Dear Candidate "+",</h4>" + 
		        		
		        		"                         <p>Thank you for requesting your company page for verification on Bolstart." + 
		        		"						 Usually we respond in 5-7 working business days to connect with you to verify your company." + 
		        		"						 Thank you so much for your patience and cooperation.<br><br>" + 
		        		"                        If you have any other questions or concerns please feel free to <a href=\"mailto:reachout@bolstart.com\">ReachOut</a> to us.</p>" + 
		        		
		        		"                       <p>Regards,<br>" + 
		     
		        		"                       Bolstart Team</p>" + 
		        		
		        		"						  </body>" + 
		        		"</html>";
			   
		      
		        	
		        
		        final String BODY = StringUtils.join( new String[] {
		            	customMailBody
		            		
		            });
			
			JobPost jobPost1 = jobPostRepository.save(jobPost);
			jobPost1.setCompany(companyRepository.findById(jobPost.getCompanyId()));
			jobPost1.setUsers(usersRepository.findById(jobPost.getRecruiter()));
			
			if(jobPost.getJobtitleId()==0 ) {
			jobPost1.setJobTitle(jobTitleRepository.save(jobPost.getJobTitle()));
			
			}
			
			
			final String SMTP_USERNAME = "AKIATVIMWQHEQHP2XQCX";
	        final String SMTP_PASSWORD = "BEKt5Kk+nrG+88jLAlIb6efRLiabeqG8wV7X9ok2TTiD";
	        final String HOST = "email-smtp.ap-south-1.amazonaws.com";
	        final int PORT = 587;
	   	Properties props = System.getProperties();
	   	props.put("mail.transport.protocol", "smtp");
	   	props.put("mail.smtp.port", PORT); 
	   	props.put("mail.smtp.starttls.enable", "true");
	   	props.put("mail.smtp.auth", "true");
	   	Session session = Session.getDefaultInstance(props);
	       MimeMessage msg = new MimeMessage(session);
	       msg.setFrom(new InternetAddress(FROM,FROMNAME));
	       msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
	       msg.setSubject(SUBJECT);
	       msg.setContent(/*StringEscapeUtils.unescapeJava(*/BODY, "text/html; charset=UTF-8");
	       Transport transport = session.getTransport();
	       
	       
	       
	       
	       
	       try
	       {
	           transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
	           transport.sendMessage(msg, msg.getAllRecipients());
	       }
	       catch (Exception ex) {
	    	   ex.printStackTrace();
	           System.out.println("The email was not sent.");
	           System.out.println("Error message: " + ex.getMessage());
	           
	       }
	       finally
	       {
	           transport.close();
	       } 
	       System.out.println("Mail Send Successfully");  
	 	

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPost1);

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
	 
	 
	 
	 
	 

	



	@PutMapping("/jobpost/update")
	public ResponseEntity<String> updateJobPost(@RequestBody JobPost jobPostEntity) throws JsonProcessingException {

		try {
			JobPost jobPost = jobPostRepository.findOne(jobPostEntity.getId());

			if (jobPostEntity.getCompanyId() != 0) {
				jobPost.setCompanyId(jobPostEntity.getCompanyId());
			}

			if (jobPostEntity.getRecruiter() != 0) {
				jobPost.setRecruiter(jobPostEntity.getRecruiter());
			}

			if (jobPostEntity.getTypeofJob() != null) {
				jobPost.setTypeofJob(jobPostEntity.getTypeofJob());
			}

			if (jobPostEntity.getJobDescription() != null) {
				jobPost.setJobDescription(jobPostEntity.getJobDescription());
			}

			if (jobPostEntity.getMinExperiance() != null) {
				jobPost.setMinExperiance(jobPostEntity.getMinExperiance());
			}

			if (jobPostEntity.getMaxExperiance() != null) {
				jobPost.setMaxExperiance(jobPostEntity.getMaxExperiance());
			}

			if (jobPostEntity.getBenifit() != null) {
				jobPost.setBenifit(jobPostEntity.getBenifit());
			}

			if (jobPostEntity.getStartDate() != null) {
				jobPost.setStartDate(jobPostEntity.getStartDate());
			}

			if (jobPostEntity.getDuration() != null) {
				jobPost.setDuration(jobPostEntity.getDuration());
			}

			if (jobPostEntity.getSalaryMin() != null) {
				jobPost.setSalaryMin(jobPostEntity.getSalaryMin());
			}

			if (jobPostEntity.getSalaryMax() != null) {
				jobPost.setSalaryMax(jobPostEntity.getSalaryMax());
			}

			if (jobPostEntity.getLocation() != null) {
				jobPost.setLocation(jobPostEntity.getLocation());
			}

			if (jobPostEntity.getCountry() != null) {
				jobPost.setCountry(jobPostEntity.getCountry());
			}

			if (jobPostEntity.getSkills() != null) {
				jobPost.setSkills(jobPostEntity.getSkills());
			}

			if (jobPostEntity.getNoticePeriod() != null) {
				jobPost.setNoticePeriod(jobPostEntity.getNoticePeriod());
			}
			if (jobPostEntity.getCity() != null) {
				jobPost.setCity(jobPostEntity.getCity());
			}

			if (jobPostEntity.getState() != null) {
				jobPost.setState(jobPostEntity.getState());
			}

			if (jobPostEntity.getCreated() != null) {
				jobPost.setCreated(jobPostEntity.getCreated());
			}

			if (jobPostEntity.getCreatedBy() != null) {
				jobPost.setCreatedBy(jobPostEntity.getCreatedBy());
			}

			if (jobPostEntity.getUpdated() != null) {
				jobPost.setUpdated(jobPostEntity.getUpdated());
			}

			if (jobPostEntity.getUpdatedBy() != null) {
				jobPost.setUpdatedBy(jobPostEntity.getUpdatedBy());
			}

			if (jobPostEntity.getActive() != null) {
				jobPost.setActive(jobPostEntity.getActive());
			}

			if (jobPostEntity.getJobCategeory() != null) {
				jobPost.setJobCategeory(jobPostEntity.getJobCategeory());
			}

			if (jobPostEntity.getQualification() != null) {
				jobPost.setQualification(jobPostEntity.getQualification());
			}

			if (jobPostEntity.getRequirements() != null) {
				jobPost.setRequirements(jobPostEntity.getRequirements());
			}

			if (jobPostEntity.getBond() != null) {
				jobPost.setBond(jobPostEntity.getBond());
			}

			if (jobPostEntity.getIsResumeRequired() != null) {
				jobPost.setIsResumeRequired(jobPostEntity.getIsResumeRequired());
			}

			if (jobPostEntity.getIsAssissmentRequired() != null) {
				jobPost.setIsAssissmentRequired(jobPostEntity.getIsAssissmentRequired());
			}

			if (jobPostEntity.getAssissmentId() != null) {
				jobPost.setAssissmentId(jobPostEntity.getAssissmentId());
			}
			if (jobPostEntity.getNoOfApplication() != null) {
				jobPost.setNoOfApplication(jobPostEntity.getNoOfApplication());
			}
			if (jobPostEntity.getStatus() != null) {
				jobPost.setStatus(jobPostEntity.getStatus());
			}

			JobPost jobPost1 = jobPostRepository.save(jobPost);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data Updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPost1);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			// TODO: handle exception

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Unable to Update Data");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData(jobPostEntity);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}

	@DeleteMapping("/deleteById")
	public ResponseEntity<String> deleteJobPostById(@RequestParam("id") Long id) throws JsonProcessingException {

		try {
			JobPost JobPostEntity = jobPostRepository.findOne(id);
			if (JobPostEntity != null) {
				jobPostRepository.delete(id);

				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("Data Deleted successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData(JobPostEntity);

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);

			}

			else {

				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("Unable to Delete Data");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);

			}

		} catch (Exception e) {
			// TODO: handle exception

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Unable to Delete Data");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}

	@GetMapping("/getbycompanyid")
	public ResponseEntity<String> getByCompanyId(@RequestParam("id") long companyId) throws JsonProcessingException {

		try {

			List<JobPost> jobPostEntity = jobPostRepository.findByCompanyId(companyId);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPostEntity);

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

	@GetMapping("/getbyskills")
	public ResponseEntity<String> getByskills(@RequestParam String skill) throws JsonProcessingException {

		try {

			List<JobPost> jobPostEntity = jobPostRepository.findBySkills(skill);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPostEntity);

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

	@GetMapping("/getbynoticeperiod")
	public ResponseEntity<String> getByNoticePeriod(@RequestParam String noticePeriod) throws JsonProcessingException {

		try {

			List<JobPost> jobPostEntity = jobPostRepository.findByNoticePeriod(noticePeriod);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPostEntity);

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

	@GetMapping("/getbylocation")
	public ResponseEntity<String> getByLocation(@RequestParam String location) throws JsonProcessingException {

		try {

			List<JobPost> jobPostEntity = jobPostRepository.findByLocation(location);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPostEntity);

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

	@GetMapping("/getbyminexp")
	public ResponseEntity<String> getByMinExp(@RequestParam String minExperiance) throws JsonProcessingException {

		try {
			// jobPost1.setCompany(companyRepository.findById(jobPost.getCompanyId()));
			List<JobPost> jobPostEntity = jobPostRepository.findByMinExperiance(minExperiance);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPostEntity);

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
	
	@GetMapping("/getStartdate")
	public ResponseEntity<String> getByStartDate(@RequestParam String startDate) throws JsonProcessingException {

		try {
			
			List<JobPost> jobPostEntity = jobPostRepository.findByStartDate(startDate);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(jobPostEntity);

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


	/*
	 * @PostMapping("/filter") public ResponseEntity<String>
	 * getByFilter(@RequestBody Map<String, String> JobPostEntity) throws
	 * JsonProcessingException { List<JobPost> jobPost =
	 * this.jobPostRepository.findAll(); List<JobPost> fetchedjobpost = new
	 * ArrayList<>();
	 * 
	 * 
	 * for(int i=0; i<fetchedjobpost.size(); i++){ if(Skills!=null){ If(!
	 * List.get(i).equals(Skills)){ List.remove(i); } }
	 * 
	 * If(minExperiance!=null){
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
	 * 
	 * jsonobjectFormat.setMessage("Data fetch successfully");
	 * jsonobjectFormat.setSuccess(true); jsonobjectFormat.setData(jobPost);
	 * 
	 * ObjectMapper Obj = new ObjectMapper(); String customExceptionStr =
	 * Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 * return ResponseEntity.ok().body(customExceptionStr); }
	 */
	// @RequestParam(value="nameOfPerson",required=false) String nameOfPerson
	@GetMapping("/filterAPI")
	List<JobPost> filterAPI(@RequestParam(value = "skills", required = false) String skills,
			@RequestParam(value = "minExperiance", required = false) String minExperiance,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "noticePeriod", required = false) String noticePeriod,
			@RequestParam(value = "companyId", required = false) Long companyId) {

		List<JobPost> list = jobPostRepository.findAll();
		ArrayList<JobPost> newList = new ArrayList<JobPost>();

		for (int i = 0; i < list.size(); i++) {
			JobPost jobPost = list.get(i);
			if (skills != null) {
				if (!(jobPost.getSkills().equals(skills))) {
					newList.add(list.get(i));
				}
			}

			if (minExperiance != null) {
				if (!(jobPost.getMinExperiance().equals(minExperiance))) {
					newList.add(list.get(i));
				}
			}
			if (location != null) {
				if (!(jobPost.getLocation().equals(location))) {
					newList.add(list.get(i));
				}
			}
			if (noticePeriod != null) {
				if (!(jobPost.getNoticePeriod().equals(noticePeriod))) {
					newList.add(list.get(i));
				}
			}
			if (companyId != null) {
				if (!(jobPost.getCompanyId() == companyId)) {
					newList.add(list.get(i));
				}
			}
		}

		// List<JobPost> listNew = jobPostRepository.findAll();
		// listNew.addAll(list);
		for (int i = 0; i < newList.size(); i++) {
			list.remove(newList.get(i));
		}

		HashSet<JobPost> set = new HashSet<>();
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i));
			// listNew.remove(newList.get(i));
		}
		ArrayList<JobPost> list1 = new ArrayList<>(set);

		System.out.println(list1);
		// return listNew;
		return list1;

	}

	@GetMapping("/getbycompanyname")
	public ResponseEntity<String> getByCompanyName(/*@RequestParam(value = "companyId", required = false) Long companyId,*/
			@RequestParam("name") String name) throws JsonProcessingException {

		try {
//					JobPost jobPost1	= jobPostRepository.save(jobPost);
//					jobPost1.setCompany(companyRepository.findById(jobPost.getCompanyId()));

			List<JobPost> fetchedCompaniesById = new ArrayList<>();

			List<Company> companyDetail = companyRepository.findByName(name);

			companyDetail
					.forEach(val -> fetchedCompaniesById.addAll(this.jobPostRepository.findByCompanyId(val.getId())));

//					JobPost jobPost= new JobPost();

			// List<Company> company =
			// JobPostRepository.findByCompanyName(getByCompanyName(companyId, name));

			// jobPost1.setCompany(companyRepository.findByName(name));

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Data fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(fetchedCompaniesById);

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
