package com.bol.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bol.core.dto.SMSObjectResponse;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class SendOTPSmsNotifications {

	@Value("${SMSGatewayTwoBalance}")
	private String getBalanceUrl;
	
	@Value("${SMSGatewayTwoSendUrl}")
	private String sendingOTPUrl;
	
	@Value("${SMSGatewayTwoVerifyUrl}")
	private String verifyOTPUrl;
	
	@Value("${SMSGatewayTwoGeneralSMS}")
	private String generalSMS;
	
	@Autowired
	JSONToObjectMapper jsonToObjectMapper;
	
	@Autowired
	SMSObjectResponse smsObjectResponse;
	
	

	/*
	 * 
	 * Send otp to user using username
	 * 
	 */
	public String sendOtp(String mobileNumber,Pageable pageable){
		String newURLForInvokingSMS=sendingOTPUrl;
		String [] splitRecordBasedOnDelimeter=newURLForInvokingSMS.split("phone_number");
		String finalURL=splitRecordBasedOnDelimeter[0]+mobileNumber+""+splitRecordBasedOnDelimeter[1];
		String response=invokeURL(finalURL);
		return response;
		
	}
	
	

/*	public ResponseEntity<String>  verifyOtpOnEmailId(String emailId,String otpUserEntered,Pageable pageable) throws JsonProcessingException{
		
		try{
		Page<UserMaster> userMaster=userMasterRepository.findByEmailId(emailId,pageable) ;
		UserMaster userMasterObj= userMaster.getContent().get(0);
		String otp=userMasterObj.getOtp();
		if(otp.equals(otpUserEntered)){
			userMasterObj.setOtp("");
			userMasterRepository.save(userMasterObj);
			Page<UserMaster> companyRegistrationDetails=userMasterRepository.findByEmailId(emailId,pageable) ;
			ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(companyRegistrationDetails);
			return ResponseEntity.ok().body(customExceptionStr);
		}else{
			CustomParameterizedException customException=new CustomParameterizedException("Otp not matching please enter valid otp", "500");
	 		ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}
		}catch(Exception e){
			CustomParameterizedException customException=new CustomParameterizedException("User registered but unable to send otp", "500");
	 		ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}
}
*/
	
	
	/*
	 * 
	 * Resttemplpate to invoke send otp and verification url.
	 * 
	 */
	public String invokeURL(String url){
		 RestTemplate restTemplate = new RestTemplate();
		    String result = restTemplate.getForObject(url, String.class);
		    System.out.println("result : "+result);
		    return result;
	}
	 

/*	public ResponseEntity<String> verifyOtpEnteredByUserUsingEmailId(Long mobileNumber,String otp,Pageable pageable) throws JsonProcessingException{
		Page<CompanyRegistration> companyRegistration=companyRegistrationRepository.findByMobileNumber(mobileNumber,pageable) ;
		CompanyRegistration companyRegistrationObj= companyRegistration.getContent().get(0);
		String details=companyRegistrationObj.getOtp();
		
		if(!details.equals("")){
		String newURLForInvokingSMS=verifyOTPUrl;
		String [] splitRecordBasedOnDelimeter=newURLForInvokingSMS.split("session_id");
		String finalURL=splitRecordBasedOnDelimeter[0]+details+""+splitRecordBasedOnDelimeter[1];
		String [] splitRecordBasedOnDelimeter2=finalURL.split("otp_input");
		String otpValue=splitRecordBasedOnDelimeter2[0]+otp;
		String response="";
		try {
			 response=invokeURL(otpValue);
			smsObjectResponse=jsonToObjectMapper.jsonToObjectMapper(response);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			CustomParameterizedException customException=new CustomParameterizedException("Please enter valid OTP", "500");
	 		ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}
		
		String detailsResponse=smsObjectResponse.getDetails();
		if(smsObjectResponse.getStatus().equalsIgnoreCase("Success")){
			companyRegistrationObj.setOtp("");  //Erasing OTP
			companyRegistrationObj.setVerified(1);
			companyRegistrationRepository.save(companyRegistrationObj);
			
			Page<CompanyRegistration> companyRegistrationDetails=companyRegistrationRepository.findByMobileNumber(mobileNumber,pageable) ;
			ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(companyRegistrationDetails);
			return ResponseEntity.ok().body(customExceptionStr);
		}else{
			ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(response);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}
	   }else{
		   
		   smsObjectResponse.setDetails("OTP Already Verified");
		   smsObjectResponse.setStatus("Success");
		   String jsonResponse = null;
		try {
			jsonResponse = jsonToObjectMapper.objectToJson(smsObjectResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectMapper Obj = new ObjectMapper(); 
		 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonResponse);
		return ResponseEntity.ok().body(customExceptionStr);
	   }
	}
	
	*/
	
	
	public String verifyOtpEnteredByUser(String mobileNumber,String otp,String otpKey,Pageable pageable){
	String details=otpKey;
	if(!details.equals("")){
		String newURLForInvokingSMS=verifyOTPUrl;
		String [] splitRecordBasedOnDelimeter=newURLForInvokingSMS.split("session_id");
		String finalURL=splitRecordBasedOnDelimeter[0]+details+""+splitRecordBasedOnDelimeter[1];
		String [] splitRecordBasedOnDelimeter2=finalURL.split("otp_input");
		String otpValue=splitRecordBasedOnDelimeter2[0]+otp;
		String response=invokeURL(otpValue);
		return response;
   }else{
		   smsObjectResponse.setDetails("Enter valid OTP key");
		   smsObjectResponse.setStatus("500");
		   String jsonResponse = null;
		try {
			jsonResponse = jsonToObjectMapper.objectToJson(smsObjectResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return jsonResponse;
	   }
	}
	
	
	
}
