package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.CompanyEmployee;

@Repository
public interface CompanyEmployeeRepository extends PagingAndSortingRepository<CompanyEmployee, Long>{

	@Query(value="select * from company_employee where company_id=?1",nativeQuery = true)
	List<CompanyEmployee> findByCompanyId(Long companyId);

	@Query(value="delete from company_employee where user_id=?1 and company_id=?2",nativeQuery = true)
	void deleteByUserId(Long userId,Long companyId);

	@Query(value="select * from company_employee where user_id=?1 and company_id=?2",nativeQuery = true)
	List<CompanyEmployee> findByUserIdAndCompanyId(Long userId, Long companyId);
	
	List<CompanyEmployee> findByUserId(Long userId); 

}
