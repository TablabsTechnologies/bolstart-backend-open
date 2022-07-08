package com.bol.core.controller;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.domain.JsonObjectFormatPage;
import com.bol.core.dto.AddressBook;
import com.bol.core.dto.AddressBookMemberEntity;
import com.bol.core.dto.Chart;
import com.bol.core.dto.Chart_Response;
import com.bol.core.dto.CommentOnPublish;
//import com.bol.core.dto.CommentOnPublish;
import com.bol.core.dto.Company;
import com.bol.core.dto.Connections;
import com.bol.core.dto.LikeDislikeOnPublish;
import com.bol.core.dto.LikeObj;
import com.bol.core.dto.LikeOnPublishComment;
import com.bol.core.dto.Publish;
import com.bol.core.dto.Users;
import com.bol.core.exception.CustomParameterizedException;
import com.bol.core.repository.AddressBookMemberRepository;
import com.bol.core.repository.AddressBookRepository;
import com.bol.core.repository.CommentOnPublishRepository;
import com.bol.core.repository.CompanyRepository;
import com.bol.core.repository.ConnectionsRepository;
import com.bol.core.repository.LikeDislikeOnPublishRepository;
import com.bol.core.repository.LikeOnPublishCommentRepository;
import com.bol.core.repository.PublishRepository;
import com.bol.core.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Publish controller",description = "used to share a Publish post,add comment on post and like dislike ")
@RequestMapping("/api")
public class PublishController {
	
	private final Logger log = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	PublishRepository publishRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	CommentOnPublishRepository commentOnPublishRepository;
	
	@Autowired
	LikeOnPublishCommentRepository likeOnPublishCommentRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	AddressBookMemberRepository addressBookMemberRepository;
	
	@Autowired
	ConnectionsRepository connectionsRepository;
	
	@Autowired
	LikeDislikeOnPublishRepository likeDislikeOnPublishRepository;
	
	@Autowired
	AddressBookRepository addressBookRepository;
	
