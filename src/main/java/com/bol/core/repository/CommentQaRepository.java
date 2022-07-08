package com.bol.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.CommentsOnQA;

@Repository
public interface CommentQaRepository extends PagingAndSortingRepository<CommentsOnQA, Long> {

	List<CommentsOnQA> findByAnswerId(Long id);

	@Query(value="select c from CommentsOnQA  c where c.answerId=?1  order by c.id desc")
	List<CommentsOnQA> findByAnswerIdLimit(Long id, Pageable comPage);

	CommentsOnQA findById(Long commentQAId);

}
