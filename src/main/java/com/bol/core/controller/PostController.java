package com.bol.core.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
import com.bol.core.domain.JsonObjectFormatPage;
import com.bol.core.dto.CommentsOnPost;
import com.bol.core.dto.Connections;
import com.bol.core.dto.LikeDislikeOnPost;
import com.bol.core.dto.LikeObj;
import com.bol.core.dto.LikeOnPostComment;
import com.bol.core.dto.Post;
import com.bol.core.dto.Publish;
import com.bol.core.dto.University;
import com.bol.core.dto.Users;
import com.bol.core.exception.CustomParameterizedException;
import com.bol.core.repository.CommentOnPostRepository;
import com.bol.core.repository.ConnectionsRepository;
import com.bol.core.repository.ExperienceRepository;
import com.bol.core.repository.LikeDislikeOnPostRepository;
import com.bol.core.repository.LikeOnPostCommentRepository;
import com.bol.core.repository.PostRepository;
import com.bol.core.repository.UniversityRepository;
import com.bol.core.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "Post controller",description = "used to share a post,add comment on post and like dislike ")
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	UsersRepository UsersRepository;
	
	
	private final Logger log = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	PostRepository PostRepository;
	
	@Autowired
	UniversityRepository   UniversityRepository;
	
	@Autowired
	ConnectionsRepository ConnectionsRepository;
	
	@Autowired
	CommentOnPostRepository CommentOnPostRepository;  
	
	@Autowired
	LikeDislikeOnPostRepository LikeDislikeOnPostRepository;
	
	
	@Autowired
	ExperienceRepository  ExperienceRepository;
	
	@Autowired
	LikeOnPostCommentRepository LikeOnPostCommentRepository;
	
	@Transactional
	@PostMapping("/post")
	public ResponseEntity<String> savePost(@RequestBody Post reqPost) throws JsonProcessingException {
		log.debug("REST request to save Post : {}", reqPost);
		System.out.println(reqPost+"   "+reqPost.getUserId());
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		reqPost.setDate(strdate);
		reqPost.setTime(time);
		Users user=UsersRepository.findById(reqPost.getUserId());
		if(user.getId()==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Post post=PostRepository.save(reqPost);
	
		if(post!=null)
		{
			post.setUserName(user.getFirstName()+" "+user.getLastName());
			post.setProfilePicture(user.getProfilePicture());
			if(user.getHeadline()!=null)
				post.setTitle(user.getHeadline());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("post saved successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(post);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to post successfully");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	
	@Transactional
	@PutMapping("/post")
	public ResponseEntity<String> editPost(@RequestBody Post reqPost) throws JsonProcessingException {
		log.debug("REST request to edit Post : {}", reqPost);
		
		
		Post temp=PostRepository.findById(reqPost.getId());
		if(temp==null)
		{
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("post not available");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
		if(reqPost.getDescription()!=null) {
			temp.setDescription(reqPost.getDescription());
		}	
		if(reqPost.getImage()!=null) {
			temp.setImage(reqPost.getImage());
		}
		
		Post post=PostRepository.save(temp);
	
		if(post!=null)
		{
			Users user=UsersRepository.findById(post.getUserId());
			post.setUserName(user.getFirstName()+" "+user.getLastName());
			post.setProfilePicture(user.getProfilePicture());
			if(user.getHeadline()!=null)
				post.setTitle(user.getHeadline());
			Pageable comPage=new PageRequest(0, 2);
			List<CommentsOnPost> commentOnPost=CommentOnPostRepository.findByPostIdLimit(post.getId(),comPage);
			
			
			 LikeDislikeOnPost like=LikeDislikeOnPostRepository.findByPostIdAndUserId(post.getId(), user.getId());
			 if(like!=null)
			 {
				 if(like.getIsLiked()!=null)
				 {
					 if(like.getIsLiked().equalsIgnoreCase("true"))
					 post.setIsLikedByUser("true");
				 }
				 if(like.getIsDisliked()!=null)
				 {
					 if(like.getIsDisliked().equalsIgnoreCase("true"))
						 post.setIsDislikedByUser("true");
				 }
			 }
			 if(commentOnPost!=null && !commentOnPost.isEmpty())
			 {
				 for(CommentsOnPost comment:commentOnPost)
				 {
					LikeOnPostComment likeOnComment= LikeOnPostCommentRepository.findByPostCommentIdAndUserId(comment.getId(), user.getId());
					 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
					 {
						 comment.setIsLikedByUser(likeOnComment.getIsLiked());
					 }
					 Users comUser=UsersRepository.findById(comment.getUserId());
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
				 post.setCommentsOnPostList(commentOnPost);
			 }
			
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("post updated successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(post);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to update post ");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	@Transactional
	@PostMapping("/post/comment")
	public ResponseEntity<String> commentOnPost(@RequestBody CommentsOnPost reqComment) throws JsonProcessingException {
		log.debug("REST request to comment on post : {}", reqComment);
		
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String strdate=dateFormat.format(date);
		SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
		String time=timeFormat.format(date);
		reqComment.setDate(strdate);
		reqComment.setTime(time);
		Users user=UsersRepository.findById(reqComment.getUserId());
		if(user==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Post post=PostRepository.findById(reqComment.getPostId());
		if(post==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("post not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		CommentsOnPost comment=CommentOnPostRepository.save(reqComment);
		if(comment!=null)
		{
			comment.setUsername(user.getFirstName()+" "+user.getLastName());
			comment.setProfilePicture(user.getProfilePicture());
			if(user.getHeadline()!=null)
				comment.setTitle(user.getHeadline());
			
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("comment on post successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(comment);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to comment on post ");
			 jsonobjectFormat.setSuccess(false);
		
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}

	@Transactional
	@PostMapping("/post/like")
	public ResponseEntity<String> likeDislikeOnPost(@RequestBody LikeDislikeOnPost reqLikeDislike) throws JsonProcessingException {
		log.debug("REST request to like or dislike a post : {}", reqLikeDislike);
		LikeDislikeOnPost result=null;
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		LikeDislikeOnPost temp=LikeDislikeOnPostRepository.findByPostIdAndUserId(reqLikeDislike.getPostId(),reqLikeDislike.getUserId());
		Post post=PostRepository.findById(reqLikeDislike.getPostId());
		if(post==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("post not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		Users user=UsersRepository.findById(reqLikeDislike.getUserId());
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
			result=LikeDislikeOnPostRepository.save(temp);
		}else {
			reqLikeDislike.setDate(strdate);
			reqLikeDislike.setTime(time);
			result=LikeDislikeOnPostRepository.save(reqLikeDislike);
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
			Post pos=PostRepository.save(post);
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
	@PostMapping("/post/comment/like")
	public ResponseEntity<String> likePostedComment(@RequestBody LikeOnPostComment reqLikeOnPostComment) throws JsonProcessingException {
		log.debug("REST request to like on posted on comment : {}", reqLikeOnPostComment);
		LikeOnPostComment result=null;
		Users user=UsersRepository.findById(reqLikeOnPostComment.getUserId());
		if(user==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("user not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		
	
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();

		CommentsOnPost comOnP=CommentOnPostRepository.findById(reqLikeOnPostComment.getPostCommentId());
		if(comOnP==null)
		{
			CustomParameterizedException customExp=new CustomParameterizedException("comment not available", "500");
			ObjectMapper obj=new ObjectMapper();
			String customExr=obj.writerWithDefaultPrettyPrinter().writeValueAsString(customExp);
			return ResponseEntity.ok().body(customExr);
		}
		LikeOnPostComment temp=LikeOnPostCommentRepository.findByPostCommentIdAndUserId(reqLikeOnPostComment.getPostCommentId(),reqLikeOnPostComment.getUserId());
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
			result=LikeOnPostCommentRepository.save(temp);
		}else {
			reqLikeOnPostComment.setDate(strdate);
			reqLikeOnPostComment.setTime(time);
			result=LikeOnPostCommentRepository.save(reqLikeOnPostComment);
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
			CommentOnPostRepository.save(comOnP);
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
	
	@GetMapping("/post/users")
	public ResponseEntity<String> getAllPostByUser(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {
	
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
	
		Pageable comPage=new PageRequest(0, 5);
		 List<Post> postList=PostRepository.findByUserIdOrderByIdDesc(userId);
		
		List<Post> posts=null;
		 if(postList!=null && !postList.isEmpty())
		 {
				/*
				 * Collections.reverse(postList); if(size<=postList.size())
				 * posts=postList.subList(0, size); else posts=postList.subList(0,
				 * postList.size());
				 */
			 posts=postList;
			 for(Post post:posts)
			 {
			
				 Users postUser=UsersRepository.findById(post.getUserId());
				 post.setUserName(postUser.getFirstName()+" "+postUser.getLastName());
				 post.setProfilePicture(postUser.getProfilePicture());
					/*
					 * List<Experience> exp= ExperienceRepository.findByUserId(postUser.getId());
					 * if(exp!=null && !exp.isEmpty()) { Collections.reverse(exp);
					 * post.setTitle(exp.get(0).getTitle()); }
					 */
				 if(postUser.getHeadline()!=null)
					 post.setTitle(postUser.getHeadline());
				 
				 List<CommentsOnPost> commentOnPost=CommentOnPostRepository.findByPostIdLimit(post.getId(),comPage);
		
				
				 LikeDislikeOnPost like=LikeDislikeOnPostRepository.findByPostIdAndUserId(post.getId(), userId);
				 if(like!=null)
				 {
					 if(like.getIsLiked().equalsIgnoreCase("true"))
					 post.setIsLikedByUser("true");
					 if(like.getIsDisliked().equalsIgnoreCase("true"))
						 post.setIsDislikedByUser("true");
				 }
				 for(CommentsOnPost comment:commentOnPost)
				 {
					LikeOnPostComment likeOnComment= LikeOnPostCommentRepository.findByPostCommentIdAndUserId(comment.getId(), userId);
					 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
					 {
						 comment.setIsLikedByUser(likeOnComment.getIsLiked());
					 }
					 Users comUser=UsersRepository.findById(comment.getUserId());
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
				 post.setCommentsOnPostList(commentOnPost);
				
			 }
			 
		/*	 Collections.sort(posts, new Comparator<Post>()
			  { 
				  public int compare (Post o2,Post o1)
				  { 
					
					return (o1.getDate()+" "+o1.getTime()).compareTo(o2.getDate()+" "+o2.getTime()); 
				  } 
			  });*/
			
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 Page<Post> result=null;
			 if(size<=postList.size())
			 {
				 result=new PageImpl<>(posts.subList(0, size), pageable, postList.size());
				 if(size==postList.size())
				 {
					 jsonobjectFormat.setEndOfList(true);
				 }
			 }
			 else {
				
				 result=new PageImpl<>(posts.subList(0, posts.size()), pageable, postList.size());
				 jsonobjectFormat.setEndOfList(true);
			 }
		 
		 jsonobjectFormat.setMessage("got all post by user succeessfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 jsonobjectFormat.setMessage("no post yet");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setEndOfList(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@GetMapping("/post/connect/users")
	public ResponseEntity<String> getAllPostByUserConnections(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId) throws JsonProcessingException {

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
	
		List<Post> result=new ArrayList<Post>();
		List<Connections> connect=ConnectionsRepository.findByUserId(userId);
	
		try{
			Connections connectObj=new Connections();
			connectObj.setId(0L);
			connectObj.setUserId(userId);
			connectObj.setConnectedUserId(201L);
			connectObj.setIsConnected("true");
			connectObj.setIsFollowed("true");
			boolean checkFlag=false;
			for(int i=0;i<connect.size();i++){
				Connections obj=connect.get(i);
				if((obj.getUserId()!=null) && (obj.getConnectedUserId()!=null)){
					if((obj.getUserId()==connectObj.getUserId()) && (obj.getConnectedUserId()==connectObj.getConnectedUserId())){
						checkFlag=true;
					}
				}
			}
			
			if(!checkFlag){
				connect.add(connectObj);
			}
		}catch(Exception e1){
			
		}
		
		/*
		 * Point 1 getting post of all user's connections with 201 users connection
		 * 
		 */
		//in 'connect' we got list of connect
		if(connect!=null && !connect.isEmpty()){
			for(int p=0;p<connect.size();p++){
				//Getting user Id of connected user 
				Connections connection=connect.get(p);
				
				if(connection.getIsConnected()!=null){
					if(connection.getIsConnected().equals("true")){
					Long userIdInSideLoop=connection.getUserId();
					Long userIdForWhichWeAreFindingPosts=0L;
				
					if(userIdInSideLoop.equals(userId)){
						//means use getConnectedUserId
						userIdForWhichWeAreFindingPosts=connection.getConnectedUserId();
					}else{
						//means use userId  as connectedUserId is user for which we are looking the posts
						userIdForWhichWeAreFindingPosts=connection.getUserId();
					}
				
					List<Post> postList=PostRepository.findByUserIdOrderByIdDesc(userIdForWhichWeAreFindingPosts);
					result.addAll(updateListOfPostsWithConnentsAndLikes(postList,userId));
					}
				}
				
			}
		}
		
		/*
		 * 
		 * Point 2 adding own posts
		 * 
		 */
		
		List<Post> postList=PostRepository.findByUserIdOrderByIdDesc(userId);
		result.addAll(updateListOfPostsWithConnentsAndLikes(postList,userId));

		HashSet<Post> setOfPost=new HashSet<Post>(result);
		ArrayList<Post> listOfNewPost =new ArrayList<Post>();
		for(Post post:setOfPost){
			listOfNewPost.add(post);
		}
		 Collections.sort(listOfNewPost, new Comparator<Post>()
		  { 
			  public int compare (Post o2,Post o1)
			  { 
				
				return (o1.getId()).compareTo(o2.getId()); 
			  } 
		  });
		 
		if(listOfNewPost!=null && !listOfNewPost.isEmpty()){
			 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
			 Page<Post> posts=null;
			 if(size<=listOfNewPost.size())
			 {
				 posts=new PageImpl<>(listOfNewPost.subList(0, size), pageable,listOfNewPost.size());
				 if(size==listOfNewPost.size())
				 jsonobjectFormat.setEndOfList(true);
			 }else {
				 jsonobjectFormat.setEndOfList(true);
				 posts=new PageImpl<>(listOfNewPost.subList(0, listOfNewPost.size()), pageable,listOfNewPost.size());
			 }
		 
			 jsonobjectFormat.setMessage("got all post by user connections succeessfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(posts);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
				 JsonObjectFormatPage jsonobjectFormat=new JsonObjectFormatPage();
				 jsonobjectFormat.setMessage("no post yet");
				 jsonobjectFormat.setSuccess(false);
				 jsonobjectFormat.setEndOfList(true);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	@DeleteMapping("post/delete/comments")
	public ResponseEntity<String> deleteCommentsForPost(@RequestParam(value = "commentId", required=true)Long commentId) throws JsonProcessingException{
		try{
		CommentOnPostRepository.delete(commentId);
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
	
	
	@GetMapping("/post/comments")
	public ResponseEntity<String> getAllCommentsByPost(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("userId")Long userId,@RequestParam("postId")Long postId) throws JsonProcessingException {
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
		List<CommentsOnPost> commentOnPost=CommentOnPostRepository.findByPostIdLimit(postId,pageab);
							
		if(commentOnPost!=null && !commentOnPost.isEmpty())
		{
		 for(CommentsOnPost comment:commentOnPost)
		 {
							 
				LikeOnPostComment likeOnComment= LikeOnPostCommentRepository.findByPostCommentIdAndUserId(comment.getId(), userId);
			    if(likeOnComment!=null && likeOnComment.getIsLiked()!=null)
				{
					comment.setIsLikedByUser(likeOnComment.getIsLiked());
				}
							 
				Users comUser=UsersRepository.findById(comment.getUserId());
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
		 Page<CommentsOnPost> posts=new PageImpl<>(commentOnPost, pageable,commentOnPost.size());
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
	
	@GetMapping("/post/likes")
	public ResponseEntity<String> getAllLikesForPost(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("postId")Long postId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	List<LikeDislikeOnPost> likeOnPostList=LikeDislikeOnPostRepository.findByPostIdLiked(postId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikeDislikeOnPost likes:likeOnPostList)
				{
					
					Users user=UsersRepository.findById(likes.getUserId());
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
	
	@GetMapping("/post/dislikes")
	public ResponseEntity<String> getAllDisLikesForPost(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("postId")Long postId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	List<LikeDislikeOnPost> likeOnPostList=LikeDislikeOnPostRepository.findByPostIdDisLiked(postId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikeDislikeOnPost likes:likeOnPostList)
				{
					
					Users user=UsersRepository.findById(likes.getUserId());
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
	
	@GetMapping("/post/comment/likes")
	public ResponseEntity<String> getAllLikesForPostComments(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size,@RequestParam("commentOnPostId")Long commentOnPostId) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
	 if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);	
	    List<LikeOnPostComment> likeOnPostComList=LikeOnPostCommentRepository.findByPostCommentIdLiked(commentOnPostId); 
				List<LikeObj> likeList=new ArrayList<>();
				for(LikeOnPostComment likes:likeOnPostComList)
				{
					
					Users user=UsersRepository.findById(likes.getUserId());
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
	
	
	@Transactional
	@DeleteMapping("/post/delete")
	public ResponseEntity<String> deletePost(@RequestParam("postId")Long postId) throws JsonProcessingException {
		try {
			
			
			List<CommentsOnPost> com=CommentOnPostRepository.findByPostId(postId);
			for(CommentsOnPost comOnP:com)
			{
				LikeOnPostCommentRepository.deleteByPostCommentId(comOnP.getId());
			}
			CommentOnPostRepository.deleteByPostId(postId);
			LikeDislikeOnPostRepository.deleteByPostId(postId);
			PostRepository.deleteById(postId);
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("deleted post successfully");
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
	
	
	
	@GetMapping("/post/univeristy")
	public ResponseEntity<String> getUniversityData() throws IOException {
		List<University> data=(List<University>) UniversityRepository.findAll();
		if(data !=null && !data.isEmpty())
		{
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("got all data  successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(data);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("data not found");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
	}
	
	/*
	 * public String insertDataInExcel() throws IOException {
	 * 
	 * String excelFilePath="C:\\Users\\sachin\\Documents\\listOfUniversities.xlsx";
	 * 
	 * 
	 * FileInputStream inputStream = new FileInputStream(excelFilePath);
	 * 
	 * Workbook workbook = new XSSFWorkbook(inputStream);
	 * 
	 * Sheet firstSheet = workbook.getSheetAt(0); Iterator<Row> rowIterator
	 * =firstSheet.iterator();
	 * 
	 * 
	 * 
	 * while (rowIterator.hasNext()) { Row nextRow = rowIterator.next();
	 * 
	 * Iterator<Cell> cellIterator = nextRow.cellIterator();
	 * 
	 * Cell nextCell = cellIterator.next(); University com=new University();
	 * 
	 * 
	 * 
	 * nextCell=cellIterator.next(); String name = nextCell.getStringCellValue();
	 * 
	 * com.setName(name); nextCell=cellIterator.next();
	 * 
	 * String city= nextCell.getStringCellValue();
	 * 
	 * com.setCity(city);
	 * 
	 * UniversityRepository.save(com);
	 * 
	 * }
	 * 
	 * 
	 * workbook.close(); return "true"; // execute the remaining queries
	 * 
	 * }
	 */

	List<Post> updateListOfPostsWithConnentsAndLikes(List<Post> postList,Long userId){
		List<Post> result=new ArrayList<Post>();
		Pageable comPage=new PageRequest(0, 5000);
		 if(postList!=null && !postList.isEmpty()) {
			 for(Post post:postList){
				 Users postUser=UsersRepository.findById(post.getUserId());
				 post.setUserName(postUser.getFirstName()+" "+postUser.getLastName());
				 post.setProfilePicture(postUser.getProfilePicture());
					/*
					 * List<Experience> exp= ExperienceRepository.findByUserId(postUser.getId());
					 * if(exp!=null && !exp.isEmpty()) { Collections.reverse(exp);
					 * post.setTitle(exp.get(0).getTitle()); }
					 */
				 if(postUser.getHeadline()!=null)
					 post.setTitle(postUser.getHeadline());
				 List<CommentsOnPost> commentOnPost=CommentOnPostRepository.findByPostIdLimit(post.getId(),comPage);
		
				
				 LikeDislikeOnPost like=LikeDislikeOnPostRepository.findByPostIdAndUserId(post.getId(), userId);
				 if(like!=null){
					 if(like.getIsLiked().equalsIgnoreCase("true"))
					 post.setIsLikedByUser("true");
					 if(like.getIsDisliked().equalsIgnoreCase("true"))
						 post.setIsDislikedByUser("true");
				 }
				 for(CommentsOnPost comment:commentOnPost){
					LikeOnPostComment likeOnComment= LikeOnPostCommentRepository.findByPostCommentIdAndUserId(comment.getId(), userId);
					 if(likeOnComment!=null && likeOnComment.getIsLiked()!=null){
						 comment.setIsLikedByUser(likeOnComment.getIsLiked());
					 }
					 
					 Users comUser=UsersRepository.findById(comment.getUserId());
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
				 post.setCommentsOnPostList(commentOnPost);
				
			 }
			result.addAll(postList);
		 }
		 
		 return result;
	}
}
