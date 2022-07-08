package com.bol.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Company;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {

	@Query(value="select * from company where user_id=?1",nativeQuery = true)
	List<Company> findByUserId(Long id);

	@Query(value="select * from company where is_verified='false'",nativeQuery = true)
	List<Company> findNotApproved();

	@Query(value="select * from company where name LIKE %?1%",nativeQuery = true)
	List<Company> findByCompanyName(String companyName);

	@Query("from Company ORDER BY creationDate DESC")
	List<Company> findAllCompany();

	@Query(value="select * from company where id=?1",nativeQuery = true)
	ArrayList<Company> findCompanyByUser(Long company_id);

	
	Company findById(Long id);
	
	List<Company> findByName(String name);
}
