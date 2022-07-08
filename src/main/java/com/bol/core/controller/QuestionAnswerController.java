package com.bol.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bol.core.domain.JsonObjectFormat;
import com.bol.core.domain.JsonObjectFormatPage;
import com.bol.core.dto.Answers;
import com.bol.core.dto.CommentsOnQA;
import com.bol.core.dto.Connections;
import com.bol.core.dto.Experience;
import com.bol.core.dto.LikeObj;
import com.bol.core.dto.LikeOnCommentQA;
import com.bol.core.dto.LikesOnAnswers;
import com.bol.core.dto.Post;
import com.bol.core.dto.Questions;
import com.bol.core.dto.Users;
import com.bol.core.exception.CustomParameterizedException;
import com.bol.core.repository.AnswerRepository;
import com.bol.core.repository.CommentQaRepository;
import com.bol.core.repository.ConnectionsRepository;
import com.bol.core.repository.ExperienceRepository;
import com.bol.core.repository.LikesOnAnswersRepository;
import com.bol.core.repository.LikesOnCommentQaRepository;
import com.bol.core.repository.QuestionRepository;
import com.bol.core.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Question Answer controller",description = "used to post question and answers and like comment on them")
@RequestMapping("/api")
public class QuestionAnswerController {
	
	private final Logger log = LoggerFactory.getLogger(QuestionAnswerController.class);

	@Autowired
	QuestionRepository QuestionRepository;
	
	@Autowired
	AnswerRepository AnswerRepository;

	@Autowired
	CommentQaRepository CommentQaRepository;
	
	@Autowired
	ExperienceRepository ExperienceRepository;
	
	@Autowired
	LikesOnAnswersRepository LikesOnAnswersRepository;
	
	@Autowired
	ConnectionsRepository ConnectionsRepository;
	
	@Autowired
	LikesOnCommentQaRepository LikesOnCommentQaRepository;
	
	@Autowired
	UsersRepository UsersRepository;
	
