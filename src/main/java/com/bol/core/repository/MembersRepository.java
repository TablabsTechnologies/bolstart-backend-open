package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Members;

@Repository
public interface MembersRepository extends PagingAndSortingRepository<Members, Long>{

	@Query(value="select * from members where company_id=?2 AND user_id=?1",nativeQuery = true)
	Members findByUserId(Long userId, Long companyId);

	@Query(value="select * from members where company_id=?1",nativeQuery = true)
	List<Members> findByCompanyId(Long companyId);

}
