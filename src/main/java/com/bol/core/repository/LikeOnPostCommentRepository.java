package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.LikeDislikeOnPost;
import com.bol.core.dto.LikeOnPostComment;

@Repository
public interface LikeOnPostCommentRepository extends PagingAndSortingRepository<LikeOnPostComment, Long> {

	LikeOnPostComment findByPostCommentIdAndUserId(Long postCommentId, Long userId);

	@Query("from LikeOnPostComment where post_comment_id=?1 and is_liked='true' ")
	List<LikeOnPostComment> findByPostCommentIdLiked(Long commentOnPostId);

	void deleteByPostCommentId(Long id);

	List<LikeOnPostComment> findByUserId(Long id);

	void deleteByUserId(Long id);



}
