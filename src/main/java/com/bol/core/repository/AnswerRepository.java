package com.bol.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Answers;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Answers, Long> {

	List<Answers> findByQuestionId(Long id);

	@Query(value="select a from Answers a where a.questionId=?1 order by a.id desc")
	List<Answers> findByQuestionIdLimit(Long id, Pageable ansPage);

	Answers findById(Long answerId);

}
