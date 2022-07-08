package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.bol.core.dto.Company;
import com.bol.core.dto.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {




	
	List<JobPost> findByCompanyId(long companyId);
	
	List<JobPost> findByJobtitleId(long jobtitleId);

	List<JobPost> findByLocation(String Location);

	List<JobPost> findByMinExperiance(String minExperiance);

	List<JobPost> findBySkills(String skills);

	List<JobPost> findByNoticePeriod(String noticePeriod);
	
	@Query(value = "SELECT * FROM bol_start.job_post where start_date=?1", nativeQuery = true)
	List<JobPost> findByStartDate(String startDate);
	
	// List<JobPost> findByCompanyOrRecruiter(long company, long recruiter);

	//SELECT * FROM bol_start.job_post where company_id in (select b.id from bol_start.company b where b.name="Techno");
	@Query(value = "SELECT * FROM bol_start.job_post where company_id=?1", nativeQuery = true)
	List<JobPost> findByCompanyName(long companyId);
	
	JobPost findById(long id);

	@Query(value = "SELECT * FROM bol_start.users where user_role=?1", nativeQuery = true)
	List<JobPost> findByIsUserOrAdmin(String userRole/*, String emailId*/);
	
	
	@Query(value = "SELECT * FROM bol_start.job_post where recruiter=?1", nativeQuery = true)
	List<JobPost> findByRecruiter(long recruiter);
	
	//List<JobPost> findByCompanyIdAndJobTitleId(long companyId, long jobtitleId);

}
