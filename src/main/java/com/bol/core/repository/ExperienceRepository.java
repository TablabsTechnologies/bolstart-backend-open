package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bol.core.dto.Experience;


@Repository
public interface ExperienceRepository extends PagingAndSortingRepository<Experience, Long> {

	List<Experience> findByUserId(Long id);

	Experience findById(Long id);

	void deleteById(Long experienceId);

	void deleteByUserId(Long id);
	
	@Transactional
	@Query(value="select * from experience where company_id=?1",nativeQuery = true)
	List<Experience> findEmployeeList(Long companyId);

	@Query(value="select * from experience where company_id=?2 AND user_id=?1",nativeQuery = true)
	List<Experience> findByUserIdAndCompanyId(Long userId,Long companyId);

}
