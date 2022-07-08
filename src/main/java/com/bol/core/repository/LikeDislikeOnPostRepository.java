package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.LikeDislikeOnPost;

@Repository
public interface LikeDislikeOnPostRepository extends PagingAndSortingRepository<LikeDislikeOnPost, Long> {

	LikeDislikeOnPost findByPostIdAndUserId(Long postId, Long userId);

	@Query("from LikeDislikeOnPost where post_id=?1 and is_liked='true'")
	List<LikeDislikeOnPost> findByPostIdLiked(Long postId);

	@Query("from LikeDislikeOnPost where post_id=?1 and is_disliked='true'")
	List<LikeDislikeOnPost> findByPostIdDisLiked(Long postId);

	void deleteByPostId(Long postId);

	void deleteByUserId(Long id);

}
