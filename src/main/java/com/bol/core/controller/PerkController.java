package com.bol.core.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.Users;
import org.springframework.web.bind.annotation.RestController;

import com.bol.core.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Perk controller",description = "used for Perk Mail Send ")
@RequestMapping("/api")
public class PerkController {
	
	@Autowired
	UsersRepository usersRepository;
	
	@PostMapping("/perk/user")
	ResponseEntity<String> perkMail(@RequestParam("userId") Long userId,@RequestParam("perkProviderName") String perkProviderName,@RequestParam("perkAmount") float perkAmount) throws JsonProcessingException{
		
         try {
			Users users=usersRepository.findOne(userId);
			
			if(users!=null) {
				
				String subject="Bolstart Company Verfication";
				 customPerkEmail(users.getEmailId(),subject,"",users.getFirstName(),perkProviderName);
				
//				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//				 
//				 jsonobjectFormat.setMessage("Unable to Send Mail");
//				 jsonobjectFormat.setSuccess(true);
//				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString("Mail Send Successfully");
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("UserId Null Unable to Send Mail");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to Send Mail");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}		
		
	}
	
	
	
	void customPerkEmail(String TO, String SUBJECT,String MESSAGE,String userName,String perkProviderName) {
		   
		 try {  
		// Replace sender@example.com with your "From" address.
	       // This address must be verified.
	       final String FROM = "reachout@bolstart.com";
	       
	        final String FROMNAME = "Bolstart Notifications";
	        
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
	        		"						  <body>\r\n" + 
	        		"						  \r\n" + 
	        		"						  \r\n" + 
	        		"						<h4>Hi "+userName+"</h4>\r\n" + 
	        		"\r\n" + 
	        		"\r\n" + 
	        		"                        <p>Congratulations on availing the perk from Bolstart. <br>\r\n" + 
	        		"						We are pleased that <b>"+perkProviderName+"</b> is ready to bolster your company&#180;s growth to the next level.<br><br>\r\n" + 
	        		"\r\n" + 
	        		"                           Please reply to this email confirming you want to avail the perk within 48 hours. We typically respond within 24 hours with links to avail the perk. Ignore this email if you&#180;ve changed your mind.\r\n" + 
	        		"\r\n" + 
	        		"                           Keep exploring our perks page as we are on our mission of “Bolstering the startup ecosystem!” by adding more perks on our platform.</p><br>\r\n" + 
	        		"\r\n" + 
	        		"\r\n" + 
	        		"                       <p>Cheers,<br>\r\n" + 
	        		"                       Bolstart Team<br><br>\r\n" + 
	        		"\r\n" + 
	        		"					   \r\n" + 
	        		"					   \r\n" + 
	        		"					  \r\n" + 
	        		"\r\n" + 
	        		"\r\n" + 
	        		"\r\n" + 
	        		"\r\n" + 
	        		"\r\n" + 
	        		"						  \r\n" + 
	        		"						  \r\n" + 
	        		"						  </body\r\n" + 
	        		"						  \r\n" + 
	        		"</html>";
		   
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
	        msg.addRecipient(RecipientType.BCC, new InternetAddress(
//	                "partners@bolstart.com"));
	        		 "partners@bolstart.com"));
	        msg.setSubject("Redeeming perk on Bolstart");
	        msg.setContent(BODY,"text/html");
	        
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
	
	

}
