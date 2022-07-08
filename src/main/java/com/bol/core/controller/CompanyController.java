package com.bol.core.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.AddressBook;
import com.bol.core.dto.AddressBookMemberEntity;
import com.bol.core.dto.Company;
import com.bol.core.dto.CompanyEmployee;
import com.bol.core.dto.Experience;
import com.bol.core.dto.MemberResponse;
import com.bol.core.dto.Members;
import com.bol.core.dto.Users;
import com.bol.core.repository.AddressBookMemberRepository;
import com.bol.core.repository.AddressBookRepository;
import com.bol.core.repository.CompanyEmployeeRepository;
import com.bol.core.repository.CompanyRepository;
import com.bol.core.repository.ExperienceRepository;
import com.bol.core.repository.MembersRepository;
import com.bol.core.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Company controller",description = "used to Company,AddressBook Crud Operations ")
@RequestMapping("/api")
public class CompanyController {
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	MembersRepository membersRepository;
	
	@Autowired
	UsersRepository usersRepository; 
	
	@Autowired
	AddressBookRepository addressBookRepository; 
	
	@Autowired
	AddressBookMemberRepository addressBookMemberRepository;
	
	@Autowired
	ExperienceRepository experienceRepository;
	
	@Autowired
	CompanyEmployeeRepository companyEmployeeRepository;
	
