package com.bol.core.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.dto.AllSearchResponseEntity;
import com.bol.core.dto.Company;
import com.bol.core.dto.ConnUser;
import com.bol.core.dto.Connections;
import com.bol.core.dto.Post;
import com.bol.core.dto.Users;
import com.bol.core.exception.CustomParameterizedException;
import com.bol.core.repository.CompanyRepository;
import com.bol.core.repository.ConnectionsRepository;
import com.bol.core.repository.UsersRepository;
import com.bol.core.utils.CometChat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Connection controller",description = "used to add connections ")
@RequestMapping("/api")
public class ConnectionController {
	
	private final Logger log = LoggerFactory.getLogger(ConnectionController.class);
	
	@Autowired
	ConnectionsRepository ConnectionsRepository;
	
	@Autowired
	UsersRepository UsersRepository;
	
	@Autowired
	CometChat cometChat;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Transactional
	@PostMapping("/connection")
	public ResponseEntity<String> addConnection(@RequestBody Connections reqConn) throws JsonProcessingException {
		log.debug("REST request to add connection : {}", reqConn);
		Connections conn=null;
		Users user=UsersRepository.findById(reqConn.getUserId());
		if(user==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}		Users conUser=UsersRepository.findById(reqConn.getConnectedUserId());
		if(conUser==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user to connect not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Connections conCon=ConnectionsRepository.findByConnectedUserIdAndUserId(reqConn.getUserId(), reqConn.getConnectedUserId());
		Connections userCon=ConnectionsRepository.findByUserIdAndConnectedUserId(reqConn.getUserId(), reqConn.getConnectedUserId());
		if(conCon!=null)
		{
			
			conCon.setUserId(reqConn.getUserId());
			conCon.setConnectedUserId(reqConn.getConnectedUserId());
			String isFollowed="false";
				isFollowed=conCon.getIsFollowed();
				conCon.setIsFollowed(conCon.getIsFollowBack());
				conCon.setIsFollowBack(isFollowed);
			conCon.setIsConnected(reqConn.getIsConnected());
			
			conn=ConnectionsRepository.save(conCon);
			
		}else if(userCon!=null)
		{
			userCon.setIsConnected(reqConn.getIsConnected());
			conn=ConnectionsRepository.save(userCon);
		}else {
			reqConn.setIsFollowBack("false");
			reqConn.setIsFollowed("false");
			conn=ConnectionsRepository.save(reqConn);
		}
		
		if(conn!=null)
		{
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("connection req sent successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(conn);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to send connection request");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@Transactional
	@PostMapping("/connection/follow")
	public ResponseEntity<String> followPerson(@RequestBody Connections reqConn) throws JsonProcessingException {
		log.debug("REST request to add connection : {}", reqConn);
		Connections conn=null;
		Users user=UsersRepository.findById(reqConn.getUserId());
		if(user==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Users conUser=UsersRepository.findById(reqConn.getConnectedUserId());
		if(conUser==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user to connect not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Connections conTemp=ConnectionsRepository.findByConnectedUserIdAndUserId(reqConn.getUserId(), reqConn.getConnectedUserId());
		Connections userTemp=ConnectionsRepository.findByUserIdAndConnectedUserId(reqConn.getUserId(), reqConn.getConnectedUserId());
		if(conTemp!=null)
		{
			conTemp.setIsFollowBack(reqConn.getIsFollowed());
			conn=ConnectionsRepository.save(conTemp);
			
		}
		else if(userTemp!=null)
		{
			userTemp.setIsFollowed(reqConn.getIsFollowed());
			conn=ConnectionsRepository.save(userTemp);
		}else {
			reqConn.setIsFollowBack("false");
			conn=ConnectionsRepository.save(reqConn);
		}
		if(conn!=null)
		{
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("user followed successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(conn);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to follow user");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@Transactional
	@PutMapping("/connection")
	public ResponseEntity<String> acceptConnectionRequest(@RequestBody Connections reqConn) throws JsonProcessingException, JSONException {
		log.debug("REST request to accept connection request : {}", reqConn);
		Connections conn=null;
	        System.out.println(" reqConn "+reqConn.toString());
			Connections tempConn=ConnectionsRepository.findById(reqConn.getId());
			if(tempConn==null)
			{
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("invalid request");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);	
			}
			if(reqConn.getUserId()!=null)
			{
				Users user1=UsersRepository.findById(reqConn.getUserId());
				if(user1==null)
				{
					CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
					ObjectMapper obj=new ObjectMapper();
					String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
					return ResponseEntity.ok().body(customExr);
				}
			}
			if(reqConn.getConnectedUserId()!=null)
			{
				Users conUser=UsersRepository.findById(reqConn.getConnectedUserId());
				if(conUser==null)
				{
					CustomParameterizedException customExp=new CustomParameterizedException("user to connect not available", "500");
					ObjectMapper obj=new ObjectMapper();
					String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
					return ResponseEntity.ok().body(customExr);
				}
			}
			if(reqConn.getIsConnected()!=null)
				tempConn.setIsConnected(reqConn.getIsConnected());
			if(reqConn.getIsFollowed()!=null)
				tempConn.setIsFollowed(reqConn.getIsFollowed());
			if(reqConn.getIsFollowBack() !=null)
				tempConn.setIsFollowBack(reqConn.getIsFollowBack());
			
			 conn=ConnectionsRepository.save(tempConn);
		
		if(conn!=null)
		{
			
			Users user=UsersRepository.findById(conn.getUserId());
			Users user2=UsersRepository.findById(conn.getConnectedUserId());
			if(user.getIsPriority().equalsIgnoreCase("true") && user2.getIsPriority().equalsIgnoreCase("true"))
			{
				List<String> accepted=new ArrayList<>();
				if(user2.getUid()!=null && user.getUid()!=null)
				{
					accepted.add(user2.getUid());
					cometChat.addFriend(user.getUid(), accepted);
				}
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("connection req accepted successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(conn);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to accept connection request");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	
	@GetMapping("/connection/id")
	public ResponseEntity<String> getConnectionRequestById(@RequestParam("id")Long id) throws JsonProcessingException {
	
		Connections conn=ConnectionsRepository.findById(id);
		if(conn!=null)
		{
			
				Users connectedUser=UsersRepository.findById(conn.getConnectedUserId());
				Users user=UsersRepository.findById(conn.getUserId());
				ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
						connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),null);
				
				ConnUser rqUser=new ConnUser(user.getId(), user.getFirstName(), user.getLastName(),
						user.getHeadline(), user.getBio(), user.getProfilePicture(),null);
				conn.setConnectedUser(conUser);
				conn.setUser(rqUser);
				
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got connection request");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(conn);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("connection request not found");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/connection/user")
	public ResponseEntity<String> getConnectionRequestForUser(@RequestParam("userId")Long userId) throws JsonProcessingException {
	
		List<Connections> conn=ConnectionsRepository.findByConnectedUserIdAndIsConnected(userId,"false");
		
		if(conn!=null && !conn.isEmpty())
		{
			for(Connections con:conn)
			{
				Users connectedUser=UsersRepository.findById(con.getConnectedUserId());
				Users user=UsersRepository.findById(con.getUserId());
				
				ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
						connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),null);
				
				ConnUser rqUser=new ConnUser(user.getId(), user.getFirstName(), user.getLastName(),
						user.getHeadline(), user.getBio(), user.getProfilePicture(),null);
				con.setConnectedUser(conUser);
				con.setUser(rqUser);
				
			}
			
			
			HashSet<Connections> setOfConnections=new HashSet<Connections>(conn);
			
			ArrayList<Connections> listOfResults=new ArrayList<Connections>();
			for(Connections con:setOfConnections){
				// if(con.getIsConnected().equals("false")){
					 listOfResults.add(con);
				// }
			}
			
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all connection requests for user");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(listOfResults);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("connection request for user not found");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	
	@GetMapping("/connection/user2")
	public ResponseEntity<String> getConnectionRequestForUser2(@RequestParam("userId")Long userId) throws JsonProcessingException {
	
		List<Connections> conn=ConnectionsRepository.findByUserIdOrConnectedUserId(userId,userId);
		
		if(conn!=null && !conn.isEmpty())
		{
			for(Connections con:conn)
			{
				Users connectedUser=UsersRepository.findById(con.getConnectedUserId());
				Users user=UsersRepository.findById(con.getUserId());
				
				ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
						connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),null);
				
				ConnUser rqUser=new ConnUser(user.getId(), user.getFirstName(), user.getLastName(),
						user.getHeadline(), user.getBio(), user.getProfilePicture(),null);
				con.setConnectedUser(conUser);
				con.setUser(rqUser);
				
			}
			
			
			HashSet<Connections> setOfConnections=new HashSet<Connections>(conn);
			System.out.println(setOfConnections.size());
			
			ArrayList<Connections> listOfResults=new ArrayList<Connections>();
			for(Connections con:setOfConnections){
				 if(con.getIsConnected().equals("true")){
					 listOfResults.add(con);
				 }
			}
			
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all connection requests for user");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(listOfResults);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("connection request for user not found");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	
	@GetMapping("/connection/userId")
	public ResponseEntity<String> getAllConnectionsForUser(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=30;
		 }
	 	 if(page!=null){
			 }else{
				 page=0;
			 }
 	 
		Pageable pageable =new PageRequest(page, size);
		
		List<Connections> connectionList=ConnectionsRepository.findByUserIdOrConnectedUserId(userId,userId);
		List<ConnUser> usersList=new ArrayList<ConnUser>();
		
	if(connectionList!=null && !connectionList.isEmpty()){
		
		for(int p=0;p<connectionList.size();p++){
			//Getting user Id of connected user 
			Connections connection=connectionList.get(p);
			
			if(connection.getIsConnected()!=null){
				String isConnectedValue=connection.getIsConnected();
				if(isConnectedValue.equals("true")){
					Long userIdInSideLoop=connection.getUserId();
					Long userIdForWhichWeAreFindingPosts=0L;
				
					if(userIdInSideLoop.equals(userId)){
						//means use getConnectedUserId
						userIdForWhichWeAreFindingPosts=connection.getConnectedUserId();
					}else{
						//means use userId  as connectedUserId is user for which we are looking the posts
						userIdForWhichWeAreFindingPosts=connection.getUserId();
					}
					Users connectedUser=UsersRepository.findById(userIdForWhichWeAreFindingPosts);
					ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
							connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),null);
					usersList.add(conUser);	
				}
			}
		}
		
		 HashSet<ConnUser> uniqueUsersList=new HashSet<ConnUser>(usersList);
		 ArrayList<ConnUser> listOfUniqueUsers=new ArrayList<ConnUser>();
		 listOfUniqueUsers.addAll(uniqueUsersList);
		Page<ConnUser> result=new PageImpl<>(listOfUniqueUsers, pageable, listOfUniqueUsers.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all connections for user");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("connections for user not found");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	
	@GetMapping("/connection/search")
	public ResponseEntity<String> searchAllUsers(@RequestParam("name")String name,@RequestParam("userId")Long userId) throws JsonProcessingException {
	
		List<Users> usersList=UsersRepository.findByNames(name);
		List<Company> companyList=companyRepository.findByCompanyName(name);
		List<Connections> conn=ConnectionsRepository.findByUserId(userId);
		
		
		if((usersList!=null && !usersList.isEmpty())||(companyList!=null && companyList.size()!=0))
		{
			Users user=UsersRepository.findById(userId);
			if(usersList.contains(user))
				usersList.remove(user);
		
			
			for(Connections connection: conn){
				Long connectedUserId=connection.getConnectedUserId();
				Users userObj=UsersRepository.findById(connectedUserId);
				if(usersList.contains(userObj)){
					usersList.remove(userObj);
				}
			}
			
			
		ArrayList<AllSearchResponseEntity> 	AllSearchResponseEntityList=new ArrayList<AllSearchResponseEntity>();
		
		  for(Users users:usersList) {
			  
			  AllSearchResponseEntity entity=new AllSearchResponseEntity();
			   entity.setCompanyId(null);
			   entity.setHeadline(users.getHeadline());
			   entity.setId(users.getId());
			   entity.setProfile(users.getProfilePicture());
			   entity.setUsername(users.getFirstName()+" "+users.getLastName());
			   
			   AllSearchResponseEntityList.add(entity);
		  }   
		
		  
           for(Company company:companyList) {
			  
			  AllSearchResponseEntity entity=new AllSearchResponseEntity();
			   entity.setCompanyId(company.getId());
			   entity.setHeadline(company.getIndustry());
			   entity.setId(company.getId());
			   entity.setProfile(company.getLogo());
			   entity.setUsername(company.getName());
			   
			   AllSearchResponseEntityList.add(entity);
		  } 
		  
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got users successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(AllSearchResponseEntityList);
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
	}
	

	@GetMapping("/connection/indsustry")
	public ResponseEntity<String> getSuggestionsBasedOnIndustryType(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=30;
		 }
  	 if(page!=null){
		 }else{
			 page=0;
		 }
  	 
		Pageable pageable =new PageRequest(page, size);
		Users user=UsersRepository.findById(userId);
		List<Users> list=new ArrayList<>();
		if(user.getIndustryName()!=null)
			list.addAll(UsersRepository.findByIndustryName(user.getIndustryName()));
		
		if(list.contains(user)){
			list.remove(user);
		}
		List<Connections> connect=ConnectionsRepository.findSuggestionsByUserId(userId);
		for(Connections con:connect)
		{
			if(con.getUserId()==userId)
			{
			Users users=UsersRepository.findById(con.getConnectedUserId());
			if(list.contains(users))
				list.remove(users);
			}else {
				Users users=UsersRepository.findById(con.getUserId());
				if(list.contains(users))
					list.remove(users);
			}
		}
		if(list!=null && !list.isEmpty())
		{
//		    list.add("piyushDataHere");
//		    list.add("rushabhDataHere");
			Collections.reverse(list);
			List<Connections> following=ConnectionsRepository.findFollowingByUserId(userId);
		
			for(Connections follow:following)
			{
				Users users=UsersRepository.findById(follow.getConnectedUserId());
				if(list.contains(users))
				{
					int i=list.indexOf(users);
				
					Users use=list.get(i);
					use.setIsFollowed("true");
					use.setIsConnected(follow.getIsConnected());
					list.set(i, use);
				}
			}
			List<Connections> conFollowing=ConnectionsRepository.findFollowingByConnectedUserId(userId);
			
			for(Connections follow:conFollowing)
			{
				Users users=UsersRepository.findById(follow.getUserId());
				if(list.contains(users))
				{
					int i=list.indexOf(users);
					
					Users use=list.get(i);
					use.setIsFollowed("true");
					use.setIsConnected(follow.getIsConnected());
					list.set(i, use);
				}
			}
			Page<Users> result=new PageImpl<>(list, pageable, list.size());
			
		 if(result.getSize()!=0) {
			 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("got suggestions successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(result);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 
		 }else {
			 
			 List<Users> userlist1=UsersRepository.findSepcificUserByEmail();
			 
			 Page<Users> result1=new PageImpl<>(userlist1, pageable, userlist1.size());
			 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("got suggestions successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(result1);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			 
		 }	
//			
//		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
//		 jsonobjectFormat.setMessage("got suggestions successfully");
//		 jsonobjectFormat.setSuccess(true);
//		 jsonobjectFormat.setData(result);
//         ObjectMapper Obj = new ObjectMapper(); 
//         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no suggestions found");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/connection/following")
	public ResponseEntity<String> getFollowingPeoples(@RequestParam("userId")Long userId) throws JsonProcessingException {
		List<Connections> conn=ConnectionsRepository.findFollowingByUserId(userId);
		List<Connections> conFollow=ConnectionsRepository.findFollowingByConnectedUserId(userId);
		List<ConnUser> usersList=new ArrayList<ConnUser>();
		
		if((conn!=null && !conn.isEmpty())||(conFollow!=null && !conFollow.isEmpty()))
		{
			if(conn!=null && !conn.isEmpty())
			{
				for(Connections con:conn)
				{
					Users connectedUser=UsersRepository.findById(con.getConnectedUserId());
					
					ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
							connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),"true");
					
				
					usersList.add(conUser);	
				}
			}
			if(conFollow!=null && !conFollow.isEmpty())
			{
				for(Connections con:conFollow)
				{
					Users connectedUser=UsersRepository.findById(con.getUserId());
					
					ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
							connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),"true");		
					usersList.add(conUser);	
				}
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got following users successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(usersList);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no followings yet");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}

	@GetMapping("/connection/followers")
	public ResponseEntity<String> getFollowers(@RequestParam("userId")Long userId) throws JsonProcessingException {
		List<Connections> conn=ConnectionsRepository.findFollowersByUserId(userId);
		List<Connections> conFollow=ConnectionsRepository.findFollowersByConnectedUserId(userId);
		List<ConnUser> usersList=new ArrayList<ConnUser>();
		if((conn!=null && !conn.isEmpty())||(conFollow!=null && !conFollow.isEmpty()))
		{
			if(conn!=null && !conn.isEmpty())
			{
				for(Connections con:conn)
				{
					Users connectedUser=UsersRepository.findById(con.getConnectedUserId());
					
					ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
							connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),con.getIsFollowed());	
					usersList.add(conUser);	
				}
			}
			if((conFollow!=null && !conFollow.isEmpty()))
			{
				for(Connections con:conFollow)
				{
					Users connectedUser=UsersRepository.findById(con.getUserId());
					
					ConnUser conUser=new ConnUser(connectedUser.getId(), connectedUser.getFirstName(), connectedUser.getLastName(),
							connectedUser.getHeadline(), connectedUser.getBio(), connectedUser.getProfilePicture(),con.getIsFollowBack());
					
				
					usersList.add(conUser);	
				}
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got followers for user successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(usersList);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no followers yet");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@Transactional
	@DeleteMapping("/connection/delete")
	public ResponseEntity<String> deleteConnection(@RequestParam("userId")Long userId ,@RequestParam("connectedUserId")Long connectedUserId) throws JsonProcessingException {
		try {
			Users user1=UsersRepository.findById(userId);
			if(user1==null)
			{
				CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
				ObjectMapper obj=new ObjectMapper();
				String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
				return ResponseEntity.ok().body(customExr);
			}
			Users conUser=UsersRepository.findById(connectedUserId);
			if(conUser==null)
			{
				CustomParameterizedException customExp=new CustomParameterizedException("user to connect not available", "500");
				ObjectMapper obj=new ObjectMapper();
				String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
				return ResponseEntity.ok().body(customExr);
			}
			Connections conTemp=	ConnectionsRepository.findByConnectedUserIdAndUserId(userId, connectedUserId);
			Connections userTemp=	ConnectionsRepository.findByUserIdAndConnectedUserId(userId, connectedUserId);
			if(conTemp!=null)
			{
				ConnectionsRepository.deleteById(conTemp.getId());
				
				Users user=UsersRepository.findById(conTemp.getUserId());
				Users user2=UsersRepository.findById(conTemp.getConnectedUserId());
				List<String> accepted=new ArrayList<>();
				if(user2.getUid()!=null && user.getUid()!=null)
				{
					accepted.add(user2.getUid());
					cometChat.deleteFriend(user.getUid(), accepted);
				}
			}else if(userTemp!=null)
			{
				ConnectionsRepository.deleteById(userTemp.getId());
				Users user=UsersRepository.findById(userTemp.getUserId());
				Users user2=UsersRepository.findById(userTemp.getConnectedUserId());
				List<String> accepted=new ArrayList<>();
				if(user2.getUid()!=null && user.getUid()!=null)
				{
					accepted.add(user2.getUid());
					cometChat.deleteFriend(user.getUid(), accepted);
				}
			}else {
				JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("no connection found to be removed");
				 jsonobjectFormat.setSuccess(false);
			
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("connection removed successfully");
		 jsonobjectFormat.setSuccess(true);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }catch(Exception e) {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to remove connection");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	
	@GetMapping("/user/gst")
	public ResponseEntity<String> checkGst(@RequestParam("gst")String gst) throws JsonProcessingException {
	
		boolean flag=cometChat.checkGst(gst);
		if(flag) {
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("gst verified ");
		 jsonobjectFormat.setSuccess(true);
		
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else{
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("gst not verified");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
}
