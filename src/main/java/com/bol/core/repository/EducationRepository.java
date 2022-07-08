package com.bol.core.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Education;

@Repository
public interface EducationRepository extends PagingAndSortingRepository<Education,Long > {

	List<Education> findByUserId(Long id);

	Education findById(Long id);

	void deleteById(Long educationId);

	void deleteByUserId(Long id);

}
