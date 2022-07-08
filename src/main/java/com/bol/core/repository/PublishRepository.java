package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Publish;

@Repository
public interface PublishRepository extends PagingAndSortingRepository<Publish,Long>{

	@Query(value="select * from publish where user_id=?1 order by id desc",nativeQuery = true)
	List<Publish> findByUserId(Long userId);

	@Query(value="select * from publish where company_id=?1",nativeQuery = true)
	List<Publish> findByCompanyId(Long companyId);

	@Query(value="select * from publish where company_id=?1 AND user_id=?2",nativeQuery = true)
	List<Publish> findByCompanyIdAndUserId(Long companyId,Long userId);
	

	@Query(value="select * from publish where group_id=?1",nativeQuery = true)
	List<Publish> findByGrupId(Long grupId);
	
	@Query(value="select * from publish where group_id=?1 and company_id=?2",nativeQuery = true)
	List<Publish> findByGrupIdAndCompanyId(Long grupId,Long companyId);
}
