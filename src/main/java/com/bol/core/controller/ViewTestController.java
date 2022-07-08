package com.bol.core.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bol.core.dto.Company;
import com.bol.core.dto.Connections;
import com.bol.core.dto.Post;
import com.bol.core.dto.Users;
import com.bol.core.repository.CompanyRepository;
import com.bol.core.repository.ConnectionsRepository;
import com.bol.core.repository.PostRepository;
import com.bol.core.repository.UsersRepository;





@Controller
public class ViewTestController {

	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	PostRepository PostRepository;
	
	@Autowired
	ConnectionsRepository ConnectionsRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	private String email1="piyush@bolstart.com";
	private String email2="rushabhshah92011@gmail.com";
	
	private boolean isLoginFailed=false;
	private boolean isLogoutSuccess=false;
	private boolean isLoginSuccess=false;
	private boolean isVerifiedSuccess=false;
	private boolean isRejectedSuccess=false;
	
	@GetMapping("/dashboard")
	public String getDashboard(Model model,HttpSession session)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
		List<Users> users=(List<Users>) usersRepository.findAll();
		List<Post> posts=(List<Post>) PostRepository.findAll();
		List<Connections> connections=ConnectionsRepository.findPendingRequests();
		model.addAttribute("userCount",users.size());
		model.addAttribute("postCount",posts.size());
		String username=(String) session.getAttribute("username");
		Users piyushUser=usersRepository.findByEmailId(email1);
		Users rushabhUser=usersRepository.findByEmailId(email2);
		if((piyushUser.getEmailId()+" "+piyushUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",piyushUser.getFirstName()+" "+piyushUser.getLastName());
			model.addAttribute("image",piyushUser.getProfilePicture());
		}else if((rushabhUser.getEmailId()+" "+rushabhUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",rushabhUser.getFirstName()+" "+rushabhUser.getLastName());
			model.addAttribute("image",rushabhUser.getProfilePicture());
		}
		if(isLoginSuccess)
		{
			model.addAttribute("success","login successfull");
			isLoginSuccess=false;
		}
		model.addAttribute("requestCount", connections.size());
		return "dashboard";
	}
	
	@GetMapping("/login")
	public String getLogiPage(Model model)
	{
		if(isLoginFailed)
		{
			model.addAttribute("errormsg","invalid email or password");
			isLoginFailed=false;
		}else if(isLogoutSuccess)
		{
			model.addAttribute("success","logout success");
			isLogoutSuccess=false;
		}
		
		return "login";
	}
	
	
	@PostMapping("/login/authenticate")
	public String getLoginPage(Model model,HttpSession session,@RequestParam("email")String email,@RequestParam("password")String password)
	{
		if((email.equals(email1))||(email.equals(email2)))
		{
			Users user=usersRepository.findByEmailId(email);
			if(user!=null)
			{
				if(password.equals(user.getPassword()))
				{
					sessionMaintain(session, email, password);
					isLoginSuccess=true;
					return "redirect:/dashboard";
				}
			}
		}
		isLoginFailed=true;
	
		return "redirect:/login";
	}
	
	
	@GetMapping("/startupVerification")
	public String getStartupVerificationPage(HttpSession session,Model model)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		String username=(String) session.getAttribute("username");
		Users piyushUser=usersRepository.findByEmailId(email1);
		Users rushabhUser=usersRepository.findByEmailId(email2);
		if((piyushUser.getEmailId()+" "+piyushUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",piyushUser.getFirstName()+" "+piyushUser.getLastName());
			model.addAttribute("image",piyushUser.getProfilePicture());
		}else if((rushabhUser.getEmailId()+" "+rushabhUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",rushabhUser.getFirstName()+" "+rushabhUser.getLastName());
			model.addAttribute("image",rushabhUser.getProfilePicture());
		}
		if(isRejectedSuccess)
		{
			model.addAttribute("errorMsg","rejected Comapny successfully");
			isRejectedSuccess=false;
		}if(isVerifiedSuccess)
		{
			model.addAttribute("success","verified Company successfully");
			isVerifiedSuccess=false;
		}
		List<Company> company=companyRepository.findNotApproved();
		System.out.println("company "+company.toString());
		model.addAttribute("company",company);
		return "startup-verification";
	}
	
	@RequestMapping(value="/verify",method = RequestMethod.GET)
	public String verifyUser(HttpSession session,Model model,@RequestParam("id")Long id)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		Users user=usersRepository.findById(id);
		user.setIsMobileVerified("true");
		usersRepository.save(user);
		isVerifiedSuccess=true;
		return "redirect:/startupVerification";
	}
	
	
	@RequestMapping(value="/verifyCompany",method = RequestMethod.GET)
	public String verifyCompany(HttpSession session,Model model,@RequestParam("id")Long id)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		Company company=companyRepository.findOne(id);
		company.setIsVerified("approved");
		companyRepository.save(company);
		isVerifiedSuccess=true;
		return "redirect:/startupVerification";
	}
	
	
	@RequestMapping(value="/reject",method = RequestMethod.GET)
	public String rejectUser(HttpSession session,Model model,@RequestParam("id")Long id)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		Users user=usersRepository.findById(id);
		user.setIsMobileVerified(null);
		usersRepository.save(user);
		isRejectedSuccess=true;
		return "redirect:/startupVerification";
	}
	
	@RequestMapping(value="/rejectCompany",method = RequestMethod.GET)
	public String rejectComapny(HttpSession session,Model model,@RequestParam("id")Long id)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		Company company=companyRepository.findOne(id);
		company.setIsVerified("reject");
		companyRepository.save(company);
		isRejectedSuccess=true;
		return "redirect:/startupVerification";
	}
	
	
	@GetMapping("/usersData/{page}")
	public String getUsersData(@PathVariable("page") String page,HttpSession session,Model model)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		String username=(String) session.getAttribute("username");
		Users piyushUser=usersRepository.findByEmailId(email1);
		Users rushabhUser=usersRepository.findByEmailId(email2);
		if((piyushUser.getEmailId()+" "+piyushUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",piyushUser.getFirstName()+" "+piyushUser.getLastName());
			model.addAttribute("image",piyushUser.getProfilePicture());
		}else if((rushabhUser.getEmailId()+" "+rushabhUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",rushabhUser.getFirstName()+" "+rushabhUser.getLastName());
			model.addAttribute("image",rushabhUser.getProfilePicture());
		}
		if(isRejectedSuccess)
		{
			model.addAttribute("errorMsg","rejected user successfully");
			isRejectedSuccess=false;
		}if(isVerifiedSuccess)
		{
			model.addAttribute("success","verified user successfully");
			isVerifiedSuccess=false;
		}
		System.out.println("Page"+page);
		Pageable page1=new PageRequest(Integer.parseInt(page), 25);
	
		Page<Users> users= usersRepository.findAllByOrderByIdDesc(page1);
		
		model.addAttribute("users",users.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", users.getTotalPages());
		
		
		return "users-data";
	}
	
	@GetMapping("/logout")
	public String getLoginPage(Model model,HttpSession session)
	{
		System.out.println("logout success");
		session.invalidate();
		isLogoutSuccess=true;
		return "redirect:/login";
	}
	
	private static void sessionMaintain(HttpSession session,String email ,String password)
	{
		session.setAttribute("username", email+" "+password);
		
	}
	
	@RequestMapping("/downloadUsersInExcel")
	public void downloadUser(HttpServletResponse response) throws IOException{
		System.out.println("I am Here");
		 try{
			 
			 response.setContentType("application/octet-stream");
			 response.setHeader("Content-Disposition","attchment; filename=registeredUser.xlsx");
			  
			 Workbook workbook=new  XSSFWorkbook();
			 
			 Sheet sheet=workbook.createSheet("registeredUser");
			 
			 Row row=sheet.createRow(0);
			 
			 CellStyle headerCellStyle = workbook.createCellStyle();
			 
			 headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
//			 headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			 
			 Cell cell=row.createCell(0);
			 cell.setCellValue("Sr");
			 cell.setCellStyle(headerCellStyle);
			 
			 cell=row.createCell(1);
			 cell.setCellValue("Id");
			 cell.setCellStyle(headerCellStyle);
			 
			 
			 cell=row.createCell(2);
			 cell.setCellValue("First Name");
			 cell.setCellStyle(headerCellStyle);
			 
			 cell=row.createCell(3);
			 cell.setCellValue("Last Name");
			 cell.setCellStyle(headerCellStyle);
			 
			 		
				 cell=row.createCell(4);
				 cell.setCellValue("Email Id");
				 cell.setCellStyle(headerCellStyle);
				
				 cell=row.createCell(5);
				 cell.setCellValue("Mobile Number");
				 cell.setCellStyle(headerCellStyle);
		
				 cell=row.createCell(6);
				 cell.setCellValue("Type of User");
				 cell.setCellStyle(headerCellStyle);
				 
				 cell=row.createCell(7);
				 cell.setCellValue("BIO");
				 cell.setCellStyle(headerCellStyle);
			
				 cell=row.createCell(8);
				 cell.setCellValue("Location");
				 cell.setCellStyle(headerCellStyle);
				 
				 cell=row.createCell(9);
				 cell.setCellValue("HeadLine");
				 cell.setCellStyle(headerCellStyle);
				 
				 cell=row.createCell(10);
				 cell.setCellValue("Industry Name");
				 cell.setCellStyle(headerCellStyle);
			
				 cell=row.createCell(11);
				 cell.setCellValue("Email Valid");
				 cell.setCellStyle(headerCellStyle);
			
				 cell=row.createCell(12);
				 cell.setCellValue("Mobile Valid");
				 cell.setCellStyle(headerCellStyle);
			
				 cell=row.createCell(13);
				 cell.setCellValue("UID");
				 cell.setCellStyle(headerCellStyle);
				 
				 List<Users> usersList=(List<Users>) usersRepository.findAll();
			 
				 for(int i=0;i<usersList.size();i++) {
					 Row dataRow=sheet.createRow(i+1);
					 dataRow.createCell(0).setCellValue(i+1);
					 if(usersList.get(i).getId()!=null) {
					 dataRow.createCell(1).setCellValue(usersList.get(i).getId());
					 }else {
						 dataRow.createCell(1).setCellValue("");
					 }
					 if(usersList.get(i).getFirstName()!=null) {	 
					 dataRow.createCell(2).setCellValue(usersList.get(i).getFirstName());
				     }else {
					 dataRow.createCell(2).setCellValue("");
				       }
					 if(usersList.get(i).getLastName()!=null) {
					 dataRow.createCell(3).setCellValue(usersList.get(i).getLastName());
	                 }else {
		             dataRow.createCell(3).setCellValue("");
	                }
					 if(usersList.get(i).getEmailId()!=null) {
					 dataRow.createCell(4).setCellValue(usersList.get(i).getEmailId());
                   }else {
	               dataRow.createCell(4).setCellValue("");
                    }
					 if(usersList.get(i).getMobileNumber()!=null) {
					 dataRow.createCell(5).setCellValue(usersList.get(i).getMobileNumber());
				     }else {
					 dataRow.createCell(5).setCellValue("");
				      }
					 if(usersList.get(i).getTypeOfUser()!=null) {
					 dataRow.createCell(6).setCellValue(usersList.get(i).getTypeOfUser());
				 }else {
					 dataRow.createCell(6).setCellValue("");
				 }
					 if(usersList.get(i).getBio()!=null) {
					 dataRow.createCell(7).setCellValue(usersList.get(i).getBio());
				 }else {
					 dataRow.createCell(7).setCellValue("");
				 }
					 if(usersList.get(i).getLocation()!=null) {
					 dataRow.createCell(8).setCellValue(usersList.get(i).getLocation());
				 }else {
					 dataRow.createCell(8).setCellValue("");
				 }
					 if(usersList.get(i).getHeadline()!=null) {
					 dataRow.createCell(9).setCellValue(usersList.get(i).getHeadline());
				 }else {
					 dataRow.createCell(9).setCellValue("");
				 }
				 if(usersList.get(i).getIndustryName()!=null) {
					 dataRow.createCell(10).setCellValue(usersList.get(i).getIndustryName());
				 }else {
					 dataRow.createCell(10).setCellValue("");
				 }
				 if(usersList.get(i).getIsEmailVerified()!=null) {
					 dataRow.createCell(11).setCellValue(usersList.get(i).getIsEmailVerified());
				 }else {
					 dataRow.createCell(11).setCellValue("");
				 }	
				 if(usersList.get(i).getIsMobileVerified()!=null) {
					 dataRow.createCell(12).setCellValue(usersList.get(i).getIsMobileVerified());
				 }else {
					 dataRow.createCell(12).setCellValue("");
				 }	
				 if(usersList.get(i).getUid()!=null) {
					 dataRow.createCell(13).setCellValue(usersList.get(i).getUid());
				 }else {
					 dataRow.createCell(13).setCellValue("");
				 }	
				 }
			 
				 for(int i=0;i<usersList.size();i++) {
				     sheet.autoSizeColumn(i);
				 }
				 
				 ByteArrayOutputStream outPutStream=new ByteArrayOutputStream();
				 workbook.write(outPutStream);
				 
				 ByteArrayInputStream inputStream= new ByteArrayInputStream(outPutStream.toByteArray());
				 IOUtils.copy(inputStream,response.getOutputStream());
				 
				 
		 }catch (Exception e) {
			// TODO: handle exception
			
		}
		
	}
	
	
	@GetMapping("/companyData")
	public String getCompanyData(HttpSession session,Model model)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		String username=(String) session.getAttribute("username");
		Users piyushUser=usersRepository.findByEmailId(email1);
		Users rushabhUser=usersRepository.findByEmailId(email2);
		if((piyushUser.getEmailId()+" "+piyushUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",piyushUser.getFirstName()+" "+piyushUser.getLastName());
			model.addAttribute("image",piyushUser.getProfilePicture());
		}else if((rushabhUser.getEmailId()+" "+rushabhUser.getPassword()).equals(username))
		{
			model.addAttribute("admiName",rushabhUser.getFirstName()+" "+rushabhUser.getLastName());
			model.addAttribute("image",rushabhUser.getProfilePicture());
		}
		if(isRejectedSuccess)
		{
			model.addAttribute("errorMsg","rejected user successfully");
			isRejectedSuccess=false;
		}if(isVerifiedSuccess)
		{
			model.addAttribute("success","verified user successfully");
			isVerifiedSuccess=false;
		}
		List<Company> companyList= (List<Company>) companyRepository.findAllCompany();
		model.addAttribute("company",companyList);
		return "company-data";
	}
	
	@RequestMapping(value="/companyDeactive",method = RequestMethod.GET)
	public String inActiveCompany(HttpSession session,Model model,@RequestParam("id")Long id)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		Company company=companyRepository.findOne(id);
		if(company!=null) {
			company.setIsActive("inactive");
			companyRepository.save(company);
			return "redirect:/companyData";
		}else {
			
		}
		return "redirect:/companyData";
	}
	
	
	@RequestMapping(value="/companyActive",method = RequestMethod.GET)
	public String activeCompany(HttpSession session,Model model,@RequestParam("id")Long id)
	{
		if(session.getAttribute("username")==null)
		{
			return "redirect:/login";
		}
	
		Company company=companyRepository.findOne(id);
		if(company!=null) {
			company.setIsActive("active");
			companyRepository.save(company);
			return "redirect:/companyData";
		}else {
			
		}
		return "redirect:/companyData";
	}
	
	
}
