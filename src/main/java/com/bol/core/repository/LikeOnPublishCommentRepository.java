package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.LikeOnPublishComment;



@Repository
public interface LikeOnPublishCommentRepository extends PagingAndSortingRepository<LikeOnPublishComment, Long> {

	LikeOnPublishComment findByPublishCommentIdAndUserId(Long id, Long userId);

	@Query("from LikeOnPublishComment where publish_comment_id=?1 and is_liked='true' ")
	List<LikeOnPublishComment> findByPublishCommentIdLiked(Long commentOnPostId);
	
	
	


}
