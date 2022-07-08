package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import com.bol.core.dto.LikeDislikeOnPublish;

@Repository
public interface LikeDislikeOnPublishRepository extends PagingAndSortingRepository<LikeDislikeOnPublish, Long> {

	@Query(value="select * from like_dislike_on_publish where publish_id=?1 AND user_id=?2",nativeQuery = true)
	LikeDislikeOnPublish findByPublishIdAndUserId(Long id, Long userId);

	@Query("from LikeDislikeOnPublish where publish_id=?1 and is_liked='true'")
	List<LikeDislikeOnPublish> findByPublishIdLiked(Long postId);

	@Query("from LikeDislikeOnPublish where publish_id=?1 and is_disliked='true'")
	List<LikeDislikeOnPublish> findByPublishIdDisLiked(Long postId);
	

}