	@Transactional
	@PostMapping("/question")
	public ResponseEntity<String> saveQuestion(@RequestBody Questions reqQues) throws JsonProcessingException {
		log.debug("REST request to save Question : {}", reqQues);
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strDate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		reqQues.setDate(strDate);
		reqQues.setTime(time);
		Users user=UsersRepository.findById(reqQues.getUserId());
		if(user==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("user not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
		Questions ques=QuestionRepository.save(reqQues);
		if(ques!=null)
		{
			ques.setUserName(user.getFirstName()+" "+user.getLastName());
			if(user.getHeadline()!=null)
				ques.setTitle(user.getHeadline());
			ques.setProfilePicture(user.getProfilePicture());
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("question saved successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(ques);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to save question successfully");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@Transactional
	@PostMapping("/question/answer")
	public ResponseEntity<String> saveAnswerForQuestion(@RequestBody Answers reqAns) throws JsonProcessingException {
		log.debug("REST request to save Answer For Question : {}", reqAns);
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		reqAns.setDate(strdate);
		reqAns.setTime(time);
		Users user=UsersRepository.findById(reqAns.getUserId());
		if(user==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("user not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		Questions ques=QuestionRepository.findById(reqAns.getQuestionId());
		if(ques==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("Question not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		Answers ans=AnswerRepository.save(reqAns);
		if(ans!=null)
		{
			ans.setUserName(user.getFirstName()+" "+user.getLastName());
			ans.setProfilePicture(user.getProfilePicture());
			if(user.getHeadline()!=null)
				ans.setTitle(user.getHeadline());
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("answer saved successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(ans);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to answer question successfully");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	

	@Transactional
	@PostMapping("/question/answer/comment")
	public ResponseEntity<String> saveCommentOnAnswer(@RequestBody CommentsOnQA reqCom) throws JsonProcessingException {
		log.debug("REST request to save comment for QA : {}", reqCom);
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		reqCom.setDate(strdate);
		reqCom.setTime(time);
		Users user=UsersRepository.findById(reqCom.getUserId());
		if(user==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("user not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		Answers ans=AnswerRepository.findById(reqCom.getAnswerId());
		if(ans==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("Answer not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		CommentsOnQA com=CommentQaRepository.save(reqCom);
		if(com!=null)
		{
			com.setUserName(user.getFirstName()+" "+user.getLastName());
			com.setProfilePicture(user.getProfilePicture());
			if(user.getHeadline()!=null)
				com.setTitle(user.getHeadline());;
				
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("commented on answer successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(com);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to comment on answer successfully");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@Transactional
	@PostMapping("/question/answer/like")
	public ResponseEntity<String> likeOnAnswer(@RequestBody LikesOnAnswers reqLikeOnAnswers) throws JsonProcessingException {
		log.debug("REST request to like answer : {}", reqLikeOnAnswers);
		LikesOnAnswers result=null;
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		LikesOnAnswers temp=LikesOnAnswersRepository.findByAnswerIdAndUserId(reqLikeOnAnswers.getAnswerId(),reqLikeOnAnswers.getUserId());
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		Users user=UsersRepository.findById(reqLikeOnAnswers.getUserId());
		if(user==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("user not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
		
		Answers ans=AnswerRepository.findById(reqLikeOnAnswers.getAnswerId());
		if(ans==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("Answer not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		if(temp!=null)
		{
			if(temp.getIsLiked().equalsIgnoreCase("true"))
			{
				temp.setIsLiked("false");
				ans.setNoOfLikes(ans.getNoOfLikes()-new Long(1));
				jsonobjectFormat.setMessage("un liked the answer successfully");
			}else {
				temp.setIsLiked("true");
				if(ans.getNoOfLikes()==null)
				{
					ans.setNoOfLikes(new Long(1));
				}else
				{
				ans.setNoOfLikes(ans.getNoOfLikes()+new Long(1));
				}
				jsonobjectFormat.setMessage("liked the answer successfully");
			}
			temp.setDate(strdate);
			temp.setTime(time);
			result=LikesOnAnswersRepository.save(temp);
		}else {
			reqLikeOnAnswers.setDate(strdate);
			reqLikeOnAnswers.setTime(time);
			result=LikesOnAnswersRepository.save(reqLikeOnAnswers);
			if(result.getIsLiked().equalsIgnoreCase("true"))
			{
				if(ans.getNoOfLikes()==null)
				{
					ans.setNoOfLikes(new Long(1));
				}else {
					ans.setNoOfLikes(ans.getNoOfLikes()+new Long(1));
				}
				jsonobjectFormat.setMessage("liked the answer successfully");
			}
			else
				jsonobjectFormat.setMessage("un liked the answer successfully");
		}
		
		if(result!=null)
		{
			AnswerRepository.save(ans);
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			
			 jsonobjectFormat.setMessage("unable to like  answer");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@Transactional
	@PostMapping("/question/answer/comment/like")
	public ResponseEntity<String> likeOnCommentQA(@RequestBody LikeOnCommentQA reqLikeOnCommentQa) throws JsonProcessingException {
		log.debug("REST request to like comment qa: {}", reqLikeOnCommentQa);
		LikeOnCommentQA result=null;
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		LikeOnCommentQA temp=LikesOnCommentQaRepository.findByCommentQAIdAndUserId(reqLikeOnCommentQa.getCommentQAId(),reqLikeOnCommentQa.getUserId());
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		Users user=UsersRepository.findById(reqLikeOnCommentQa.getUserId());
		if(user==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("user not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		CommentsOnQA com=CommentQaRepository.findById(reqLikeOnCommentQa.getCommentQAId());
		if(com==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("Comment not available", "500");
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		if(temp!=null)
		{
			if(temp.getIsLiked().equalsIgnoreCase("true"))
			{
				temp.setIsLiked("false");
				com.setNoOfLikes(com.getNoOfLikes()-new Long(1));
				jsonobjectFormat.setMessage("un liked the comment successfully");
			}else {
				temp.setIsLiked("true");
				if(com.getNoOfLikes()==null)
				{
					com.setNoOfLikes(new Long(1));
				}else {
					com.setNoOfLikes(com.getNoOfLikes()+new Long(1));
				}
				
				jsonobjectFormat.setMessage("liked the comment successfully");
			}
			temp.setDate(strdate);
			temp.setTime(time);
			result=LikesOnCommentQaRepository.save(temp);
		}else {
			reqLikeOnCommentQa.setTime(time);
			reqLikeOnCommentQa.setDate(strdate);
			result=LikesOnCommentQaRepository.save(reqLikeOnCommentQa);
			if(result.getIsLiked().equalsIgnoreCase("true"))
			{
				if(com.getNoOfLikes()==null)
				{
					com.setNoOfLikes(new Long(1));
				}else {
					com.setNoOfLikes(com.getNoOfLikes()+new Long(1));
				}
				jsonobjectFormat.setMessage("liked the comment successfully");
			}
			else
				jsonobjectFormat.setMessage("un liked the comment successfully");
		}
		
		if(result!=null)
		{
			CommentQaRepository.save(com);
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			
			 jsonobjectFormat.setMessage("unable to like  comment");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@GetMapping("/question/userId")
	public ResponseEntity<String> getAllQuestionsByUser(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {

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
			
			Pageable ansPage=new PageRequest(0, 5);
		Pageable comPage=new PageRequest(0, 2);
		
		 List<Questions> quesList=QuestionRepository.findByUserId(userId);
		
		 if(quesList!=null && !quesList.isEmpty())
		 {
			 for(Questions ques:quesList)
			 {
				Users quesUser=UsersRepository.findById(ques.getUserId());
				ques.setUserName(quesUser.getFirstName()+" "+quesUser.getLastName());
				ques.setProfilePicture(quesUser.getProfilePicture());
				if(quesUser.getHeadline()!=null)
					ques.setTitle(quesUser.getHeadline());
				
				List<Answers> answerList=AnswerRepository.findByQuestionIdLimit(ques.getId(),ansPage);
				
				for(Answers ans:answerList)
				{
					Users ansUser=UsersRepository.findById(ans.getUserId());
					ans.setUserName(ansUser.getFirstName()+" "+ansUser.getLastName());
					ans.setProfilePicture(ansUser.getProfilePicture());
					if(ansUser.getHeadline()!=null)
						ans.setTitle(ansUser.getHeadline());
					
					List<CommentsOnQA> commentQaList=CommentQaRepository.findByAnswerIdLimit(ans.getId(),comPage);
					LikesOnAnswers like=LikesOnAnswersRepository.findByAnswerIdAndUserId(ans.getId(), userId);
					 if(like!=null)
					 {
						 if(like.getIsLiked().equalsIgnoreCase("true"))
						 ans.setIsLiked("true");
						
					 }
				
					 
					 for(CommentsOnQA comment:commentQaList)
					 {
						 Users comUser=UsersRepository.findById(ques.getUserId());
							comment.setUserName(comUser.getFirstName()+" "+comUser.getLastName());
							comment.setProfilePicture(comUser.getProfilePicture());
							if(comUser.getHeadline()!=null)
								comment.setTitle(comUser.getHeadline());
							
						LikeOnCommentQA likeOnComment= LikesOnCommentQaRepository.findByCommentQAIdAndUserId(comment.getId(), userId);
						 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
						 {
							 comment.setIsLikeByUser(likeOnComment.getIsLiked());
						 }
					 }
					 ans.setCommentsOnQaList(commentQaList);
				}
				ques.setAnswerList(answerList); 
			 }
			 
			 Collections.sort(quesList, new Comparator<Questions>()
			  { 
				  public int compare (Questions o2,Questions o1)
				  { 
					
					return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
				  } 
			  });
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 Page<Questions> result=null;
			 if(size<=quesList.size())
			 {
				 result=new PageImpl<>(quesList.subList(0, size), pageable, quesList.size());
				 if(size==quesList.size())
				 {
					 jsonobjectFormat.setEndOfList(true);
				 }
			 }
			 else {
				
				 result=new PageImpl<>(	quesList.subList(0,quesList.size()), pageable, quesList.size());
				 jsonobjectFormat.setEndOfList(true);
			 }
			
	
		 jsonobjectFormat.setMessage("got all questions by user succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("not questions yet");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/question/connect/userId")
	public ResponseEntity<String> getAllQuestionsByUserConnections(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {
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
		Pageable ansPage=new PageRequest(0, 5);
		List<Questions> result=new ArrayList<Questions>();
		List<Connections> connect=ConnectionsRepository.findByConnectionForUser(userId);
		if(connect!=null && !connect.isEmpty())
		{
			for(Connections con:connect)
			{
			 List<Questions> quesList=QuestionRepository.findByUserId(con.getConnectedUserId());
			 if(quesList!=null && !quesList.isEmpty())
			 {
				 for(Questions ques:quesList)
				 {
					 Users quesUser=UsersRepository.findById(ques.getUserId());
						ques.setUserName(quesUser.getFirstName()+" "+quesUser.getLastName());
						ques.setProfilePicture(quesUser.getProfilePicture());
						if(quesUser.getHeadline()!=null)
							ques.setTitle(quesUser.getHeadline());
					 
					List<Answers> answerList=AnswerRepository.findByQuestionIdLimit(ques.getId(),ansPage);
					
					for(Answers ans:answerList)
					{
						Users ansUser=UsersRepository.findById(ans.getUserId());
						ans.setUserName(ansUser.getFirstName()+" "+ansUser.getLastName());
						ans.setProfilePicture(ansUser.getProfilePicture());
						if(ansUser.getHeadline()!=null)
							ans.setTitle(ansUser.getHeadline());
						
						List<CommentsOnQA> commentQaList=CommentQaRepository.findByAnswerIdLimit(ans.getId(),comPage);
						LikesOnAnswers like=LikesOnAnswersRepository.findByAnswerIdAndUserId(ans.getId(), userId);
						 if(like!=null)
						 {
							 if(like.getIsLiked().equalsIgnoreCase("true"))
							 ans.setIsLiked("true");
							
						 }
						 for(CommentsOnQA comment:commentQaList)
						 {
							 Users comUser=UsersRepository.findById(comment.getUserId());
								comment.setUserName(comUser.getFirstName()+" "+comUser.getLastName());
								comment.setProfilePicture(comUser.getProfilePicture());
								if(comUser.getHeadline()!=null)
									comment.setTitle(comUser.getHeadline());
								
							LikeOnCommentQA likeOnComment= LikesOnCommentQaRepository.findByCommentQAIdAndUserId(comment.getId(), userId);
							 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
							 {
								 comment.setIsLikeByUser(likeOnComment.getIsLiked());
							 }
						 }
						 ans.setCommentsOnQaList(commentQaList);
					}
					ques.setAnswerList(answerList); 
				 }
				 result.addAll(quesList);
			 }
			
			}
		}
		 if(result!=null && !result.isEmpty())
		 { 
			 Collections.sort(result, new Comparator<Questions>()
		  { 
			  public int compare (Questions o2,Questions o1)
			  { 
				
				return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
			  } 
		  });
		
		 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
		 Page<Questions> resul=null;
		 if(size<=result.size())
		 {
			 resul=new PageImpl<>(result.subList(0, size), pageable, result.size());
			 if(size==result.size())
			 {
				 jsonobjectFormat.setEndOfList(true);
			 }
		 }
		 else {
			
			 resul=new PageImpl<>(result.subList(0, result.size()), pageable, result.size());
			 jsonobjectFormat.setEndOfList(true);
		 }
	
		 jsonobjectFormat.setMessage("got all questions by user connections succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(resul);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 List<Questions> quesList=QuestionRepository.findByUserId(userId);	
			 if(quesList!=null && !quesList.isEmpty())
			 {
				 for(Questions ques:quesList)
				 {
					Users quesUser=UsersRepository.findById(ques.getUserId());
					ques.setUserName(quesUser.getFirstName()+" "+quesUser.getLastName());
					ques.setProfilePicture(quesUser.getProfilePicture());
					if(quesUser.getHeadline()!=null)
						ques.setTitle(quesUser.getHeadline());
					
					List<Answers> answerList=AnswerRepository.findByQuestionIdLimit(ques.getId(),ansPage);
					
					for(Answers ans:answerList)
					{
						Users ansUser=UsersRepository.findById(ans.getUserId());
						ans.setUserName(ansUser.getFirstName()+" "+ansUser.getLastName());
						ans.setProfilePicture(ansUser.getProfilePicture());
						if(ansUser.getHeadline()!=null)
							ans.setTitle(ansUser.getHeadline());
						
						List<CommentsOnQA> commentQaList=CommentQaRepository.findByAnswerIdLimit(ans.getId(),comPage);
						LikesOnAnswers like=LikesOnAnswersRepository.findByAnswerIdAndUserId(ans.getId(), userId);
						 if(like!=null)
						 {
							 if(like.getIsLiked().equalsIgnoreCase("true"))
							 ans.setIsLiked("true");
							
						 }
					
						 
						 for(CommentsOnQA comment:commentQaList)
						 {
							 Users comUser=UsersRepository.findById(ques.getUserId());
								comment.setUserName(comUser.getFirstName()+" "+comUser.getLastName());
								comment.setProfilePicture(comUser.getProfilePicture());
								if(comUser.getHeadline()!=null)
									comment.setTitle(comUser.getHeadline());
								
							LikeOnCommentQA likeOnComment= LikesOnCommentQaRepository.findByCommentQAIdAndUserId(comment.getId(), userId);
							 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
							 {
								 comment.setIsLikeByUser(likeOnComment.getIsLiked());
							 }
						 }
						 ans.setCommentsOnQaList(commentQaList);
					}
					ques.setAnswerList(answerList); 
				 }
			 
				 Collections.sort(quesList, new Comparator<Questions>()
				  { 
					  public int compare (Questions o2,Questions o1)
					  { 
						
						return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
					  } 
				  });
			 
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 
				 Page<Questions> resul=null;
				 if(size<=quesList.size())
				 {
					 resul=new PageImpl<>(quesList.subList(0, size), pageable, quesList.size());
					 if(size==quesList.size())
					 {
						 jsonobjectFormat.setEndOfList(true);
					 }
				 }
				 else {
					
					 resul=new PageImpl<>(	quesList.subList(0,quesList.size()), pageable, quesList.size());
					 jsonobjectFormat.setEndOfList(true);
				 }
				
			 
			 jsonobjectFormat.setMessage("got all questions by user succeessfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(resul);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("not questions yet");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
			 }
	}
	
	@GetMapping("/question/connect/userId/industryType")
	public ResponseEntity<String> getAllQuestionsByUserIndustryType(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {
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
		Pageable ansPage=new PageRequest(0, 5);
		List<Users> connect=null;
		List<Questions> result=new ArrayList<Questions>();
		Users user=UsersRepository.findById(userId);
		if(user!=null & user.getIndustryName()!=null)
		{
			connect=UsersRepository.findByIndustryName(user.getIndustryName());
		}
		
		if(connect!=null && !connect.isEmpty())
		{
			for(Users con:connect)
			{
			 List<Questions> quesList=QuestionRepository.findByUserId(con.getId());
			 if(quesList!=null && !quesList.isEmpty())
			 {
				 for(Questions ques:quesList)
				 {
					 Users quesUser=UsersRepository.findById(ques.getUserId());
						ques.setUserName(quesUser.getFirstName()+" "+quesUser.getLastName());
						ques.setProfilePicture(quesUser.getProfilePicture());
						if(quesUser.getHeadline()!=null)
							ques.setTitle(quesUser.getHeadline());
						
					List<Answers> answerList=AnswerRepository.findByQuestionIdLimit(ques.getId(),ansPage);
					
					for(Answers ans:answerList)
					{
						Users ansUser=UsersRepository.findById(ans.getUserId());
						ans.setUserName(ansUser.getFirstName()+" "+ansUser.getLastName());
						ans.setProfilePicture(ansUser.getProfilePicture());
						if(ansUser.getHeadline()!=null)
							ans.setTitle(ansUser.getHeadline());
						
						List<CommentsOnQA> commentQaList=CommentQaRepository.findByAnswerIdLimit(ans.getId(),comPage);
						LikesOnAnswers like=LikesOnAnswersRepository.findByAnswerIdAndUserId(ans.getId(), userId);
						 if(like!=null)
						 {
							 if(like.getIsLiked().equalsIgnoreCase("true"))
							 ans.setIsLiked("true");
							
						 }
						 for(CommentsOnQA comment:commentQaList)
						 {
							 Users comUser=UsersRepository.findById(comment.getUserId());
								comment.setUserName(comUser.getFirstName()+" "+comUser.getLastName());
								comment.setProfilePicture(comUser.getProfilePicture());
								if(comUser.getHeadline()!=null)
									comment.setTitle(comUser.getHeadline());
								
							LikeOnCommentQA likeOnComment= LikesOnCommentQaRepository.findByCommentQAIdAndUserId(comment.getId(), userId);
							 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
							 {
								 comment.setIsLikeByUser(likeOnComment.getIsLiked());
							 }
						 }
						 ans.setCommentsOnQaList(commentQaList);
					}
					ques.setAnswerList(answerList); 
				 }
				 result.addAll(quesList);
			 }
			
			}
		}
		 if(result!=null && !result.isEmpty())
		 {
			 
			 Collections.sort(result, new Comparator<Questions>()
			  { 
				  public int compare (Questions o2,Questions o1)
				  { 
					
					return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
				  } 
			  });
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 
			 Page<Questions> resul=null;
			 if(size<=result.size())
			 {
				 resul=new PageImpl<>(result.subList(0, size), pageable, result.size());
				 if(size==result.size())
				 {
					 jsonobjectFormat.setEndOfList(true);
				 }
			 }
			 else {
				
				 resul=new PageImpl<>(	result.subList(0,result.size()), pageable, result.size());
				 jsonobjectFormat.setEndOfList(true);
			 }
		
		 jsonobjectFormat.setMessage("got all questions by user connections succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(resul);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("not questions yet");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	
	@GetMapping("/question/answers")
	public ResponseEntity<String> getAllAnswersForQuestions(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId,@RequestParam("questionId")Long quesId) throws JsonProcessingException {
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
		Pageable comPage =new PageRequest(0, 2);
		
		List<Answers> answerList=AnswerRepository.findByQuestionIdLimit(quesId,pageable);
		 if(answerList!=null && !answerList.isEmpty())
		 {
			for(Answers ans:answerList)
			{
				Users ansUser=UsersRepository.findById(ans.getUserId());
				ans.setUserName(ansUser.getFirstName()+" "+ansUser.getLastName());
				ans.setProfilePicture(ansUser.getProfilePicture());
				if(ansUser.getHeadline()!=null)
					ans.setTitle(ansUser.getHeadline());
				 
				List<CommentsOnQA> commentQaList=CommentQaRepository.findByAnswerIdLimit(ans.getId(),comPage);
				LikesOnAnswers like=LikesOnAnswersRepository.findByAnswerIdAndUserId(ans.getId(), userId);
				 if(like!=null)
				 {
					 if(like.getIsLiked().equalsIgnoreCase("true"))
					 ans.setIsLiked("true");
					
				 }
				 for(CommentsOnQA comment:commentQaList)
				 {
					 Users comUser=UsersRepository.findById(comment.getUserId());
						comment.setUserName(comUser.getFirstName()+" "+comUser.getLastName());
						comment.setProfilePicture(comUser.getProfilePicture());
						if(comUser.getHeadline()!=null)
							comment.setTitle(comUser.getHeadline());
					 
					LikeOnCommentQA likeOnComment= LikesOnCommentQaRepository.findByCommentQAIdAndUserId(comment.getId(), userId);
					 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
					 {
						 comment.setIsLikeByUser(likeOnComment.getIsLiked());
					 }
				 }
				 ans.setCommentsOnQaList(commentQaList);
			}
			 Collections.sort(answerList, new Comparator<Answers>()
			  { 
				  public int compare (Answers o2,Answers o1)
				  { 
					
					return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
				  } 
			  });
				JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 
			 Page<Answers> resul=null;
			 if(size<=answerList.size())
			 {
				 resul=new PageImpl<>(answerList.subList(0, size), pageable, answerList.size());
				 if(size==answerList.size())
				 {
					 jsonobjectFormat.setEndOfList(true);
				 }
			 }
			 else {
				
				 resul=new PageImpl<>(answerList.subList(0,answerList.size()), pageable, answerList.size());
				 jsonobjectFormat.setEndOfList(true);
			 }
		 jsonobjectFormat.setMessage("got all answers for question succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(resul);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no answers yet");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/question/answers/comments")
	public ResponseEntity<String> getAllCommentsForAnswer(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId,@RequestParam("answerId")Long answerId) throws JsonProcessingException {
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
		List<CommentsOnQA> commentQaList=CommentQaRepository.findByAnswerIdLimit(answerId,pageable);
		if(commentQaList!=null && !commentQaList.isEmpty())
		{
			for(CommentsOnQA comment:commentQaList)
			{
				 Users comUser=UsersRepository.findById(comment.getUserId());
					comment.setUserName(comUser.getFirstName()+" "+comUser.getLastName());
					comment.setProfilePicture(comUser.getProfilePicture());
					if(comUser.getHeadline()!=null)
						comment.setTitle(comUser.getHeadline());
					
				LikeOnCommentQA likeOnComment= LikesOnCommentQaRepository.findByCommentQAIdAndUserId(comment.getId(), userId);
				if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
				{
					comment.setIsLikeByUser(likeOnComment.getIsLiked());
				}
			}
				
			Collections.sort(commentQaList, new Comparator<CommentsOnQA>()
			  { 
				  public int compare (CommentsOnQA o2,CommentsOnQA o1)
				  { 
					
					return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
				  } 
			  });
				JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 
			 Page<CommentsOnQA> resul=null;
			 if(size<=commentQaList.size())
			 {
				 resul=new PageImpl<>(commentQaList.subList(0, size), pageable, commentQaList.size());
				 if(size==commentQaList.size())
				 {
					 jsonobjectFormat.setEndOfList(true);
				 }
			 }
			 else {
				
				 resul=new PageImpl<>(commentQaList.subList(0,commentQaList.size()), pageable, commentQaList.size());
				 jsonobjectFormat.setEndOfList(true);
			 }
		
		 jsonobjectFormat.setMessage("got all comments on answer succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(resul);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("no comments yet");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/question/answers/likes")
	public ResponseEntity<String> getAllLikesForAnswers(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("answerId")Long answerId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	List<LikesOnAnswers> likeOnAnsList=LikesOnAnswersRepository.findByAnswerIdLiked(answerId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikesOnAnswers likes:likeOnAnsList)
				{
					
					Users user=UsersRepository.findById(likes.getUserId());
					 LikeObj likeobj=new LikeObj(user.getId(), user.getFirstName()+" "+user.getLastName(), null,user.getProfilePicture());
					 likeobj.setProfilePicture(user.getProfilePicture());
					 
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
	
	@GetMapping("/question/answers/comment/likes")
	public ResponseEntity<String> getAllLikesForComments(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("commentOnQaId")Long commentOnQaId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	    List<LikeOnCommentQA> likeOnComList=LikesOnCommentQaRepository.findByCommentIdLiked(commentOnQaId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikeOnCommentQA likes:likeOnComList)
				{
					
					Users user=UsersRepository.findById(likes.getUserId());
					 LikeObj likeobj=new LikeObj(user.getId(), user.getFirstName()+" "+user.getLastName(), null,user.getProfilePicture());
						likeobj.setProfilePicture(user.getProfilePicture());
					 
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
}