   @PostMapping("/publish/savePublishPost")
   ResponseEntity<String> savePublishPost(@RequestBody Publish publishEntity) throws JsonProcessingException, ParseException{
	   
//	   try {
		   
		    Date date=new Date();
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			String strdate=dateFormat.format(date);
			SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
			String time=timeFormat.format(date);
		    
			publishEntity.setDate(strdate);
			publishEntity.setTime(time);
		    
			Users user=null;
			Company companyObj=null;
			
	
			if(publishEntity.getCompanyId()!=null){
			 companyObj=companyRepository.findOne(publishEntity.getCompanyId());
			} 
			if(publishEntity.getUserId()!=null) {
				user=usersRepository.findById(publishEntity.getUserId());
				}
			
			if(publishEntity.getCompanyId()!=null )
			{	
			   if(publishEntity.getUserId()!=null) {
				publishEntity.setUserName(user.getFirstName()+" "+user.getLastName()+" for "+companyObj.getName());
			   }else {
				   publishEntity.setUserName(companyObj.getName());
				   publishEntity.setProfilePicture(companyObj.getLogo());
			   }
			}else if(publishEntity.getUserId()!=null) {
				publishEntity.setUserName(user.getFirstName()+" "+user.getLastName());
			}
			
			
			
			String chart_url="";
			if(publishEntity!=null && publishEntity.getChartUrls().size()!=0) {
				
				for(Chart_Response chartJson:publishEntity.getChartUrls()) {
					
					Chart chartObj=new Chart();

					chart_url=chart_url+chartJson.getChartUrl().trim()+",";
					
				}
				
			}
			
			String strChart_url="";
			
			if(chart_url.length()!=0) {
				strChart_url=chart_url.substring(0,chart_url.length()-1);
			}
			
			
			publishEntity.setChart_url(strChart_url);
			
			Publish publish=publishRepository.save(publishEntity);
			
			String mailbody[]= {publishEntity.getDescription(),publishEntity.getImage()};
			
			
			if(publish!=null)
			{
				if(publish.getCompanyId()!=null) {
				publish.setProfilePicture(companyObj.getLogo());
				}else if(publish.getUserId()!=null) {
				publish.setProfilePicture(user.getProfilePicture());
				}

				
				if(user!=null && user.getHeadline()!=null) {
					publish.setTitle(user.getHeadline());

				}
		    if(publish.getDescription()!=null) {
		    	 if(publish.getCompanyId()!=null) {
		    		 
		    	 }else if(publish.getUserId()!=null) {
//		    	 message="Hello "+user.getFirstName()+" "+user.getLastName()+"\n\n"+publish.getDescription()+"\n\n";
		    	 }
		    } 
//			String emailIds[]=publishEntity.getEmailId().split(",");	
			
			String subject="";
			
//			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//			
//			Date date1 = formatter.parse(publish.getDate());
//			
//			String date2=formatter.format(date1);  
			
			
			String start_dt = publish.getDate();
			 SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			    SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
			    Date date123 = format1.parse(start_dt);
			    System.out.println(format2.format(date123));
		    
		    
			if(companyObj!=null) {
				subject=companyObj.getName()+" Update "+format2.format(date123);
			}else if(user!=null) {
				subject=user.getFirstName()+" "+user.getLastName()+" Update "+format2.format(date123);
			}
			
//			String message="\n\n"+publish.getUserName()+"\n\n"+publish.getProfilePicture();
			
			List<AddressBookMemberEntity> addressBookMember=addressBookMemberRepository.getByGroupId(publish.getGroupId());
			
			String userfullName=user.getFirstName()+" "+user.getLastName();
			for(AddressBookMemberEntity members:addressBookMember) {
				publicPostEmailNew(members.getEmailid(),subject,members.getFirstName(),mailbody,userfullName,members.getId(),publish.getChartUrls(),companyObj,user);
			}	
				
				
		    JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			jsonobjectFormat.setMessage("Data Save successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(publish);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 	 
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				jsonobjectFormat.setMessage("Unable to save Publish Post");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData(publishEntity);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
		
//	} catch (Exception e) {
//		// TODO: handle exception
//		
//		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//		 
//			jsonobjectFormat.setMessage("Unable to Save Data");
//			jsonobjectFormat.setSuccess(false);
//			jsonobjectFormat.setData("");
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//	}
	   
	   
   }
   
//   @PutMapping("/publish/updatePublistPost")
//   ResponseEntity<String> updatePublishPost(@RequestBody Publish publishEntity) throws JsonProcessingException{
//	   
//	   try {
//		   
//		   Publish publishObj=publishRepository.findOne(publishEntity.getId());
//		   
//		   publishObj.setDate(publishEntity.getDate());
//		   publishObj.setDescription(publishEntity.getDescription());
//		   publishObj.setEmailId(publishEntity.getEmailId());
//		   publishObj.setImage(publishEntity.getImage());
//		   publishObj.setIsDislikedByUser(publishEntity.getIsDislikedByUser());
//		   publishObj.setIsLikedByUser(publishEntity.getIsLikedByUser());
//		   publishObj.setNoOfDislikes(publishEntity.getNoOfDislikes());
//		   publishObj.setNoOfLikes(publishEntity.getNoOfLikes());
//		   publishObj.setProfilePicture(publishEntity.getProfilePicture());
//		   publishObj.setTime(publishEntity.getTime());
//		   publishObj.setTitle(publishEntity.getTitle());
//		   publishObj.setUserId(publishEntity.getUserId());
//		   publishObj.setUserName(publishEntity.getUserName());
//		   
//		   publishRepository.save(publishObj);
//		   
//		   JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			jsonobjectFormat.setMessage("Data Updated successfully");
//			jsonobjectFormat.setSuccess(true);
//			jsonobjectFormat.setData(publishEntity);
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//		
//	} catch (Exception e) {
//		// TODO: handle exception
//		
//		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//		 
//			jsonobjectFormat.setMessage("Unable to Update Data");
//			jsonobjectFormat.setSuccess(false);
//			jsonobjectFormat.setData("");
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//	}
//	   
//	   
//   }
   
   
   
   
//   @DeleteMapping("/publish/deletePublishPostById")
//   ResponseEntity<String> deletePublishPostById(@RequestParam("id") Long id) throws JsonProcessingException{
//	   
//	   try {
//		   
//		   Publish publishList=publishRepository.findOne(id);
//		   
//		   if(publishList!=null) {
//		  
//			   publishRepository.delete(id); 
//			   
//		   JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			jsonobjectFormat.setMessage("Data Deleted successfully");
//			jsonobjectFormat.setSuccess(true);
//			jsonobjectFormat.setData(publishList);
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//		   }else {
//			   
//			   JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//				 
//				jsonobjectFormat.setMessage("Unable to Delete Data");
//				jsonobjectFormat.setSuccess(false);
//				jsonobjectFormat.setData("");
//		         
//				 ObjectMapper Obj = new ObjectMapper(); 
//		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//			 	 return ResponseEntity.ok().body(customExceptionStr);
//			   
//		   }
//		
//	} catch (Exception e) {
//		// TODO: handle exception
//		
//		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//		 
//			jsonobjectFormat.setMessage("Unable to Delete Data");
//			jsonobjectFormat.setSuccess(false);
//			jsonobjectFormat.setData("");
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//	}
//	   
//	   
//   }
	

   void publicPostEmail(String TO, String SUBJECT,String memberFirstName, String[] mailbody,String ownerName,Long memberId, ArrayList<Chart_Response> chart, Company companyObj, Users user) {
	   
	 try {  
	// Replace sender@example.com with your "From" address.
       // This address must be verified.
       final String FROM = "reachout@bolstart.com";
       
        final String FROMNAME = "Bolstart Notifications";
	    
        String customMailBody="";
        
        System.out.println("chartURLList "+chart.size());
        String preTagStyle="style=\"font-family:Tahoma;\"";
        ArrayList<String> imageTagArrayList=new ArrayList<String>();
        if(chart!=null && chart.size()!=0) {
        	System.out.println(" Inside 1");
        for(Chart_Response chartUrl:chart) {
        	System.out.println(" Inside 2");
        	 String imageTagString="<img src=\""+chartUrl.getChartUrl()+"\""+"width=400px height=400px/><br>";
        	 imageTagArrayList.add(imageTagString);
        }
        }
        String imageTagFinalString="";
        if(imageTagArrayList!=null && imageTagArrayList.size()!=0) {
        	System.out.println(" Inside 3");
         for(String str1:imageTagArrayList) {	
        	 System.out.println(" Inside 4");
        	 imageTagFinalString=imageTagFinalString+str1;
         }
        }
        
        if(imageTagFinalString.length()>2) {
        	System.out.println(" Inside 5");
            if(mailbody[1]==null ) {
                customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
               		"						<p>Hello "+memberFirstName+",</p>" +  
               		"                          <span>"+mailbody[0]+"</span><br>\r\n" + 
               		"<br>"+imageTagFinalString+
               		"                          <p>Thank you,<br>"+ownerName+"</p>\r\n" + 
               		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+
               		"						  </body\r\n" + 
               		"						  \r\n" + 
               		"</html>";
               }else if(mailbody[1]!=null){
               	
               	 customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
               	 		"						  <body>\r\n" +  
               	 		"						<p>Hello "+memberFirstName+",</p>" + 
               	 		
               	 		
               	 		"                           <span>"+mailbody[0]+"</span><br>" + 
               	 		"                          <img src="+mailbody[1]+" width=200px height=200px /><br>" + 
               	 		"                          "+imageTagFinalString+""+
               	 		"                          <p>Thank you<br>"+ownerName+"</p>" + 
               	 		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+

               	 		"						  </body>" + 
               	 		"</html>";
               	
               	
               }
        	
        }else {
        
        	System.out.println(" Inside 6");
        
        if(mailbody[1]==null ) {
         customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
        		"						<h4>Hello "+memberFirstName+",</h4>" +  
        		"                            <span>"+mailbody[0]+"</span><br>\r\n" + 
        		"<br>"+
        		"                          <p>Thank you,<br>"+ownerName+"</p>\r\n" + 
        		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+
        		"						  </body\r\n" + 
        		"						  \r\n" + 
        		"</html>";
        }else if(mailbody[1]!=null){
        	
        	
        	 customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
        	 		"						  <body>\r\n" +  
        	 		"						<h4>Hello "+memberFirstName+",</h4>" + 
        	 		
        	 		"                            <span>"+mailbody[0]+"</span><br>" + 
        	 		"                          <img src="+mailbody[1]+" width=200px height=200px />" + 
        	 		
        	 		"						 \r\n" + 
        	 		"                          <p>Thank you<br>"+ownerName+"</p>" + 
        	 		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+

        	 		"						  </body>" + 
        	 		"</html>";
        	
        	
        }
        }
        
        String comapnyLogo="\""+companyObj.getLogo()+"\"";
        
        String userProfileImage="\""+user.getProfilePicture()+"\"";
        
        System.out.println(" comapnyLogo "+comapnyLogo);
        

        
        final String BODY = StringUtils.join( new String[] {
        	customMailBody
        		
        });
        
        // Replace recipient@example.com with a "To" address. If your account 
        // is still in the sandbox, this address must be verified.
        // final String TO = "sachinparmar.7393@gmail.com";
        
        // Replace smtp_username with your Amazon SES SMTP user name.
         final String SMTP_USERNAME = "AKIATVIMWQHEQHP2XQCX";
        
        // Replace smtp_password with your Amazon SES SMTP password.
         final String SMTP_PASSWORD = "BEKt5Kk+nrG+88jLAlIb6efRLiabeqG8wV7X9ok2TTiD";
        
        // The name of the Configuration Set to use for this message.
        // If you comment out or remove this variable, you will also need to
        // comment out or remove the header below.
       // static final String CONFIGSET = "ConfigSet";
        
        // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
        // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
        // for more information.
         final String HOST = "email-smtp.ap-south-1.amazonaws.com";
        
        // The port you will connect to on the Amazon SES SMTP endpoint. 
         final int PORT = 587;
        
        //final String SUBJECT = "Jai Shree Krishna Amazon SES test (SMTP interface accessed using Java)";
        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(StringEscapeUtils.unescapeJava(BODY), "text/html; charset=UTF-8");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
     //   msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
           
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        } 
        
        
      System.out.println("Mail Send Successfully");  
	 }catch (Exception e) {
		// TODO: handle exception
		
		 
		 
	}
   }

   void publicPostEmailNew(String TO, String SUBJECT,String memberFirstName, String[] mailbody,String ownerName,Long memberId, ArrayList<Chart_Response> chart, Company companyObj, Users user) {
	   
		 try {  
	       final String FROM = "reachout@bolstart.com";
	        final String FROMNAME = "Bolstart Notifications";
	        String customMailBody="";
	        ArrayList<String> imageTagArrayList=new ArrayList<String>();
	        if(chart!=null && chart.size()!=0) {
	        for(Chart_Response chartUrl:chart) {
	        	 String imageTagString="<img src=\""+chartUrl.getChartUrl()+"\""+"width=400px height=400px/><br>";
	        	 imageTagArrayList.add(imageTagString);
	        }
	        }
	       
	        String imageTagFinalString="";
	        if(imageTagArrayList!=null && imageTagArrayList.size()!=0) {
	        	System.out.println(" Inside 3");
	         for(String str1:imageTagArrayList) {	
	        	 System.out.println(" Inside 4");
	        	 imageTagFinalString=imageTagFinalString+str1;
	         }
	        }
	        
	        
	        if(mailbody.length!=0){
	        	String temp=mailbody[0];
	        	mailbody[0] = temp.replaceAll("(\r\n|\n)", "<br />");
	        	
	        }
	        
	        if(imageTagFinalString.length()>2) {
	        	System.out.println(" Inside 5");
	            if(mailbody[1]==null ) {
	                customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
	               		"						<p>Hello "+memberFirstName+",</p>" +  
	               		"                          <span>"+mailbody[0]+"</span><br>\r\n" + 
	               		"<br>"+imageTagFinalString+
	               		"                          <p>Thank you,<br>"+ownerName+"</p>\r\n" + 
	               		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+
	               		"						  </body\r\n" + 
	               		"						  \r\n" + 
	               		"</html>";
	               }else if(mailbody[1]!=null){
	               	
	               	 customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
	               	 		"						  <body>\r\n" +  
	               	 		"						<p>Hello "+memberFirstName+",</p>" + 
	               	 		
	               	 		
	               	 		"                           <span>"+mailbody[0]+"</span><br>" + 
	               	 		"                          <img src="+mailbody[1]+" width=200px height=200px /><br>" + 
	               	 		"                          "+imageTagFinalString+""+
	               	 		"                          <p>Thank you<br>"+ownerName+"</p>" + 
	               	 		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+

	               	 		"						  </body>" + 
	               	 		"</html>";
	               	
	               	
	               }
	        	
	        }else {
	        
	        	System.out.println(" Inside 6");
	        
	        if(mailbody[1]==null ) {
	         customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
	        		"						<h4>Hello "+memberFirstName+",</h4>" +  
	        		"                            <span>"+mailbody[0]+"</span><br>\r\n" + 
	        		"<br>"+
	        		"                          <p>Thank you,<br>"+ownerName+"</p>\r\n" + 
	        		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+
	        		"						  </body\r\n" + 
	        		"						  \r\n" + 
	        		"</html>";
	        }else if(mailbody[1]!=null){
	        	
	        	
	        	 customMailBody="<!DOCTYPE HTML PUBLIC \\\"-//W3C//DTD XHTML 1.0 Transitional //EN\\\" \\\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\\\">\r\n" + 
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
	        	 		"						  <body>\r\n" +  
	        	 		"						<h4>Hello "+memberFirstName+",</h4>" + 
	        	 		
	        	 		"                            <span>"+mailbody[0]+"</span><br>" + 
	        	 		"                          <img src="+mailbody[1]+" width=200px height=200px />" + 
	        	 		
	        	 		"						 \r\n" + 
	        	 		"                          <p>Thank you<br>"+ownerName+"</p>" + 
	        	 		"<h5>This email was sent to you because "+ownerName+" added you to their address book on Bolstart. If you wish to <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>, click here.</h5>"+

	        	 		"						  </body>" + 
	        	 		"</html>";
	        	
	        	
	        }
	        }
	        String comapnyLogo="\""+companyObj.getLogo()+"\"";
	        
	        String userProfileImage="\""+user.getProfilePicture()+"\"";
	        
	        final String BODY = StringUtils.join( new String[] {
	        	customMailBody
	        		
	        });
	        
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
	        msg.setContent(StringEscapeUtils.unescapeJava(BODY), "text/html; charset=UTF-8");
	        Transport transport = session.getTransport();
	        try
	        {
	            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
	            transport.sendMessage(msg, msg.getAllRecipients());
	        }
	        catch (Exception ex) {
	            System.out.println("The email was not sent.");
	            System.out.println("Error message: " + ex.getMessage());
	            
	        }
	        finally
	        {
	            transport.close();
	        } 
		 }catch (Exception e) {
		}
	   }
   

//	@PostMapping("/publish/comment")
//	public ResponseEntity<String> commentOnPublish(@RequestBody CommentOnPublish reqComment)
//			throws JsonProcessingException {
//		log.debug("REST request to comment on publish : {}", reqComment);
//
//		Date date = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String strdate = dateFormat.format(date);
//		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
//		String time = timeFormat.format(date);
//		reqComment.setDate(strdate);
//		reqComment.setTime(time)
//;
//		Users user = usersRepository.findById(reqComment.getUserId());
//
//		if (user == null) {
//			CustomParameterizedException customExp = new CustomParameterizedException("user not available", "500");
//			ObjectMapper obj = new ObjectMapper();
//			String customExr = obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
//			return ResponseEntity.ok().body(customExr);
//		}
//
//		Publish publish = publishRepository.findOne(reqComment.getPublishId());
//		if (publish == null) {
//			CustomParameterizedException customExp = new CustomParameterizedException("publish not available", "500");
//			ObjectMapper obj = new ObjectMapper();
//			String customExr = obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
//			return ResponseEntity.ok().body(customExr);
//		}
//		CommentOnPublish comment = commentOnPublishRepository.save(reqComment);
//		if (comment != null) {
//			comment.setUsername(user.getFirstName() + " " + user.getLastName());
//			comment.setProfilePicture(user.getProfilePicture());
//			if (user.getHeadline() != null)
//				comment.setTitle(user.getHeadline());
//
//			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
//			jsonobjectFormat.setMessage("comment on publish successfully");
//			jsonobjectFormat.setSuccess(true);
//			jsonobjectFormat.setData(comment);
//			ObjectMapper Obj = new ObjectMapper();
//			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//			return ResponseEntity.ok().body(customExceptionStr);
//		} else {
//			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
//			jsonobjectFormat.setMessage("unable to comment on publish ");
//			jsonobjectFormat.setSuccess(false);
//
//			ObjectMapper Obj = new ObjectMapper();
//			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//			return ResponseEntity.ok().body(customExceptionStr);
//		}
//
//	}
//	@GetMapping("/publish/comments")
//	public ResponseEntity<String> getAllCommentsByPublish(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId,@RequestParam("publishId")Long publishId) throws JsonProcessingException {
//		Pageable pageable =new PageRequest(page, size); 
//		if(page==null){
//		 		page=0;
//				 }else{
//					 
//				 }
//		if(size==null){
//			 size=10;
//		 }else{
//			size=size+size*page;
//		 }
//	
//	 
//		
//		Pageable pageab =new PageRequest(page, size); 
//		List<CommentOnPublish> commentOnPublish=commentOnPublishRepository.findByPublishId(publishId);
//							
//	
//		if(commentOnPublish!=null && !commentOnPublish.isEmpty())
//		{
//		 for(CommentOnPublish comment:commentOnPublish)
//		 {
//							 
//				LikeOnPublishComment likeOnComment= likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), userId);
//			    if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
//				{
//					comment.setIsLikedByUser(likeOnComment.getIsLiked());
//				}
//							 
//				Users comUser=usersRepository.findById(comment.getUserId());
//				comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
//				comment.setProfilePicture(comUser.getProfilePicture());
//				/*
//				 * List<Experience> comExp= ExperienceRepository.findByUserId(comUser.getId());
//				 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
//				 * comment.setTitle(comExp.get(0).getTitle()); }
//				 */
//				 if(comUser.getHeadline()!=null)
//					 comment.setTitle(comUser.getHeadline());
//		  }
//		 Page<CommentOnPublish> publish=new PageImpl<>(commentOnPublish, pageable,commentOnPublish.size());
//		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//		 jsonobjectFormat.setMessage("got all comments on publish succeessfully");
//		 jsonobjectFormat.setSuccess(true);
//		 jsonobjectFormat.setData(publish);
//        ObjectMapper Obj = new ObjectMapper(); 
//        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//	 	 return ResponseEntity.ok().body(customExceptionStr);
//			
//		}else {
//			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 jsonobjectFormat.setMessage("no comments found");
//			 jsonobjectFormat.setSuccess(false);
//		
//	         ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//		 }
//	}
   
	@GetMapping("/publish/publishPost/byUserId")
	ResponseEntity<String> getPublishPostByUser(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException, MalformedURLException{
		
		try {

			if(page==null) {
				page=0;
			}else {
				
			}if(size==null)
			{
				size=10;
			}else {
				
			}
			Pageable pageable =new PageRequest(page, size);
			
				size=size+size*page;
		
			Pageable comPage=new PageRequest(0, 2);
			
			List<Publish> publishPost=publishRepository.findByUserId(userId);
			if(publishPost!=null && publishPost.size()!=0) {
				for(int j=0;j<publishPost.size();j++) {
				
				     String chartUrl[]=publishPost.get(j).getChart_url().split("},");
				     
				     if(chartUrl.length!=0) {
				    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
				    	 
				    	 
				    	 
				    	 for(int i=0;i<chartUrl.length;i++) {
				    		 if(chartUrl[i]!=null) {
				    			 if(chartUrl.length-1==i) {
					    			 Chart_Response chartResponse=new Chart_Response();
					    			 chartResponse.setChartUrl(chartUrl[i]+"");
					    			 System.out.println(" If Part "+" i "+i+" "+" chartUrl[i] "+chartUrl[i]);
					    			 url.add(chartResponse);
					    			 }else {
					    				 
					    				 Chart_Response chartResponse1=new Chart_Response();
						    			 chartResponse1.setChartUrl(chartUrl[i]+"}");
						    			 System.out.println(" else Part "+" i "+i+" "+" chartUrl[i] "+chartUrl[i]);
						    			 url.add(chartResponse1);
					    				 
					    			 }
				    		 }
				    	 }
				    	 
				    	 if(url!=null && url.size()!=0) {
				    		 publishPost.get(j).setChartUrls(url);;
				    	 }
				    	 
				     }
				}
				}
			List<Publish> publishs=null;
			
			if(publishPost!=null && publishPost.size()!=0) {
				
				publishs=publishPost;
				
				
				for(Publish publish:publishs) {

					
					Company company=null;
					Users user=usersRepository.findOne(publish.getUserId());
					
					if(publish.getCompanyId()!=null) {
					    company=companyRepository.findOne(publish.getCompanyId());
					}
					
//					if(publish.getCompanyId()!=null) {
//						publish.setUserName(company.getName());
//					}else if(publish.getUserId()!=null) {
//						publish.setUserName(user.getFirstName()+" "+user.getLastName());
//					}
					
					if(publish.getCompanyId()!=null) {
						publish.setProfilePicture(company.getLogo());
					}else if(publish.getUserId()!=null) {
						publish.setProfilePicture(user.getProfilePicture());
					}
					
//					if(publish.getCompanyId()!=null) {
//						publish.setTitle(company.get);
//					}else if(publish.getUserId()!=null) {
//						publish.setUserName(user.getFirstName()+" "+user.getLastName());
//					}
					
					List<CommentOnPublish> publishComments=commentOnPublishRepository.findByPublishIdLimit(publish.getId(),comPage);
					
					LikeDislikeOnPublish like=likeDislikeOnPublishRepository.findByPublishIdAndUserId(publish.getId(), user.getId());
				    if(like!=null)
					{
					  if(like.getIsLiked().equalsIgnoreCase("true"))
						  publish.setIsLikedByUser("true");
					  if(like.getIsDisliked().equalsIgnoreCase("true"))
						  publish.setIsDislikedByUser("true");
					 }	
					
					for(CommentOnPublish comment:publishComments) {
					
						LikeOnPublishComment likeOnComment=likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), comment.getUserId());
						if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
						 {
							 comment.setIsLikedByUser(likeOnComment.getIsLiked());
						 }
						  
						 Users comUser=usersRepository.findById(comment.getUserId());
						 comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
						 comment.setProfilePicture(comUser.getProfilePicture());
						 
						 if(comUser.getHeadline()!=null)
							 comment.setTitle(comUser.getHeadline());
					}
					 
					 publish.setCommentsOnPublishtList(publishComments); 
				}
				System.out.println(" publishs "+publishs);
				Collections.sort(publishs, new Comparator<Publish>()
				  { 
					  public int compare (Publish o2,Publish o1)
					  { 
						
						return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
					  } 
				  });
				
				
				 
				
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 
				 Page<Publish> result=null;
				 if(size<=publishPost.size())
				 {
					 result=new PageImpl<>(publishs.subList(0, size), pageable, publishPost.size());
					 if(size==publishPost.size())
					 {
						 jsonobjectFormat.setEndOfList(true);
					 }
				 }
				 else {
					
					 result=new PageImpl<>(publishs.subList(0, publishs.size()), pageable, publishPost.size());
					 jsonobjectFormat.setEndOfList(true);
				 }
				 
				 jsonobjectFormat.setMessage("User Publish Post Fetch succeessfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(result);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		         System.out.println(" customExceptionStr "+customExceptionStr.toString());
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 jsonobjectFormat.setMessage("Publish Post Not Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setEndOfList(true);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 jsonobjectFormat.setMessage("Publish Post Not Found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setEndOfList(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	
	@GetMapping("/publish/publishPost/companyTimeline")
	ResponseEntity<String> getPublishPostCompanyTimeline(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("companyId")Long companyId,@RequestParam("userId") Long userId) throws JsonProcessingException{
		Pageable comPage=new PageRequest(0, 10000); 

		if(page==null) {
			page=0;
		}
		
		if(size==null)
		{
			size=10;
		}
		
		
		Pageable pageable =new PageRequest(page, size);	
		Users user=usersRepository.findById(userId);
		 List<Publish> result=new ArrayList<Publish>();
		 if(user.getId()!=null){
			 List<AddressBookMemberEntity> listOfAddressBookMemberEntity=addressBookMemberRepository.findByEmailid(user.getEmailId());
			 for(AddressBookMemberEntity addressBookMemberEntity:listOfAddressBookMemberEntity){
				 List<Publish> 	listOfPublishItems=publishRepository.findByGrupId(addressBookMemberEntity.getGrupId());
						if(listOfPublishItems!=null && listOfPublishItems.size()!=0) {
							for(int j=0;j<listOfPublishItems.size();j++) {
								String chartUrl1=listOfPublishItems.get(j).getChart_url();
								if(chartUrl1!=null && chartUrl1.length()!=0) {
									String chartUrl[]=listOfPublishItems.get(j).getChart_url().split("},");
							     if(chartUrl.length!=0) {
							    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
							    	 for(int d=0;d<chartUrl.length;d++) {
							    		 if(chartUrl[d]!=null) {
							    			 if(chartUrl.length-1==d) {
								    			 Chart_Response chartResponse=new Chart_Response();
								    			 chartResponse.setChartUrl(chartUrl[d]+"");
								    			 System.out.println(" If Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
								    			 url.add(chartResponse);
								    			 }else {
								    				 Chart_Response chartResponse1=new Chart_Response();
									    			 chartResponse1.setChartUrl(chartUrl[d]+"}");
									    			 System.out.println(" else Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
									    			 url.add(chartResponse1);
								    			 }
							    		 	}
							    	 }
							    	 
							    	 if(url!=null && url.size()!=0) {
							    		 listOfPublishItems.get(j).setChartUrls(url);
							    	 }
							      }
								}
							}
						}
						 if(listOfPublishItems!=null && !listOfPublishItems.isEmpty())
						 {
							
							 for(int j=0;j<listOfPublishItems.size();j++)
							 {
								 Users postUser=usersRepository.findById(listOfPublishItems.get(j).getUserId());
//								 post.setUserName(postUser.getFirstName()+" "+postUser.getLastName());
								 listOfPublishItems.get(j).setProfilePicture(postUser.getProfilePicture());
									/*
									 * List<Experience> exp= ExperienceRepository.findByUserId(postUser.getId());
									 * if(exp!=null && !exp.isEmpty()) { Collections.reverse(exp);
									 * post.setTitle(exp.get(0).getTitle()); }
									 */
								 if(postUser.getHeadline()!=null)
									 listOfPublishItems.get(j).setTitle(postUser.getHeadline());
								 List<CommentOnPublish> commentOnPost=commentOnPublishRepository.findByPublishIdLimit(listOfPublishItems.get(j).getId(),comPage);
						
								
								 LikeDislikeOnPublish like=likeDislikeOnPublishRepository.findByPublishIdAndUserId(listOfPublishItems.get(j).getId(), userId);
								 if(like!=null)
								 {
									 if(like.getIsLiked().equalsIgnoreCase("true"))
										 listOfPublishItems.get(j).setIsLikedByUser("true");
									 if(like.getIsDisliked().equalsIgnoreCase("true"))
										 listOfPublishItems.get(j).setIsDislikedByUser("true");
								 }
								 for(CommentOnPublish comment:commentOnPost)
								 {
									 
									LikeOnPublishComment likeOnComment= likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), userId);
									 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
									 {
										 comment.setIsLikedByUser(likeOnComment.getIsLiked());
									 }
									 
									 Users comUser=usersRepository.findById(comment.getUserId());
									 comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
									comment.setProfilePicture(comUser.getProfilePicture());	
									 /*
										 * List<Experience> comExp= ExperienceRepository.findByUserId(comUser.getId());
										 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
										 * comment.setTitle(comExp.get(0).getTitle()); }
										 */
									 if(comUser.getHeadline()!=null)
										 comment.setTitle(comUser.getHeadline());
								 }
								 listOfPublishItems.get(j).setCommentsOnPublishtList(commentOnPost);
							 }
							 
						 }
						 
						 for(Publish publish:listOfPublishItems){
							 if(publish.getCompanyId()!=null){
								 if(publish.getCompanyId().equals(companyId)){
									 result.add(publish);
								 }
							 }
						 }
						 
						 
			 }
			 
			 
					 if(result!=null && !result.isEmpty()){
						 Collections.sort(result, new Comparator<Publish>()
						  { 
							  public int compare (Publish o2,Publish o1)
							  { 
								
								return (o1.getId()).compareTo(o2.getId()); 
							  } 
						  });
						 			 
						 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
						 Page<Publish> posts=null;
						 if(size<=result.size())
						 {
							 posts=new PageImpl<>(result.subList(0, size), pageable,result.size());
							 if(size==result.size())
							 jsonobjectFormat.setEndOfList(true);
						 }else {
							 jsonobjectFormat.setEndOfList(true);
							 posts=new PageImpl<>(result.subList(0, result.size()), pageable,result.size());
						 }
					 
					 jsonobjectFormat.setMessage("got all post by user connections succeessfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(posts);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}else{
					 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
					 jsonobjectFormat.setMessage("No Publish Items found for your groups");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setEndOfList(true);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}
					 
		 }else{
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 jsonobjectFormat.setMessage("User not found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setEndOfList(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/publish/publishPost/companyTimeline2")
	ResponseEntity<String> getPublishPostCompanyTimeline2(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("companyId")Long companyId,@RequestParam("userId") Long userId) throws JsonProcessingException{
		
		try {

			if(page==null) {
				page=0;
			}else {
				
			}if(size==null)
			{
				size=10;
			}else {
				
			}
			Pageable pageable =new PageRequest(page, size);
			size=size+size*page;
			Pageable comPage=new PageRequest(0, 2);
			
			Users users=usersRepository.findById(userId);
			List<AddressBook> listOfAddressBook=addressBookRepository.findByCompanyId(companyId);
			List<Long> listOfValidGroups=new ArrayList<Long>();
			for(int i=0;i<listOfAddressBook.size();i++){
				AddressBook addressBook= listOfAddressBook.get(i);
				Long id= addressBook.getId(); //Got group Id
				List<AddressBookMemberEntity> listOfAddressBookMembers=addressBookMemberRepository.findByGrupId(id);
				for(int j=0;j<listOfAddressBookMembers.size();j++){
					AddressBookMemberEntity addressBookMemberEntity=listOfAddressBookMembers.get(j);
					if(addressBookMemberEntity.getEmailid().equals(users.getEmailId())){
						listOfValidGroups.add(addressBookMemberEntity.getGrupId());
					}
				}
			}
			
			List<Publish> publishPost=new ArrayList<Publish>();
			for(int p=0;p<listOfValidGroups.size();p++){
				publishPost=publishRepository.findByGrupId(listOfValidGroups.get(p));
			}
			
			if(publishPost!=null && publishPost.size()!=0) {
				for(int j=0;j<publishPost.size();j++) {
					if(publishPost.get(j).getChart_url()!=null && publishPost.get(j).getChart_url().length()!=0) {
				     String chartUrl[]=publishPost.get(j).getChart_url().split("},");
				     
				     if(chartUrl.length!=0) {
				    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
				    	 for(int i=0;i<chartUrl.length;i++) {
				    		 if(chartUrl[i]!=null) {
				    			 if(chartUrl.length-1==i) {
					    			 Chart_Response chartResponse=new Chart_Response();
					    			 chartResponse.setChartUrl(chartUrl[i]+"");
					    			 System.out.println(" If Part "+" i "+i+" "+" chartUrl[i] "+chartUrl[i]);
					    			 url.add(chartResponse);
					    			 }else {
					    				 
					    				 Chart_Response chartResponse1=new Chart_Response();
						    			 chartResponse1.setChartUrl(chartUrl[i]+"}");
						    			 System.out.println(" else Part "+" i "+i+" "+" chartUrl[i] "+chartUrl[i]);
						    			 url.add(chartResponse1);
					    				 
					    			 }
				    		 }
				    	 }
				    	 
				    	 if(url!=null && url.size()!=0) {
				    		 publishPost.get(j).setChartUrls(url);;
				    	 }
				    	 
				     }
				}
				}
				}
			
			List<Publish> publishs=null;
			
			if(publishPost!=null && publishPost.size()!=0) {
				
				publishs=publishPost;
				
				for(Publish publish:publishs) {
					
					Company company=null;
					Users user=null;
					if(publish.getUserId()!=null) {
					user=usersRepository.findOne(publish.getUserId());
					}
					if(publish.getCompanyId()!=null) {
					    company=companyRepository.findOne(publish.getCompanyId());
					}
					
//					if(publish.getCompanyId()!=null && publish.getUserId()!=null) {
//						publish.setUserName(company.getName());
//					}else if(publish.getUserId()!=null) {
//						publish.setUserName(user.getFirstName()+" "+user.getLastName());
//					}
					
//					if(publish.getCompanyId()!=null) {
//						publish.setProfilePicture(company.getLogo());
//					}else 
					if(publish.getUserId()!=null) {
						publish.setProfilePicture(user.getProfilePicture());
					}
					
					if(publish.getCompanyId()!=null) {
//						publish.setTitle(company.get);
					}else if(publish.getUserId()!=null) {
						publish.setTitle(user.getHeadline());
					}
					
//					List<CommentOnPublish> publishComments=commentOnPublishRepository.findCommentsOnPublish(publish.getId());
					List<CommentOnPublish> publishComments=commentOnPublishRepository.findByPublishIdLimit(publish.getId(), comPage);
					System.out.println(" publish.getId() "+publish.getId());
					LikeDislikeOnPublish like=likeDislikeOnPublishRepository.findByPublishIdAndUserId(publish.getId(), user.getId());
				    if(like!=null)
					{
					  if(like.getIsLiked().equalsIgnoreCase("true"))
						  publish.setIsLikedByUser("true");
					  if(like.getIsDisliked().equalsIgnoreCase("true"))
					  publish.setIsDislikedByUser("true");
					 }	
					
					for(CommentOnPublish comment:publishComments) {
					
						LikeOnPublishComment likeOnComment=likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), comment.getUserId());
						if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
						 {
							 comment.setIsLikedByUser(likeOnComment.getIsLiked());
						 }
						  
						 Users comUser=usersRepository.findById(comment.getUserId());
						 comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
						 comment.setProfilePicture(comUser.getProfilePicture());
						 
						 if(comUser.getHeadline()!=null)
							 comment.setTitle(comUser.getHeadline());
					}
					 
					 publish.setCommentsOnPublishtList(publishComments); 
				}
				
				Collections.sort(publishs, new Comparator<Publish>()
				  { 
					  public int compare (Publish o1,Publish o2)
					  { 
						
						return (o2.getDate()+" "+o2.getTime()).compareTo(o1.getDate()+" "+o1.getTime()); 
					  } 
				  });
				
				 
				
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 
				 Page<Publish> result=null;
				 if(size<=publishPost.size())
				 {
					 result=new PageImpl<>(publishs.subList(0, size), pageable, publishPost.size());
					 if(size==publishPost.size())
					 {
						 jsonobjectFormat.setEndOfList(true);
					 }
				 }
				 else {
					
					 result=new PageImpl<>(publishs.subList(0, publishs.size()), pageable, publishPost.size());
					 jsonobjectFormat.setEndOfList(true);
				 }
				 
				 jsonobjectFormat.setMessage("Company Publish Post Fetch succeessfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(result);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 jsonobjectFormat.setMessage("Publish Post Not Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setEndOfList(true);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 jsonobjectFormat.setMessage("Publish Post Not Found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setEndOfList(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	
	@Transactional
	@PostMapping("/publish/comment")
	public ResponseEntity<String> commentOnPublish(@RequestBody CommentOnPublish reqComment) throws JsonProcessingException {
		log.debug("REST request to comment on Publish post : {}", reqComment);
		
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		reqComment.setDate(strdate);
		reqComment.setTime(time);
		Users user=usersRepository.findById(reqComment.getUserId());
		if(user==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Publish publish=publishRepository.findOne(reqComment.getPublishId());
		if(publish==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("Publish post not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		CommentOnPublish comment=commentOnPublishRepository.save(reqComment);
		if(comment!=null)
		{
			comment.setUsername(user.getFirstName()+" "+user.getLastName());
			comment.setProfilePicture(user.getProfilePicture());
			if(user.getHeadline()!=null)
				comment.setTitle(user.getHeadline());
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("comment on Publish post successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(comment);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to comment on Publish post ");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
		
	@GetMapping("/publish/connect/users")
	public ResponseEntity<String> getAllPublishPostByUserConnections2(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {
		Pageable comPage=new PageRequest(0, 2); 

		if(page==null) {
			page=0;
		}
		
		if(size==null)
		{
			size=10;
		}
		
		
		Pageable pageable =new PageRequest(page, size);	
		Users user=usersRepository.findById(userId);
		 List<Publish> result=new ArrayList<Publish>();
		 if(user.getId()!=null){
			 List<AddressBookMemberEntity> listOfAddressBookMemberEntity=addressBookMemberRepository.findByEmailid(user.getEmailId());
			 for(AddressBookMemberEntity addressBookMemberEntity:listOfAddressBookMemberEntity){
				 List<Publish> 	listOfPublishItems=publishRepository.findByGrupId(addressBookMemberEntity.getGrupId());
						if(listOfPublishItems!=null && listOfPublishItems.size()!=0) {
							for(int j=0;j<listOfPublishItems.size();j++) {
								String chartUrl1=listOfPublishItems.get(j).getChart_url();
								if(chartUrl1!=null && chartUrl1.length()!=0) {
									String chartUrl[]=listOfPublishItems.get(j).getChart_url().split("},");
							     if(chartUrl.length!=0) {
							    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
							    	 for(int d=0;d<chartUrl.length;d++) {
							    		 if(chartUrl[d]!=null) {
							    			 if(chartUrl.length-1==d) {
								    			 Chart_Response chartResponse=new Chart_Response();
								    			 chartResponse.setChartUrl(chartUrl[d]+"");
								    			 System.out.println(" If Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
								    			 url.add(chartResponse);
								    			 }else {
								    				 Chart_Response chartResponse1=new Chart_Response();
									    			 chartResponse1.setChartUrl(chartUrl[d]+"}");
									    			 System.out.println(" else Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
									    			 url.add(chartResponse1);
								    			 }
							    		 	}
							    	 }
							    	 
							    	 if(url!=null && url.size()!=0) {
							    		 listOfPublishItems.get(j).setChartUrls(url);
							    	 }
							      }
								}
							}
						}
						 if(listOfPublishItems!=null && !listOfPublishItems.isEmpty())
						 {
							
							 for(int j=0;j<listOfPublishItems.size();j++)
							 {
								 Users postUser=usersRepository.findById(listOfPublishItems.get(j).getUserId());
//								 post.setUserName(postUser.getFirstName()+" "+postUser.getLastName());
								 listOfPublishItems.get(j).setProfilePicture(postUser.getProfilePicture());
									/*
									 * List<Experience> exp= ExperienceRepository.findByUserId(postUser.getId());
									 * if(exp!=null && !exp.isEmpty()) { Collections.reverse(exp);
									 * post.setTitle(exp.get(0).getTitle()); }
									 */
								 if(postUser.getHeadline()!=null)
									 listOfPublishItems.get(j).setTitle(postUser.getHeadline());
								 List<CommentOnPublish> commentOnPost=commentOnPublishRepository.findByPublishIdLimit(listOfPublishItems.get(j).getId(),comPage);
						
								
								 LikeDislikeOnPublish like=likeDislikeOnPublishRepository.findByPublishIdAndUserId(listOfPublishItems.get(j).getId(), userId);
								 if(like!=null)
								 {
									 if(like.getIsLiked().equalsIgnoreCase("true"))
										 listOfPublishItems.get(j).setIsLikedByUser("true");
									 if(like.getIsDisliked().equalsIgnoreCase("true"))
										 listOfPublishItems.get(j).setIsDislikedByUser("true");
								 }
								 for(CommentOnPublish comment:commentOnPost)
								 {
									 
									LikeOnPublishComment likeOnComment= likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), userId);
									 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
									 {
										 comment.setIsLikedByUser(likeOnComment.getIsLiked());
									 }
									 
									 Users comUser=usersRepository.findById(comment.getUserId());
									 comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
									comment.setProfilePicture(comUser.getProfilePicture());	
									 /*
										 * List<Experience> comExp= ExperienceRepository.findByUserId(comUser.getId());
										 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
										 * comment.setTitle(comExp.get(0).getTitle()); }
										 */
									 if(comUser.getHeadline()!=null)
										 comment.setTitle(comUser.getHeadline());
								 }
								 listOfPublishItems.get(j).setCommentsOnPublishtList(commentOnPost);
							 }
							 
						 }
						 result.addAll(listOfPublishItems);
			 }
			 
			 
					 if(result!=null && !result.isEmpty()){
						 Collections.sort(result, new Comparator<Publish>()
						  { 
							  public int compare (Publish o2,Publish o1)
							  { 
								
								return (o1.getId()).compareTo(o2.getId()); 
							  } 
						  });
						 			 
						 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
						 Page<Publish> posts=null;
						 if(size<=result.size())
						 {
							 posts=new PageImpl<>(result.subList(0, size), pageable,result.size());
							 if(size==result.size())
							 jsonobjectFormat.setEndOfList(true);
						 }else {
							 jsonobjectFormat.setEndOfList(true);
							 posts=new PageImpl<>(result.subList(0, result.size()), pageable,result.size());
						 }
					 
					 jsonobjectFormat.setMessage("got all post by user connections succeessfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(posts);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}else{
					 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
					 jsonobjectFormat.setMessage("No Publish Items found for your groups");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setEndOfList(true);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}
					 
		 }else{
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 jsonobjectFormat.setMessage("User not found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setEndOfList(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
		
	}
	
	
	
	
	
	
	@GetMapping("/publish/connect/users2")
	public ResponseEntity<String> getAllPublishPostByUserConnections(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {

		
		if(page==null) {
			page=0;
		}else {
			
		}if(size==null)
		{
			size=10;
		}else {
			
		}
		Pageable pageable =new PageRequest(page, size);	
			size=size+size*page;
	
		Pageable comPage=new PageRequest(0, 2);
		List<Publish> result=new ArrayList<Publish>();
		
		List<Connections> connect=connectionsRepository.findByUserId(userId);
	    
		if(connect!=null && !connect.isEmpty())
		{
			int i=1;
			for(Connections con:connect)
			{
				Long user=null;
				if(userId!=con.getUserId())
				{
					user=con.getUserId();
				}else if(userId!=con.getConnectedUserId())
				{
					user=con.getConnectedUserId();
				}
		
				
				List<Publish> 	postList=publishRepository.findByUserId(user);
				
				
				if(postList!=null && postList.size()!=0) {
					for(int j=0;j<postList.size();j++) {
						String chartUrl1=postList.get(j).getChart_url();
						
						if(chartUrl1!=null && chartUrl1.length()!=0) {
							String chartUrl[]=postList.get(j).getChart_url().split("},");
						
					     if(chartUrl.length!=0) {
					    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
					    	 
					    	 for(int d=0;d<chartUrl.length;d++) {
					    		 if(chartUrl[d]!=null) {
					    			 if(chartUrl.length-1==d) {
						    			 Chart_Response chartResponse=new Chart_Response();
						    			 chartResponse.setChartUrl(chartUrl[d]+"");
						    			 System.out.println(" If Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
						    			 url.add(chartResponse);
						    			 }else {
						    				 
						    				 Chart_Response chartResponse1=new Chart_Response();
							    			 chartResponse1.setChartUrl(chartUrl[d]+"}");
							    			 System.out.println(" else Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
							    			 url.add(chartResponse1);
						    				 
						    			 }
					    		 }
					    	 }
					    	 
					    	 if(url!=null && url.size()!=0) {
					    		 postList.get(j).setChartUrls(url);;
					    	 }
					    	 
					     }
						}
					}
					}
				 if(i==connect.size())
				 {
					 
					List<Publish> postuser=publishRepository.findByUserId(userId);
					
					
					if(postuser!=null && postuser.size()!=0) {
						for(int j=0;j<postuser.size();j++) {
							if(postuser.get(j).getChart_url()!=null && postuser.get(j).getChart_url().length()!=0) {
						     String chartUrl[]=postuser.get(j).getChart_url().split("},");
						     
						     if(chartUrl.length!=0) {
						    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
						    	 
						    	 for(int d=0;d<chartUrl.length;d++) {
						    		 if(chartUrl[d]!=null) {
						    			 if(chartUrl.length-1==d) {
							    			 Chart_Response chartResponse=new Chart_Response();
							    			 chartResponse.setChartUrl(chartUrl[d]+"");
							    			 System.out.println(" If Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
							    			 url.add(chartResponse);
							    			 }else {
							    				 
							    				 Chart_Response chartResponse1=new Chart_Response();
								    			 chartResponse1.setChartUrl(chartUrl[d]+"}");
								    			 System.out.println(" else Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
								    			 url.add(chartResponse1);
							    				 
							    			 }
						    		 }
						    	 }
						    	 
						    	 if(url!=null && url.size()!=0) {
						    		 postuser.get(j).setChartUrls(url);;
						    	 }
						    	 
						     }
							}
						}
						}
					
					
					
					if(postuser!=null && !postuser.isEmpty())
					{
						postList.addAll(postuser);
					}
					
				 }
				 if(postList!=null && !postList.isEmpty())
				 {
					
					 for(Publish post:postList)
					 {
						 Users postUser=usersRepository.findById(post.getUserId());
//						 post.setUserName(postUser.getFirstName()+" "+postUser.getLastName());
						 post.setProfilePicture(postUser.getProfilePicture());
							/*
							 * List<Experience> exp= ExperienceRepository.findByUserId(postUser.getId());
							 * if(exp!=null && !exp.isEmpty()) { Collections.reverse(exp);
							 * post.setTitle(exp.get(0).getTitle()); }
							 */
						 if(postUser.getHeadline()!=null)
							 post.setTitle(postUser.getHeadline());
						 List<CommentOnPublish> commentOnPost=commentOnPublishRepository.findByPublishIdLimit(post.getId(),comPage);
				
						
						 LikeDislikeOnPublish like=likeDislikeOnPublishRepository.findByPublishIdAndUserId(post.getId(), userId);
						 if(like!=null)
						 {
							 if(like.getIsLiked().equalsIgnoreCase("true"))
							 post.setIsLikedByUser("true");
							 if(like.getIsDisliked().equalsIgnoreCase("true"))
								 post.setIsDislikedByUser("true");
						 }
						 for(CommentOnPublish comment:commentOnPost)
						 {
							 
							LikeOnPublishComment likeOnComment= likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), userId);
							 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
							 {
								 comment.setIsLikedByUser(likeOnComment.getIsLiked());
							 }
							 
							 Users comUser=usersRepository.findById(comment.getUserId());
							 comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
							comment.setProfilePicture(comUser.getProfilePicture());	
							 /*
								 * List<Experience> comExp= ExperienceRepository.findByUserId(comUser.getId());
								 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
								 * comment.setTitle(comExp.get(0).getTitle()); }
								 */
							 if(comUser.getHeadline()!=null)
								 comment.setTitle(comUser.getHeadline());
						 }
						 post.setCommentsOnPublishtList(commentOnPost);
						
					 }
					result.addAll(postList);
				 }
				 i++;
			}
			
			if(result!=null && result.size()!=0) {
				
				for(Publish publishData:result) {
					
					AddressBook addressBook=addressBookRepository.findOne(publishData.getGroupId());
					
					if(addressBook!=null) {
						
						ArrayList<AddressBookMemberEntity> addressBookMember=addressBookMemberRepository.getByGroupId(addressBook.getId());
						
						if(addressBookMember!=null && addressBookMember.size()!=0) {
							
							
							
						}
						
					}
					
					
				}
				
			}
			
		}
		
		
		
		
		
		if(result!=null && !result.isEmpty())
		{
		
			 Collections.sort(result, new Comparator<Publish>()
			  { 
				  public int compare (Publish o2,Publish o1)
				  { 
					
					return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
				  } 
			  });
			 			 
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 Page<Publish> posts=null;
			 if(size<=result.size())
			 {
				 posts=new PageImpl<>(result.subList(0, size), pageable,result.size());
				 if(size==result.size())
				 jsonobjectFormat.setEndOfList(true);
			 }else {
				 jsonobjectFormat.setEndOfList(true);
				 posts=new PageImpl<>(result.subList(0, result.size()), pageable,result.size());
			 }
		 
		 jsonobjectFormat.setMessage("got all post by user connections succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(posts);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 List<Publish> postList=publishRepository.findByUserId(userId);
			 
			 
			 
			 if(postList!=null && !postList.isEmpty())
			 {
				
		
						for(int j=0;j<postList.size();j++) {
							if(postList.get(j).getChart_url()!=null && postList.get(j).getChart_url().length()!=0)
							{
						     String chartUrl[]=postList.get(j).getChart_url().split("},");
						     
						     if(chartUrl.length!=0) {
						    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
						    	 
						    	 for(int d=0;d<chartUrl.length;d++) {
						    		 if(chartUrl[d]!=null) {
						    			 if(chartUrl.length-1==d) {
							    			 Chart_Response chartResponse=new Chart_Response();
							    			 chartResponse.setChartUrl(chartUrl[d]+"");
							    			 System.out.println(" If Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
							    			 url.add(chartResponse);
							    			 }else {
							    				 
							    				 Chart_Response chartResponse1=new Chart_Response();
								    			 chartResponse1.setChartUrl(chartUrl[d]+"}");
								    			 System.out.println(" else Part "+" i "+d+" "+" chartUrl[i] "+chartUrl[d]);
								    			 url.add(chartResponse1);
							    				 
							    			 }
						    		 }
						    	 }
						    	 
						    	 if(url!=null && url.size()!=0) {
						    		 postList.get(j).setChartUrls(url);;
						    	 }
						    	 
						     }
							}
						}
						
						
				 
				 
				 for(Publish post:postList)
				 {
					 Users postUser=usersRepository.findById(post.getUserId());
//					 post.setUserName(postUser.getFirstName()+" "+postUser.getLastName());
					 post.setProfilePicture(postUser.getProfilePicture());
						/*
						 * List<Experience> exp= ExperienceRepository.findByUserId(postUser.getId());
						 * if(exp!=null && !exp.isEmpty()) { Collections.reverse(exp);
						 * post.setTitle(exp.get(0).getTitle()); }
						 */
					 if(postUser.getHeadline()!=null)
						 post.setTitle(postUser.getHeadline());
					 List<CommentOnPublish> commentOnPost=commentOnPublishRepository.findByPublishIdLimit(post.getId(),comPage);
			
					
					 LikeDislikeOnPublish like=likeDislikeOnPublishRepository.findByPublishIdAndUserId(post.getId(), userId);
					 if(like!=null)
					 {
						 if(like.getIsLiked().equalsIgnoreCase("true"))
						 post.setIsLikedByUser("true");
						 if(like.getIsDisliked().equalsIgnoreCase("true"))
							 post.setIsDislikedByUser("true");
					 }
					 for(CommentOnPublish comment:commentOnPost)
					 {
						 
						LikeOnPublishComment likeOnComment= likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), userId);
						 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
						 {
							 comment.setIsLikedByUser(likeOnComment.getIsLiked());
						 }
						 
						 Users comUser=usersRepository.findById(comment.getUserId());
						 comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
						 comment.setProfilePicture(comUser.getProfilePicture());
							/*
							 * List<Experience> comExp= ExperienceRepository.findByUserId(comUser.getId());
							 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
							 * comment.setTitle(comExp.get(0).getTitle()); }
							 */
						 if(comUser.getHeadline()!=null)
							 comment.setTitle(comUser.getHeadline());
					 }
					 post.setCommentsOnPublishtList(commentOnPost);
				 }
				 Collections.sort(postList, new Comparator<Publish>()
				  { 
					  public int compare (Publish o1,Publish o2)
					  { 
						return (o2.getDate()+" "+o2.getTime()).compareTo(o1.getDate()+" "+o1.getTime()); 
					  } 
				  });
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 Page<Publish> posts=null;
				 if(size<=postList.size())
				 {
					 if(size==postList.size())
						 jsonobjectFormat.setEndOfList(true);
				posts=new PageImpl<>(postList.subList(0, size), pageable,postList.size());
				 }else {
					 jsonobjectFormat.setEndOfList(true);
					 posts=new PageImpl<>(postList.subList(0, postList.size()), pageable,postList.size());
				 }
			 
			 jsonobjectFormat.setMessage("got all post by user connections succeessfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(posts);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			 else {
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 jsonobjectFormat.setMessage("no post yet");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setEndOfList(true);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
		 }
	}
	
	
	@Transactional
	@PostMapping("/publish/like")
	public ResponseEntity<String> likeDislikeOnPublishPost(@RequestBody LikeDislikeOnPublish reqLikeDislike) throws JsonProcessingException {
		log.debug("REST request to like or dislike a Publish post : {}", reqLikeDislike);
		LikeDislikeOnPublish result=null;
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		LikeDislikeOnPublish temp=likeDislikeOnPublishRepository.findByPublishIdAndUserId(reqLikeDislike.getPublishId(),reqLikeDislike.getUserId());
		Publish post=publishRepository.findOne(reqLikeDislike.getPublishId());
		if(post==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("post not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Users user=usersRepository.findById(reqLikeDislike.getUserId());
		if(user==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Date date=new Date();
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		
	
		
		if(temp!=null)
		{
			if(reqLikeDislike.getIsDisliked().equalsIgnoreCase("true"))
			{
				if(temp.getIsLiked().equalsIgnoreCase("true"))
				{
					temp.setIsLiked("false");
					temp.setIsDisliked("true");
					post.setNoOfLikes(post.getNoOfLikes()-new Long(1));
					if(post.getNoOfDislikes()==null)
					{
						post.setNoOfDislikes(new Long(1));
					}else
						post.setNoOfDislikes(post.getNoOfDislikes()+new Long(1));
					
					
					jsonobjectFormat.setMessage("disliked the post successfully");
				}else if(temp.getIsLiked().equalsIgnoreCase("false"))
				{
					if(temp.getIsDisliked().equalsIgnoreCase("false"))
					{
						temp.setIsDisliked("true");
						if(post.getNoOfDislikes()==null)
						{
							post.setNoOfDislikes(new Long(1));
						}else
							post.setNoOfDislikes(post.getNoOfDislikes()+new Long(1));
						
						jsonobjectFormat.setMessage("disliked the post successfully");
					}else {
						temp.setIsDisliked("false");
						post.setNoOfDislikes(post.getNoOfDislikes()-new Long(1));
						jsonobjectFormat.setMessage("un disliked the post successfully");
					}
				}
			}else if(reqLikeDislike.getIsLiked().equalsIgnoreCase("true"))
			{
				if(temp.getIsDisliked().equalsIgnoreCase("true"))
				{
					temp.setIsDisliked("false");
					temp.setIsLiked("true");
					post.setNoOfDislikes(post.getNoOfDislikes()-new Long(1));
					if(post.getNoOfLikes()==null)
					{
						post.setNoOfLikes(new Long(1));
					}else {
						post.setNoOfLikes(post.getNoOfLikes()+new Long(1));
					}
					jsonobjectFormat.setMessage("liked the post successfully");
				}else if(temp.getIsDisliked().equalsIgnoreCase("false"))
				{
					if(temp.getIsLiked().equalsIgnoreCase("true"))
					{
				
						temp.setIsLiked("false");
						
						post.setNoOfLikes(post.getNoOfLikes()-new Long(1));
						jsonobjectFormat.setMessage("un liked the post successfully");
					}else {
						temp.setIsLiked("true");
						post.setNoOfLikes(post.getNoOfLikes()+new Long(1));
						jsonobjectFormat.setMessage("liked the post successfully");
					}
				}
			}else if(reqLikeDislike.getIsDisliked().equalsIgnoreCase("false") && reqLikeDislike.getIsLiked().equalsIgnoreCase("false"))
			{
				if(temp.getIsLiked().equalsIgnoreCase("true"))
				{
					temp.setIsLiked("false");
					post.setNoOfLikes(post.getNoOfLikes()-new Long(1));
					jsonobjectFormat.setMessage("un liked the post successfully");
				}else if(temp.getIsDisliked().equalsIgnoreCase("true"))
				{
					temp.setIsDisliked("false");
					post.setNoOfDislikes(post.getNoOfDislikes()-new Long(1));
					jsonobjectFormat.setMessage("un disliked the post successfully");
				}
			}
			
	
			temp.setDate(strdate);
			temp.setTime(time);
			result=likeDislikeOnPublishRepository.save(temp);
		}else {
			reqLikeDislike.setDate(strdate);
			reqLikeDislike.setTime(time);
			result=likeDislikeOnPublishRepository.save(reqLikeDislike);
			if(result.getIsDisliked().equalsIgnoreCase("true"))
			{
				if(post.getNoOfDislikes()==null)
				{
					post.setNoOfDislikes(new Long(1));
				}else {
					post.setNoOfDislikes(post.getNoOfDislikes()+new Long(1));
				}
			
				jsonobjectFormat.setMessage("disliked the post successfully");
			}else if(result.getIsLiked().equalsIgnoreCase("true"))
			{
				if(post.getNoOfLikes()==null )
				{
					post.setNoOfLikes(new Long(1));
				}else {
					post.setNoOfLikes(post.getNoOfLikes()+new Long(1));
				}
				jsonobjectFormat.setMessage("liked the post successfully");
			}
		}
		
		if(result!=null)
		{
			Publish pos=publishRepository.save(post);
			result.setNoOflikes(pos.getNoOfLikes());
			result.setNoOfDislikes(pos.getNoOfDislikes());
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			
			 jsonobjectFormat.setMessage("unable to like  post ");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	
	
	@Transactional
	@PostMapping("/publish/comment/like")
	public ResponseEntity<String> likePostedComment(@RequestBody LikeOnPublishComment reqLikeOnPostComment) throws JsonProcessingException {
		log.debug("REST request to like on posted on comment : {}", reqLikeOnPostComment);
		LikeOnPublishComment result=null;
		Users user=usersRepository.findById(reqLikeOnPostComment.getUserId());
		if(user==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		
	
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();

		CommentOnPublish comOnP=commentOnPublishRepository.findOne(reqLikeOnPostComment.getPublishCommentId());
		if(comOnP==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("comment not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		LikeOnPublishComment temp=likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(reqLikeOnPostComment.getPublishCommentId(),reqLikeOnPostComment.getUserId());
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		
		if(temp!=null)
		{
			if(temp.getIsLiked().equalsIgnoreCase("true"))
			{
				temp.setIsLiked("false");
				comOnP.setNoOfLikes(comOnP.getNoOfLikes()-new Long(1));
				jsonobjectFormat.setMessage("un liked the comment successfully");
			}else {
				temp.setIsLiked("true");
				comOnP.setNoOfLikes(comOnP.getNoOfLikes()+new Long(1));
				jsonobjectFormat.setMessage("liked the comment successfully");
			}
			temp.setDate(strdate);
			temp.setTime(time);
			result=likeOnPublishCommentRepository.save(temp);
		}else {
			reqLikeOnPostComment.setDate(strdate);
			reqLikeOnPostComment.setTime(time);
			result=likeOnPublishCommentRepository.save(reqLikeOnPostComment);
			if(result.getIsLiked().equalsIgnoreCase("true"))
			{
				if(comOnP.getNoOfLikes()==null||comOnP.getNoOfLikes()==0)
				{
					comOnP.setNoOfLikes(new Long(1));
				}else {
					comOnP.setNoOfLikes(comOnP.getNoOfLikes()+new Long(1));
				}
			
				jsonobjectFormat.setMessage("liked the comment successfully");
			}
			else
			{
				jsonobjectFormat.setMessage("un liked the comment successfully");
			}
		}
		
		if(result!=null)
		{
			commentOnPublishRepository.save(comOnP);
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			
			 jsonobjectFormat.setMessage("unable to like posted comment ");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	
	@GetMapping("/publish/comments")
	public ResponseEntity<String> getAllCommentsByPublishPost(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId,@RequestParam("postId")Long postId) throws JsonProcessingException {
		Pageable pageable =new PageRequest(page, size); 
		if(page==null){
		 		page=0;
				 }else{
					 
				 }
		if(size==null){
			 size=10;
		 }else{
			size=size+size*page;
		 }
 	
 	 
		
		Pageable pageab =new PageRequest(page, size); 
		List<CommentOnPublish> commentOnPost=commentOnPublishRepository.findByPublishIdLimit(postId,pageab);
							
		if(commentOnPost!=null && !commentOnPost.isEmpty())
		{
		 for(CommentOnPublish comment:commentOnPost)
		 {
							 
				LikeOnPublishComment likeOnComment= likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), userId);
			    if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
				{
					comment.setIsLikedByUser(likeOnComment.getIsLiked());
				}
							 
				Users comUser=usersRepository.findById(comment.getUserId());
				comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
				comment.setProfilePicture(comUser.getProfilePicture());
				/*
				 * List<Experience> comExp= ExperienceRepository.findByUserId(comUser.getId());
				 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
				 * comment.setTitle(comExp.get(0).getTitle()); }
				 */
				 if(comUser.getHeadline()!=null)
					 comment.setTitle(comUser.getHeadline());
		  }
		 Page<CommentOnPublish> posts=new PageImpl<>(commentOnPost, pageable,commentOnPost.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all comments on post succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(posts);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no comments found");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	
	@GetMapping("/publish/likes")
	public ResponseEntity<String> getAllLikesForPublishPost(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("postId")Long postId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	List<LikeDislikeOnPublish> likeOnPostList=likeDislikeOnPublishRepository.findByPublishIdLiked(postId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikeDislikeOnPublish likes:likeOnPostList)
				{
					
					Users user=usersRepository.findById(likes.getUserId());
					 LikeObj likeobj=new LikeObj(user.getId(), user.getFirstName()+" "+user.getLastName(), null,user.getProfilePicture());
						/*
						 * List<Experience> comExp= ExperienceRepository.findByUserId(user.getId());
						 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
						 * likeobj.setTitle(comExp.get(0).getTitle()); }
						 */
					 if(user.getHeadline()!=null)
						 likeobj.setTitle(user.getHeadline());
					 likeList.add(likeobj);
				 }
		if(likeList!=null && !likeList.isEmpty())
		{
		 Page<LikeObj> ques=new PageImpl<>(likeList, pageable, likeList.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all likes  successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(ques);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no users liked yet");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/publish/dislikes")
	public ResponseEntity<String> getAllDisLikesForPublishPost(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("postId")Long postId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	List<LikeDislikeOnPublish> likeOnPostList=likeDislikeOnPublishRepository.findByPublishIdDisLiked(postId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikeDislikeOnPublish likes:likeOnPostList)
				{
					
					Users user=usersRepository.findById(likes.getUserId());
					 LikeObj likeobj=new LikeObj(user.getId(), user.getFirstName()+" "+user.getLastName(), null,user.getProfilePicture());
						/*
						 * List<Experience> comExp= ExperienceRepository.findByUserId(user.getId());
						 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
						 * likeobj.setTitle(comExp.get(0).getTitle()); }
						 */
					 if(user.getHeadline()!=null)
						 likeobj.setTitle(user.getHeadline());
					 likeList.add(likeobj);
				 }
		if(likeList!=null && !likeList.isEmpty())
		{
		 Page<LikeObj> ques=new PageImpl<>(likeList, pageable, likeList.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all likes  successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(ques);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no users liked yet");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/publish/comment/likes")
	public ResponseEntity<String> getAllLikesForPublishPostComments(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("commentOnPostId")Long commentOnPostId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	    List<LikeOnPublishComment> likeOnPostComList=likeOnPublishCommentRepository.findByPublishCommentIdLiked(commentOnPostId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikeOnPublishComment likes:likeOnPostComList)
				{
					
					Users user=usersRepository.findById(likes.getUserId());
					 LikeObj likeobj=new LikeObj(user.getId(), user.getFirstName()+" "+user.getLastName(), null,user.getProfilePicture());
						/*
						 * List<Experience> comExp= ExperienceRepository.findByUserId(user.getId());
						 * if(comExp!=null && !comExp.isEmpty()) { Collections.reverse(comExp);
						 * likeobj.setTitle(comExp.get(0).getTitle()); }
						 */
					 if(user.getHeadline()!=null)
						 likeobj.setTitle(user.getHeadline());
					 likeList.add(likeobj);
				 }
		if(likeList!=null && !likeList.isEmpty())
		{
		 Page<LikeObj> ques=new PageImpl<>(likeList, pageable, likeList.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all likes  successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(ques);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no users liked yet");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	
	@GetMapping("/publish/getAll")
	ResponseEntity<String> getAllPublishPost(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException{
		
		try {

			if(page==null) {
				page=0;
			}else {
				
			}if(size==null)
			{
				size=10;
			}else {
				
			}
			Pageable pageable =new PageRequest(page, size);
			
				size=size+size*page;
		
			Pageable comPage=new PageRequest(0, 2);
			
			List<Publish> publishPost=(List<Publish>) publishRepository.findAll();
			if(publishPost!=null && publishPost.size()!=0) {
				for(int j=0;j<publishPost.size();j++) {
				
					if(publishPost.get(j).getChart_url()!=null && publishPost.get(j).getChart_url().length()!=0) {
						
			
				     String chartUrl[]=publishPost.get(j).getChart_url().split("},");
				     
				     if(chartUrl.length!=0) {
				    	 ArrayList<Chart_Response> url=new ArrayList<Chart_Response>();
				    	 
				    	 for(int i=0;i<chartUrl.length;i++) {
				    		 if(chartUrl[i]!=null) {
				    			 if(chartUrl.length-1==i) {
				    			 Chart_Response chartResponse=new Chart_Response();
				    			 chartResponse.setChartUrl(chartUrl[i]+"");
				    			 System.out.println(" If Part "+" i "+i+" "+" chartUrl[i] "+chartUrl[i]);
				    			 url.add(chartResponse);
				    			 }else {
				    				 
				    				 Chart_Response chartResponse1=new Chart_Response();
					    			 chartResponse1.setChartUrl(chartUrl[i]+"}");
					    			 System.out.println(" else Part "+" i "+i+" "+" chartUrl[i] "+chartUrl[i]);
					    			 url.add(chartResponse1);
				    				 
				    			 }
				    		 
				    		 }
				    	 }
				    	 
				    	 if(url!=null && url.size()!=0) {
				    		 publishPost.get(j).setChartUrls(url);;
				    	 }
				    	 
				     }
				}
				}
				}
			System.out.println(" publishPost "+publishPost.size());
			
			List<Publish> publishs=null;
			
			if(publishPost!=null && publishPost.size()!=0) {
				
				publishs=publishPost;
				
				for(Publish publish:publishs) {
					
					Company company=null;
					Users user=null;
					if(publish.getUserId()!=null) {
						System.out.println(" publish.getUserId() "+publish.getUserId());
					user=usersRepository.findOne(publish.getUserId());
					System.out.println(" User Details :- "+user.toString());
					}
					if(publish.getCompanyId()!=null) {
						System.out.println(" publish.getCompanyId() "+publish.getCompanyId());
					    company=companyRepository.findOne(publish.getCompanyId());
					}
					
					
//					if(publish.getCompanyId()!=null && publish.getUserId()!=null) {
//						publish.setUserName(company.getName());
//					}else if(publish.getUserId()!=null) {
//						publish.setUserName(user.getFirstName()+" "+user.getLastName());
//					}
					
					if(publish.getCompanyId()!=null) {
						if(company.getLogo()!=null && company.getLogo().length()!=0) {
						publish.setProfilePicture(company.getLogo());
						}
					}else if(publish.getUserId()!=null) {
						publish.setProfilePicture(user.getProfilePicture());
					}
					
					if(publish.getCompanyId()!=null) {
//						publish.setTitle(company.get);
					}else if(publish.getUserId()!=null) {
						publish.setTitle(user.getHeadline());
					}
					
//					List<CommentOnPublish> publishComments=commentOnPublishRepository.findCommentsOnPublish(publish.getId());
					List<CommentOnPublish> publishComments=commentOnPublishRepository.findByPublishIdLimit(publish.getId(), comPage);
					System.out.println(" publish.getId() "+publish.getId()+" user.getId() "+user.getId());
					LikeDislikeOnPublish like=likeDislikeOnPublishRepository.findByPublishIdAndUserId(publish.getId(), user.getId());
				    if(like!=null)
					{
					  if(like.getIsLiked().equalsIgnoreCase("true"))
						  publish.setIsLikedByUser("true");
					  if(like.getIsDisliked().equalsIgnoreCase("true"))
					  publish.setIsDislikedByUser("true");
					 }	
					
					for(CommentOnPublish comment:publishComments) {
					
						LikeOnPublishComment likeOnComment=likeOnPublishCommentRepository.findByPublishCommentIdAndUserId(comment.getId(), comment.getUserId());
						if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
						 {
							 comment.setIsLikedByUser(likeOnComment.getIsLiked());
						 }
						  
						 Users comUser=usersRepository.findById(comment.getUserId());
						 comment.setUsername(comUser.getFirstName()+" "+comUser.getLastName());
						 comment.setProfilePicture(comUser.getProfilePicture());
						 
						 if(comUser.getHeadline()!=null)
							 comment.setTitle(comUser.getHeadline());
					}
					 
					 publish.setCommentsOnPublishtList(publishComments); 
				}
				
				Collections.sort(publishs, new Comparator<Publish>()
				  { 
					  public int compare (Publish o2,Publish o1)
					  { 
						
						return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
					  } 
				  });
				
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 
				 Page<Publish> result=null;
				 if(size<=publishPost.size())
				 {
					 result=new PageImpl<>(publishs.subList(0, size), pageable, publishPost.size());
					 if(size==publishPost.size())
					 {
						 jsonobjectFormat.setEndOfList(true);
					 }
				 }
				 else {
					
					 result=new PageImpl<>(publishs.subList(0, publishs.size()), pageable, publishPost.size());
					 jsonobjectFormat.setEndOfList(true);
				 }
				 
				 jsonobjectFormat.setMessage("Company Publish Post Fetch succeessfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(result);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 jsonobjectFormat.setMessage("Publish Post Not Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setEndOfList(true);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 jsonobjectFormat.setMessage("Publish Post Not Found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setEndOfList(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	@GetMapping("/thank/page/id")
	ModelAndView thankYouPage(@RequestParam("id")Long id) {
		System.out.println("iamhere Deepak");
		ModelAndView mv=new ModelAndView();
		
		AddressBookMemberEntity member=addressBookMemberRepository.findOne(id);
		
		AddressBook addressBook=addressBookRepository.findOne(member.getGrupId());
		
		Company company=companyRepository.findOne(addressBook.getCompanyId());
		
		mv.addObject("username", member.getFirstName());
		mv.addObject("companyName", company.getName());
		mv.setViewName("Thankyou");
		return mv;
	}
	
	
	@GetMapping("/unsubscribe/page/id")
	ModelAndView unsubscribethankYouPage(@RequestParam("id")Long id) {
		System.out.println("iamhere Deepak");
		ModelAndView mv=new ModelAndView();
		
		AddressBookMemberEntity member=addressBookMemberRepository.findOne(id);
		addressBookMemberRepository.delete(id);
		
		AddressBook addressBook=addressBookRepository.findOne(member.getGrupId());
		
		Company company=companyRepository.findOne(addressBook.getCompanyId());
		
		mv.addObject("username", member.getFirstName());
		mv.addObject("companyName", company.getName());
		mv.setViewName("unsubscribe");
		return mv;
	}
	
	@DeleteMapping("publish/delete/comments")
	public ResponseEntity<String> deleteCommentsForPost(@RequestParam(value = "commentId", required=true)Long commentId) throws JsonProcessingException{
		try{
		commentOnPublishRepository.delete(commentId);
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("deleted comment successfully");
		 jsonobjectFormat.setSuccess(true);
		
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to remove post");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
}
