package com.bol.core.repository;

import java.util.List;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Questions;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Questions, Long> {

	List<Questions> findByUserId(Long userId);

	Questions  findById(Long questionId);

}
