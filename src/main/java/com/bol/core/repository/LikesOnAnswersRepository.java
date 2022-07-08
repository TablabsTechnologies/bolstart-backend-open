package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.LikesOnAnswers;

@Repository
public interface LikesOnAnswersRepository extends PagingAndSortingRepository<LikesOnAnswers, Long> {

	LikesOnAnswers findByAnswerIdAndUserId(Long answerId, Long userId);

	@Query("from LikesOnAnswers where answer_id=?1 and is_liked='true' ")
	List<LikesOnAnswers> findByAnswerIdLiked(Long answerId);

}
