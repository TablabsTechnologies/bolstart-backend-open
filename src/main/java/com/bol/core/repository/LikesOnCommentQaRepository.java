package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.LikeOnCommentQA;

@Repository
public interface LikesOnCommentQaRepository extends PagingAndSortingRepository<LikeOnCommentQA, Long> {

	LikeOnCommentQA findByCommentQAIdAndUserId(Long commentQAId, Long userId);

	@Query("from LikeOnCommentQA where comment_qa_id=?1 and is_liked='true' ")
	List<LikeOnCommentQA> findByCommentIdLiked(Long commentOnQaId);

}
