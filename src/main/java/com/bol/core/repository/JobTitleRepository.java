package com.bol.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.JobTitles;

@Repository
public interface JobTitleRepository extends PagingAndSortingRepository<JobTitles, String>{
	
	JobTitles findById(long id);
	
	@Query(value = "SELECT * FROM bol_start.job_title where title=?1", nativeQuery = true)
    JobTitles findByTitle(String title);
}