	@PostMapping("/company/create")
	public ResponseEntity<String> saveCompany(@RequestBody Company companyEntity) throws JsonProcessingException{
		
//		try {
			
			if(companyEntity!=null) {
			
			List<Company> userExits=companyRepository.findByUserId(companyEntity.getUser_id());	
			
			if(userExits.size()==0 || userExits.isEmpty() ) {
				
			if(companyEntity.getServices()!=null && companyEntity.getServices().size()!=0) {	
			String services="";
			for(String service:companyEntity.getServices()) {
				
				services=services+service+",";
				
			}
			
			if(services.length()!=0) {
			String serviceString=services.substring(0,services.length()-1);
			companyEntity.setService(serviceString);
			}
			}	
			
			
			
			Company companyResult=companyRepository.save(companyEntity);
			
			System.out.println(" companyResult "+companyResult.toString());
			
			
			Experience expObj=new Experience();
			
			expObj.setCompanyId(companyResult.getId());
			expObj.setCompanyName(companyResult.getName());
			expObj.setCompanyLogo(companyResult.getLogo());
			expObj.setUserId(companyResult.getUser_id());
			
			experienceRepository.save(expObj);
			
			
//            List<Experience> userExperience=experienceRepository.findByUserId(companyResult.getUser_id());
            Users userObj=usersRepository.findOne(companyResult.getUser_id());
            
            System.out.println(" userObj "+userObj);
			
            userObj.setUserRole("owner");
            usersRepository.save(userObj);
            
//			for(Experience exp:userExperience) {
//				
//				exp.setCompanyId(companyResult.getId());
//				experienceRepository.save(exp);
//			}
			
			
			
			if(companyResult!=null) {
			Users user=usersRepository.findOne(companyEntity.getUser_id());
			user.setCompany_id(companyResult.getId());
			usersRepository.save(user);
			String subject="Bolstart Company Verfication";
			if(companyResult!=null) {
				
				Users user1=usersRepository.findOne(companyResult.getUser_id());
				String username=user1.getFirstName()+" "+user1.getLastName();
				
				companyVerificationEmail(companyResult.getContactEmail(),subject,username);
				
				System.out.println(" companyResult.getContactEmail() "+companyResult.getContactEmail());
				
			}
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data Save successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(companyResult);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data Not Save successfully");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData(companyEntity);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			}else {
				
                 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("User Already created Company");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData(companyEntity);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data Not Save successfully");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData(companyEntity);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
//		}catch (Exception e) {
//			// TODO: handle exception
//			
//			
//			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			 jsonobjectFormat.setMessage("Data Not Save successfully");
//			 jsonobjectFormat.setSuccess(false);
//			 jsonobjectFormat.setData("");
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//			
//		}
		
	}
	
	
	@PutMapping("/company/update")
	public ResponseEntity<String> updateCompany(@RequestBody Company companyEntity) throws JsonProcessingException{
		
//		try {
			 
			 Company company=companyRepository.findOne(companyEntity.getId());
			
			 if(companyEntity.getCompany_type()!=null) {
			 company.setCompany_type(companyEntity.getCompany_type());
			 }
			 
			 if(companyEntity.getContactEmail()!=null) {
			 company.setContactEmail(companyEntity.getContactEmail());
			 }
			 
			 if(companyEntity.getCity()!=null) {
			 company.setCity(companyEntity.getCity());
			 }
			 
			 if(companyEntity.getContactNumber()!=null) {
			 company.setContactNumber(companyEntity.getContactNumber());
			 }
			 
			 if(companyEntity.getCountry()!=null) {
			 company.setCountry(companyEntity.getCountry());
			 }
			 
			 if(companyEntity.getDescription()!=null) {
			 company.setDescription(companyEntity.getDescription());
			 }
			 
			 if(companyEntity.getIndustry()!=null) {
			 company.setIndustry(companyEntity.getIndustry());
			 }
			 
			 if(companyEntity.getIsVerified()!=null) {
			 company.setIsVerified(companyEntity.getIsVerified());
			 }
			 
			 if(companyEntity.getLogo()!=null) {
			 company.setLogo(companyEntity.getLogo());
			 }
			 
			 if(companyEntity.getName()!=null) {
			 company.setName(companyEntity.getName());
			 }
			 
			 if(companyEntity.getProfile_url()!=null) {
			 company.setProfile_url(companyEntity.getProfile_url());
			 }
			 
			 if(companyEntity.getServices()!=null && companyEntity.getServices().size()!=0) {	
					String services="";
					for(String service:companyEntity.getServices()) {
						
						services=services+service+",";
						
					}
					
					if(services.length()!=0) {
					String serviceString=services.substring(0,services.length()-1);
					company.setService(serviceString);
					company.setServices(companyEntity.getServices());
					}
				}
//			 company.setServices(companyEntity.getServices());
			 if(companyEntity.getState()!=null) {
			 company.setState(companyEntity.getState());
			 }
			 
			 if(companyEntity.getUser_id()!=null) {
			 company.setUser_id(companyEntity.getUser_id());
			 }
			 
			 if(companyEntity.getWebsiteLink()!=null) {
			 company.setWebsiteLink(companyEntity.getWebsiteLink());
			 }
			 
			 companyRepository.save(company);
			 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data Updated successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(company);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
			
//		}catch (Exception e) {
//			// TODO: handle exception
//			
//			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			 jsonobjectFormat.setMessage("Unable to Update Data");
//			 jsonobjectFormat.setSuccess(false);
//			 jsonobjectFormat.setData(companyEntity);
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//			
//		}
		
	}
	
	@GetMapping("/company/getAll")
	public ResponseEntity<String> getAllCompany() throws JsonProcessingException{
			
		try {
			 
			 List<Company> companyList=(List<Company>) companyRepository.findAll();
			 
			 System.out.println("companyList "+companyList.size());
			 
			 if(companyList!=null && companyList.size()!=0) {
			for(Company company:companyList) {	
			
			 if(company.getService()!=null && company.getService().length()!=0) {
				 
				 String serviceListArray[]=company.getService().split(",");
				 ArrayList<String> list=new ArrayList<>();
				 for(String servicesValue:serviceListArray) {
					 list.add(servicesValue);
				 }
	       			
				 company.setServices(list);
				 
			 }	
			
			 
			}	 
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(companyList);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("No Data Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				 
			 }
			
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
	
	@GetMapping("/company/getById")
	public ResponseEntity<String> getCompanyById(@RequestParam("id") Long id) throws JsonProcessingException{
			
		try {
			 
			 Company companyEntity=companyRepository.findOne(id);
			 
			 if(companyEntity!=null) {
			 
			if(companyEntity.getService()!=null && companyEntity.getService().length()!=0) {
				
				 String serviceListArray[]=companyEntity.getService().split(",");
				 ArrayList<String> list=new ArrayList<>();
				 for(String servicesValue:serviceListArray) {
					 list.add(servicesValue);
				 }
			   companyEntity.setServices(list);
				
			}	 
					 
				 
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(companyEntity);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("No Data Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				 
			 }
			
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
	
	
	
	@GetMapping("/company/searchByCompanyName")
	public ResponseEntity<String> getCompanyByCompanyName(@RequestParam("companyName") String  companyName) throws JsonProcessingException{
			
//		try {
			 
			 List<Company> companyEntity=companyRepository.findByCompanyName(companyName);
			 
			 if(companyEntity!=null) {
			for(int i=0;i<companyEntity.size();i++) { 
			if(companyEntity.get(i).getService()!=null && companyEntity.get(i).getService().length()!=0) {
				
				 String serviceListArray[]=companyEntity.get(i).getService().split(",");
				 ArrayList<String> list=new ArrayList<>();
				 for(String servicesValue:serviceListArray) {
					 list.add(servicesValue);
				 }
				 companyEntity.get(i).setServices(list);
				
			}	 
			}
					 
				 
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(companyEntity);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("No Data Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				 
			 }
			
//		}catch (Exception e) {
//			// TODO: handle exception
//			
//			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			 jsonobjectFormat.setMessage("No Data Found");
//			 jsonobjectFormat.setSuccess(false);
//			 jsonobjectFormat.setData("");
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//			
//		}
		
	}
	
	
	@DeleteMapping("/company/deleteById")
	public ResponseEntity<String> deleteCompanyById(@RequestParam("id") Long id) throws JsonProcessingException{
			
		try {
			 
			 Company companyEntity=companyRepository.findOne(id);
			 
			 if(companyEntity!=null) {
			 
			 Users user=usersRepository.findOne(companyEntity.getUser_id());	 
			
			 user.setCompany_id(null);
			 
			 usersRepository.save(user);
			 
			 companyRepository.delete(id);	 
			 	 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data Deleted successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(companyEntity);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to Delete Data");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				 
			 }
			
		}catch (Exception e) {
			// TODO: handle exception
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to Delete Data");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	
	@GetMapping("/company/getByUserId")
	public ResponseEntity<String> getCompanyByUserId(@RequestParam("userId") Long id) throws JsonProcessingException{
			
		try {
			 
			 List<Company> companyEntity=companyRepository.findByUserId(id);
			 
			 
			 if(companyEntity!=null) {
				 
				 for(Company company:companyEntity) {	
				
				if(company.getService()!=null && company.getService().length()!=0) {
					 String serviceListArray[]=company.getService().split(",");
					 ArrayList<String> list=new ArrayList<>();
					 for(String servicesValue:serviceListArray) {
						 list.add(servicesValue);
					 }
		       			
					 company.setServices(list);
					
				}	 
					
					}	 
				 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(companyEntity);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("No Data Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				 
			 }
			
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
	
	@PostMapping("/company/addMember")
	ResponseEntity<String> addMember(@RequestBody Members membersEntity) throws JsonProcessingException{
		
		try {
			
			 if(membersEntity!=null) {
			 
			Members membersExits=membersRepository.findByUserId(membersEntity.getUserId(),membersEntity.getCompanyId());
			if(membersExits==null) {
			Members memberObj=membersRepository.save(membersEntity);
            
			 if(memberObj!=null) {
            	 	
					MemberResponse memberResponseObj=new MemberResponse();
					
					Users user=usersRepository.findById(memberObj.getUserId());
					if(user!=null) {
					
						if(user.getFirstName()!=null && user.getLastName()!=null) {
						memberResponseObj.setUserName(user.getFirstName()+" "+user.getLastName());
						}else {
							memberResponseObj.setUserName("");
						}
						if(user.getProfilePicture()!=null) {
						memberResponseObj.setUserProfilePiture(user.getProfilePicture());
						}else {
							
							memberResponseObj.setUserProfilePiture("");
						}
						if(user.getHeadline()!=null) {
						memberResponseObj.setHeadLine(user.getHeadline());
						}else {
							
							memberResponseObj.setHeadLine("");
						}
						if(user.getId()!=null) {
						memberResponseObj.setUserId(user.getId());
						}else {
							memberResponseObj.setUserId(null);
						}
						memberResponseObj.setUserRole(memberObj.getUserRole());
						memberResponseObj.setCompany_id(memberObj.getCompanyId());
						Company company=companyRepository.findOne(memberObj.getCompanyId());
						memberResponseObj.setCompany_name(company.getName());
					}
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("Data Save Successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(memberResponseObj);
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);		 
			
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data Not Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			
			
//			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			 jsonobjectFormat.setMessage("Data Save successfully");
//			 jsonobjectFormat.setSuccess(true);
//			 jsonobjectFormat.setData(memberObj);
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Member Alredy Added");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to Save Data");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			
			
		} catch (Exception e) {
			// TODO: handle exception
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to Save Data");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@PostMapping("/company/getAllMembers")
	ResponseEntity<String> getAllMembers() throws JsonProcessingException{
		
		try {
			
			 List<Members> memberlist=(List<Members>) membersRepository.findAll();
			 if(memberlist!=null && memberlist.size()!=0) {
             
			 ArrayList<MemberResponse> memberResponseList=new ArrayList<MemberResponse>();	 
				 
				for(Members member:memberlist) {
					
					MemberResponse memberResponseObj=new MemberResponse();
					
					Users user=usersRepository.findById(member.getUserId());
					if(user!=null) {
					
						if(user.getFirstName()!=null && user.getLastName()!=null) {
						memberResponseObj.setUserName(user.getFirstName()+" "+user.getLastName());
						}else {
							memberResponseObj.setUserName("");
						}
						if(user.getProfilePicture()!=null) {
						memberResponseObj.setUserProfilePiture(user.getProfilePicture());
						}else {
							
							memberResponseObj.setUserProfilePiture("");
						}
						if(user.getHeadline()!=null) {
						memberResponseObj.setHeadLine(user.getHeadline());
						}else {
							
							memberResponseObj.setHeadLine("");
						}
						if(user.getId()!=null) {
						memberResponseObj.setUserId(user.getId());
						}else {
							memberResponseObj.setUserId(null);
						}
						memberResponseObj.setUserRole(member.getUserRole());
						memberResponseObj.setCompany_id(member.getCompanyId());
						memberResponseObj.setMemberId(member.getId());
						Company company=companyRepository.findOne(member.getCompanyId());
						memberResponseObj.setCompany_name(company.getName());
					}
					
					memberResponseList.add(memberResponseObj);
					
				} 
				 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(memberResponseList);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("No Data Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			
			
		} catch (Exception e) {
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
	
	
	@GetMapping("/company/getMembers")
	ResponseEntity<String> getMembers(@RequestParam("id") Long id) throws JsonProcessingException{
		
		try {
			
			 Members memberobj=membersRepository.findOne(id);
			 if(memberobj!=null) {
             	 	
					MemberResponse memberResponseObj=new MemberResponse();
					
					Users user=usersRepository.findById(memberobj.getUserId());
					if(user!=null) {
					
						if(user.getFirstName()!=null && user.getLastName()!=null) {
						memberResponseObj.setUserName(user.getFirstName()+" "+user.getLastName());
						}else {
							memberResponseObj.setUserName("");
						}
						if(user.getProfilePicture()!=null) {
						memberResponseObj.setUserProfilePiture(user.getProfilePicture());
						}else {
							
							memberResponseObj.setUserProfilePiture("");
						}
						if(user.getHeadline()!=null) {
						memberResponseObj.setHeadLine(user.getHeadline());
						}else {
							
							memberResponseObj.setHeadLine("");
						}
						if(user.getId()!=null) {
						memberResponseObj.setUserId(user.getId());
						}else {
							memberResponseObj.setUserId(null);
						}
						memberResponseObj.setUserRole(memberobj.getUserRole());
						memberResponseObj.setCompany_id(memberobj.getCompanyId());
						memberResponseObj.setMemberId(memberobj.getId());
						Company company=companyRepository.findOne(memberobj.getCompanyId());
						memberResponseObj.setCompany_name(company.getName());
					}
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("Data fetch successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(memberResponseObj);
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);		 
			
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data Not Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			
			
		} catch (Exception e) {
			// TODO: handle exception
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data Not Found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@DeleteMapping("/company/member/remove")
	ResponseEntity<String> removeMember(@RequestParam("id") Long id) throws JsonProcessingException{
		
		try {
			
			Members member=membersRepository.findOne(id);
			if(member!=null) {
				
				Users user=usersRepository.findOne(member.getUserId());
				
				user.setCompany_id(null);
				
				usersRepository.save(user);
				
				membersRepository.delete(member);
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Member Remove Successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(member);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to Remove Member");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to Remove Member");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	
	@GetMapping("/company/getAllMembersByCompanyId")
	ResponseEntity<String> getAllMembersByCompanyId(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("companyId") Long companyId) throws JsonProcessingException{
		
		try {
			
			if(size!=null){
			 }else{
				 size=10;
			 }
			if(page!=null){
			 }else{
				 page=0;
			 }
			
			Pageable pageable =new PageRequest(page, size);	
			
			 List<Members> memberlist=(List<Members>) membersRepository.findByCompanyId(companyId);
			 Page<Members> beneficiariesListObj=membersRepository.findAll(pageable);
			 if(memberlist!=null && memberlist.size()!=0) {
             
			 ArrayList<MemberResponse> memberResponseList=new ArrayList<MemberResponse>();	 
				 
				for(Members member:beneficiariesListObj) {
					
					MemberResponse memberResponseObj=new MemberResponse();
					
					Users user=usersRepository.findById(member.getUserId());
					if(user!=null) {
					
						if(user.getFirstName()!=null && user.getLastName()!=null) {
						memberResponseObj.setUserName(user.getFirstName()+" "+user.getLastName());
						}else {
							memberResponseObj.setUserName("");
						}
						if(user.getProfilePicture()!=null) {
						memberResponseObj.setUserProfilePiture(user.getProfilePicture());
						}else {
							
							memberResponseObj.setUserProfilePiture("");
						}
						if(user.getHeadline()!=null) {
						memberResponseObj.setHeadLine(user.getHeadline());
						}else {
							
							memberResponseObj.setHeadLine("");
						}
						if(user.getId()!=null) {
						memberResponseObj.setUserId(user.getId());
						}else {
							memberResponseObj.setUserId(null);
						}
						memberResponseObj.setUserRole(member.getUserRole());
						memberResponseObj.setCompany_id(member.getCompanyId());
						memberResponseObj.setMemberId(member.getId());
						Company company=companyRepository.findOne(member.getCompanyId());
						memberResponseObj.setCompany_name(company.getName());
					}
					
					memberResponseList.add(memberResponseObj);
					
				} 
				 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(memberResponseList);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 }else {
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("No Data Found");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
			
			
		} catch (Exception e) {
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
	
	
	@PostMapping("/company/createAddressBook")
	ResponseEntity<String> createAddressBook(@RequestBody AddressBook addressBookEntity) throws JsonProcessingException{
		
		try {
			
			
			if(addressBookEntity!=null) {
			
			AddressBook addressbook=addressBookRepository.save(addressBookEntity);
				
			if(addressBookEntity.getEmailId()!=null && addressBookEntity.getEmailId().size()!=0 ) {
				
			ArrayList<AddressBookMemberEntity> addressbookMember=new ArrayList<AddressBookMemberEntity>();
				
				for(AddressBookMemberEntity	 emails:addressBookEntity.getEmailId()) {
				
					AddressBookMemberEntity addressbookMemberObject=new AddressBookMemberEntity();
					
					if(emails.getUser_id()!=null) {
						addressbookMemberObject.setUser_type("BolStart User");
					}else {
						addressbookMemberObject.setUser_type("Guest User");
					}
					
					addressbookMemberObject.setFirstName(emails.getFirstName());
					addressbookMemberObject.setLastName(emails.getLastName());
					
					addressbookMemberObject.setEmailid(emails.getEmailid());
					addressbookMemberObject.setProfile_piture(emails.getProfile_piture());
					addressbookMemberObject.setUser_id(emails.getUser_id());
					addressbookMemberObject.setGrupId(addressbook.getId());
				
					AddressBookMemberEntity addressbookMemberObject1=addressBookMemberRepository.save(addressbookMemberObject);
					
					addressbookMember.add(addressbookMemberObject1);
				}
				
				
					
					addressbook.setEmailId(addressbookMember);
					
					AddressBook addressbookEntity1=addressBookRepository.save(addressbook);
					
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("Address Book Successfully Created");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(addressbookEntity1);
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
					
				
				
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Address Book Successfully Created");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(addressbook);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}	
			
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to Create Address Book");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to Create Address Book");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	
	@PutMapping("/company/updateAddressBook")
	ResponseEntity<String> updateAddressBook(@RequestBody AddressBook addressBookEntity) throws JsonProcessingException{
		
		try {
			
			
			if(addressBookEntity!=null) {
				
				AddressBook addressBook=addressBookRepository.findOne(addressBookEntity.getId());
			
			if(addressBook!=null) {
				
				if(addressBookEntity.getGroupName()!=null) {
					addressBook.setGroupName(addressBookEntity.getGroupName());
				}
				
				if(addressBookEntity.getCompanyId()!=null) {
					addressBook.setCompanyId(addressBookEntity.getCompanyId());
				}
				
			}	
			
			if(addressBookEntity.getEmailId()!=null && addressBookEntity.getEmailId().size()!=0 ) {
				
				ArrayList<AddressBookMemberEntity> addressbookMember=new ArrayList<AddressBookMemberEntity>();
				
				addressBookMemberRepository.deleteAllByGroupId(addressBook.getId());
				
				for(AddressBookMemberEntity emails:addressBookEntity.getEmailId()) {
					
					AddressBookMemberEntity addressbookMemberObject=new AddressBookMemberEntity();
					
					if(emails.getUser_id()!=null) {
						addressbookMemberObject.setUser_type("BolStart User");
					}else {
						addressbookMemberObject.setUser_type("Guest User");
					}
					
					
					
					addressbookMemberObject.setFirstName(emails.getFirstName());
					addressbookMemberObject.setLastName(emails.getLastName());
					
					addressbookMemberObject.setEmailid(emails.getEmailid());
					addressbookMemberObject.setProfile_piture(emails.getProfile_piture());
					addressbookMemberObject.setUser_id(emails.getUser_id());
					addressbookMemberObject.setGrupId(addressBook.getId());
				
					AddressBookMemberEntity addressbookMemberObject1=addressBookMemberRepository.save(addressbookMemberObject);
					
					addressbookMember.add(addressbookMemberObject1);
					
					
					
				}
			
				addressBook.setEmailId(addressbookMember);
			}else {
				
				addressBookMemberRepository.deleteAllByGroupId(addressBook.getId());
				
				ArrayList<AddressBookMemberEntity> addressmember=new ArrayList<AddressBookMemberEntity>();
				
				addressBook.setEmailId(addressmember);
				
			}
			
				AddressBook addressbook=addressBookRepository.save(addressBook);
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Address Book Updated Successfully ");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(addressbook);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to Update Address Book");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to Update Address Book");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	
	@GetMapping("/company/addressBook/getByCompanyId")
	ResponseEntity<String> getAddressBookByCompanyId(@RequestParam("companyid") Long companyid) throws JsonProcessingException{
		
		try {
			
			
			if(companyid!=null && companyid!=0) {
				
			List<AddressBook> addressBookList=addressBookRepository.findByCompanyId(companyid);
			
			if(addressBookList!=null && addressBookList.size()!=0 ) {
				
				for(int i=0;i<addressBookList.size();i++) {
					
				ArrayList<AddressBookMemberEntity> addressBookMemberList=addressBookMemberRepository.getByGroupId(addressBookList.get(i).getId()); 
					
				addressBookList.get(i).setEmailId(addressBookMemberList); 
				
			  }
			}
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data fetch Successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(addressBookList);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to fetch Data");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
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
	
	@GetMapping("/company/addressBook/getById")
	ResponseEntity<String> getAddressBookById(@RequestParam("id") Long id) throws JsonProcessingException{
		
		try {
			
			
			if(id!=null) {
				
			AddressBook addressBookList=addressBookRepository.findOne(id);
			
			System.out.println(" addressBookList "+addressBookList);
			
			if(addressBookList!=null) {

				
				ArrayList<AddressBookMemberEntity> addressbookMember1=addressBookMemberRepository.getByGroupId(addressBookList.getId());
				
				addressBookList.setEmailId(addressbookMember1);
			  
			}
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data fetch Successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(addressBookList);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to fetch Data");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
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
	
	@GetMapping("/company/addressBook/getAll")
	ResponseEntity<String> getAllAddressBook() throws JsonProcessingException{
		
		try {
			
				
			List<AddressBook> addressBookList=(List<AddressBook>) addressBookRepository.findAll();
		
			if(addressBookList!=null && addressBookList.size()!=0 ) {
				
			for(int i=0;i<addressBookList.size();i++) {
					
                ArrayList<AddressBookMemberEntity> addressBookMemberList=addressBookMemberRepository.getByGroupId(addressBookList.get(i).getId()); 
				
               
                
				addressBookList.get(i).setEmailId(addressBookMemberList); 
				
			  }
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Data fetch Successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(addressBookList);
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
			}else {
				
                 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data Not Found");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(addressBookList);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
				
			}
		
		} catch (Exception e) {
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
	
	
	@DeleteMapping("/company/addressBook/delete")
	ResponseEntity<String> deleteAddressBookById(@RequestParam("id") Long id) throws JsonProcessingException{
		
		try {
			
			
			if(id!=null) {
				
			AddressBook addressBookList=addressBookRepository.findOne(id);
			
			if(addressBookList!=null) {
				
				addressBookList.setEmailId(addressBookMemberRepository.getByGroupId(addressBookList.getId()));
				addressBookMemberRepository.deleteAllByGroupId(addressBookList.getId());

				addressBookRepository.delete(id);
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Address Book Remove Successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(addressBookList);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			  
			}else {
				
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to delete Data");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
				
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to delete Data");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to delete Data");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	@GetMapping("/company/allEmployeeList")
	ResponseEntity<String> companyEmployeeList(@RequestParam("companyId") Long companyId) throws JsonProcessingException{
			List<Experience> emplListAsExp=experienceRepository.findEmployeeList(companyId);
			if(emplListAsExp!=null && emplListAsExp.size()!=0) {
				ArrayList<Users> userArrayList=new ArrayList<Users>();	
				
				for(Experience exp:emplListAsExp) {
					if(exp.getIsWorking()) {
					Users userList=usersRepository.findById(exp.getUserId());
					if(userList!=null){
						userArrayList.add(userList);
					}
					}else if(!exp.getIsWorking()){
						Users userList=usersRepository.findUserByRoleOwner(exp.getUserId());
						if(userList!=null){
							userArrayList.add(userList);
						}
					}
				}	
			
			
			if(userArrayList!=null && userArrayList.size()!=0) {
				
				HashSet<Users> userRemoveDoublicate=new HashSet<Users>();
				
				for(Users userList:userArrayList) {
					userRemoveDoublicate.add(userList);
				}
				
				
				List<Users> finalListArray=new ArrayList<Users>(userRemoveDoublicate);
				List<Users> listToBeShared=new ArrayList<Users>();
				List<Users> ownersList= new ArrayList<Users>();
				List<Users> adminList= new ArrayList<Users>();
				List<Users> employeesList= new ArrayList<Users>();
				
				for(Users users:finalListArray){
					if(users.getUserRole().contains("owner")){
						ownersList.add(users);
					}else if(users.getUserRole().contains("Admin")){
						adminList.add(users);
					}else{
						if(users.getUserRole().contains("employee") ||  users.getUserRole().contains("Employee")){
							 employeesList.add(users);
						}else{
							 users.setUserRole("Employee");
							 employeesList.add(users);
						}
					}
					
					
				}
				
				listToBeShared.addAll(ownersList);
				listToBeShared.addAll(adminList);
				listToBeShared.addAll(employeesList);
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Data fetch Successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(listToBeShared);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				List<CompanyEmployee> companyEmployee=companyEmployeeRepository.findByCompanyId(companyId);
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("No Data Fond");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
			}else {
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("No Data Fond");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		
	}
	
	@GetMapping("/company/removeEmployee")
	ResponseEntity<String> removeCompanyEmployee(@RequestParam("userId") Long userId,@RequestParam("companyId") Long companyId) throws JsonProcessingException{
		
//		try {
			
			Users userObj=usersRepository.findOne(userId);
			
			System.out.println(" userObj "+userObj);
			
			if(userObj!=null) {
				
				List<Experience> expList=experienceRepository.findByUserIdAndCompanyId(userObj.getId(),companyId);
				
				if(expList!=null && expList.size()!=0) {
					
					for(Experience exp:expList) {
						
//						if(exp.getIsWorking()==true) {
						
						List<CompanyEmployee> comEmpObjList=companyEmployeeRepository.findByUserIdAndCompanyId(userId,companyId);
						
						for(CompanyEmployee emp:comEmpObjList) {
						companyEmployeeRepository.delete(emp);
						}
						exp.setCompanyId(null);
						exp.setIsWorking(false);
						
						experienceRepository.save(exp);
						
						
//						}
						
					}
					
					userObj.setUserRole(null);
					userObj.setCompany_id(null);
					
					Users uers1=usersRepository.save(userObj);
					
                     JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("Employee Removed Successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(userObj);
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
					
					
				}else {
					
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("Unable to Remove Employee");
					 jsonobjectFormat.setSuccess(false);
					 jsonobjectFormat.setData("");
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
					
				}
				
			}else {
				
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to Remove Employee");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
//		} catch (Exception e) {
//			// TODO: handle exception
//			
//			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			 jsonobjectFormat.setMessage("Unable to Remove Employee");
//			 jsonobjectFormat.setSuccess(false);
//			 jsonobjectFormat.setData("");
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//			
//		}
		
	}
	
	@PostMapping("/company/addEmployeeUser")
	ResponseEntity<String> assignEmployeeRole(@RequestParam("userid") Long userId,@RequestParam("userRole") String userRole,@RequestParam("companyId") Long companyId) throws JsonProcessingException{
		
		try {
			Users user=usersRepository.findOne(userId);
			
			if(user!=null) {
				if(userRole!=null) {
				
				
				user.setUserRole(userRole);
				if(userRole.equalsIgnoreCase("Admin")) {
				user.setCompany_id(companyId);
				}
				usersRepository.save(user);
				
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 
				 jsonobjectFormat.setMessage("User Role Has Been Updated");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(user);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
				}else {
					
					JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("User Role Can't Null");
					 jsonobjectFormat.setSuccess(false);
					 jsonobjectFormat.setData("");
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
					
				}
				
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable to update User Role");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to update User Role");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	@GetMapping("/company/getEmployeeByRole")
	ResponseEntity<String> getEmpByUserId(@RequestParam("userRole") String userRole) throws JsonProcessingException{
		
		try {
			
			if(userRole!=null) {
				
				List<Users> userList=usersRepository.findByUserRole(userRole); 
				
				if(userList!=null && userList.size()!=0) {
					
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("Employee fetch Successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(userList);
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}else {
					
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("No Data Found");
					 jsonobjectFormat.setSuccess(false);
					 jsonobjectFormat.setData("");
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(Obj);
				 	 return ResponseEntity.ok().body(customExceptionStr);
					
				}
				
				
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("User Role Can't Null");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("Unable to fetch Employee");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	
	void companyVerificationEmail(String TO, String SUBJECT,String MESSAGE) {
		   
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
	        		"						  <body>" + 
	        		"						<h4>Hi "+MESSAGE+",</h4>" + 
	        		
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
	        msg.addRecipient(Message.RecipientType.TO,new InternetAddress(TO));
	        msg.addRecipient(RecipientType.BCC,new InternetAddress("reachout@bolstart.com"));
	       // msg.addRecipient(Message.RecipientType.BCC,new InternetAddress("reachout@bolstart.com"));
	        msg.setSubject(SUBJECT);
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
	            transport.send(msg);
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
	
	@PostMapping("/company/addAddressBookMember")
	ResponseEntity<String> addAddressBookMember(@RequestParam("groupId") Long groupId,@RequestParam("userId") Long userId,@RequestBody ArrayList<AddressBookMemberEntity> addressBokMember) throws JsonProcessingException{
		
//		try {
			
			if(groupId!=null) {
			
			AddressBook addressbook=addressBookRepository.findOne(groupId);
			
			Users user=usersRepository.findOne(userId);
			
			Company companyObj=companyRepository.findOne(addressbook.getCompanyId());
				
			if(addressBokMember!=null && addressBokMember.size()!=0 ) {
			String subject="Bolstart Address Book";	
			ArrayList<AddressBookMemberEntity> addressbookMember=new ArrayList<AddressBookMemberEntity>();
				
				for(AddressBookMemberEntity	 emails:addressBokMember) {
				
					AddressBookMemberEntity addressbookMemberObject=new AddressBookMemberEntity();
					
					if(emails.getUser_id()!=null) {
						addressbookMemberObject.setUser_type("BolStart User");
					}else {
						addressbookMemberObject.setUser_type("Guest User");
					}
					
					addressbookMemberObject.setFirstName(emails.getFirstName());
					addressbookMemberObject.setLastName(emails.getLastName());
					
					addressbookMemberObject.setEmailid(emails.getEmailid());
					addressbookMemberObject.setProfile_piture(emails.getProfile_piture());
					addressbookMemberObject.setUser_id(emails.getUser_id());
					addressbookMemberObject.setGrupId(addressbook.getId());
				
					AddressBookMemberEntity addressbookMemberObject1=addressBookMemberRepository.save(addressbookMemberObject);
					String fullName=user.getFirstName()+" "+user.getLastName();
					customEmailAddressBook(emails.getEmailid(),subject,"",emails.getFirstName(),addressbookMemberObject1.getId(),fullName,companyObj.getName(),addressbookMemberObject1.getId());
					
//					customEmailAddressBook(String TO, String SUBJECT,String MESSAGE,String userName,Long userId,String ownerName,String companyName);
					addressbookMember.add(addressbookMemberObject1);
				}
		
					addressbook.setEmailId(addressbookMember);
					
//					AddressBook addressbookEntity1=addressBookRepository.save(addressbook);
					
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 
					 jsonobjectFormat.setMessage("Address Book Member added Successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(addressbook);
			         
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}else {
				
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Address Book Successfully Created");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(addressbook);
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}	
			
				
			}else {
				
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 
				 jsonobjectFormat.setMessage("Unable add member into Address Book");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
		         
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
				
			}
			
//		} catch (Exception e) {
//			// TODO: handle exception
//			
//			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//			 
//			 jsonobjectFormat.setMessage("Unable to Create Address Book");
//			 jsonobjectFormat.setSuccess(false);
//			 jsonobjectFormat.setData("");
//	         
//			 ObjectMapper Obj = new ObjectMapper(); 
//	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		 	 return ResponseEntity.ok().body(customExceptionStr);
//			
//		}
		
	}
	
	
	void customEmailAddressBook(String TO, String SUBJECT,String MESSAGE,String userName,Long userId,String ownerName,String companyName,Long memberId) {
		   
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
	        		"body {"+
	        		"	  color: blue;"+
	        		"	}"+
	        		"						  </style>\r\n" + 
	        		"						  </head> \r\n" + 
	        		"						  <body >\r\n" + 
	        		"						<h4>Hi "+userName+",</h4>\r\n" + 
	        		"\r\n" + 
	        		"                        <p>Hope you are doing well. "+ownerName+" of "+companyName+", has added you in their address book to share their company weekly traction updates with you." + 
	        		"                         <p>  If you wish to stay updated with "+ownerName+" of "+companyName+"&#180;s weekly updates,<a href=\"https://api.bolstart.com/bolstart/api/thank/page/id?id="+memberId+"\"> click on this link.</a></p> " + 
	        		"                        And if you find it is spam or if you are not interested, you can <a href=\"https://api.bolstart.com/bolstart/api/unsubscribe/page/id?id="+memberId+"\"> unsubscribe </a>from their list.</p>\r\n" + 
	        		"\r\n" + 
	        		"<br>                      <p>Regards,<br>\r\n" + 
	        		"\r\n" + 
	        		"                      Bolstart Team</p>\r\n" + 
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
	        msg.setSubject(SUBJECT);
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
	
	@PostMapping("/company/admin/addEmployee")
	public ResponseEntity<String> adminAddEmployee(@RequestBody CompanyEmployee employeeEntity) throws JsonProcessingException{
		try {
			if(employeeEntity!=null && employeeEntity.getUserId()!=null && employeeEntity.getCompanyId()!=null) {
				Users users=usersRepository.findOne(employeeEntity.getUserId());
				if(users!=null) {
						Company company=companyRepository.findOne(employeeEntity.getCompanyId());
						if(company!=null) {
								//verify if user is current employee of some other company or not
								List<Experience> listOfExperienceForUser=experienceRepository.findByUserId(employeeEntity.getUserId());
								boolean shouldWeSaveExp=validateUsersExperience(listOfExperienceForUser);
								if(shouldWeSaveExp){
									CompanyEmployee employee=companyEmployeeRepository.save(employeeEntity);
									Experience exp=new Experience();
									exp.setCompanyName(company.getName());
									exp.setCompanyLogo(company.getLogo());
									exp.setCompanyId(employee.getCompanyId());
									exp.setUserId(employee.getUserId());
									exp.setIsWorking(true);
									exp.setTitle(employee.getTitle());
									exp.setStartMonth(employee.getStartMonth());
									exp.setStartYear(employee.getStartYear());
									experienceRepository.save(exp);
									if(employee!=null) {
											Users users1=usersRepository.findOne(employee.getUserId());
											users1.setUserRole("Employee");
											users1.setCompany_id(employee.getCompanyId());
											Users users2=usersRepository.save(users1);
											JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
											 jsonobjectFormat.setMessage("Employee Added Successfully");
											 jsonobjectFormat.setSuccess(true);
											 jsonobjectFormat.setData(users2);
											 ObjectMapper Obj = new ObjectMapper(); 
									         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
										 	 return ResponseEntity.ok().body(customExceptionStr);
									}else {
										JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
										 jsonobjectFormat.setMessage("Unable to add Employee");
										 jsonobjectFormat.setSuccess(false);
										 jsonobjectFormat.setData("");
										 ObjectMapper Obj = new ObjectMapper(); 
								         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
									 	 return ResponseEntity.ok().body(customExceptionStr);
									}
								}else {
									JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
									 jsonobjectFormat.setMessage("Unable to add user as it seems he is already associated with some other organisation");
									 jsonobjectFormat.setSuccess(false);
									 jsonobjectFormat.setData("");
									 ObjectMapper Obj = new ObjectMapper(); 
							         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
								 	 return ResponseEntity.ok().body(customExceptionStr);
								}
						}else {
							JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
							 jsonobjectFormat.setMessage("Enter Company Id "+employeeEntity.getCompanyId()+" Company not present");
							 jsonobjectFormat.setSuccess(false);
							 jsonobjectFormat.setData("");
							 ObjectMapper Obj = new ObjectMapper(); 
					         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
						 	 return ResponseEntity.ok().body(customExceptionStr);
						}
				}else {
					JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 jsonobjectFormat.setMessage("Enter User Id "+employeeEntity.getUserId()+" User not present");
					 jsonobjectFormat.setSuccess(false);
					 jsonobjectFormat.setData("");
					 ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}
			}else {
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Unable to add Employee");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setData("");
				 ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		} catch (Exception e) {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Unable to fetch Employee");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}

	public boolean validateUsersExperience(List<Experience> listOfExperienceForUser){
		boolean response=true;
		for(int i=0;i<listOfExperienceForUser.size();i++){
			Experience experience=listOfExperienceForUser.get(i);
			if(experience.getIsWorking()){
				response=false;
				return response;
			}
			
			if((!(experience.getEndMonth()!=null)) || (!experience.getEndMonth().trim().equals(""))){
				response=false;
				return response;
			}
		}
		return response;
	}
}
