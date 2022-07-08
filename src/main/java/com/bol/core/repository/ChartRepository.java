package com.bol.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Chart;

@Repository
public interface ChartRepository extends PagingAndSortingRepository<Chart, Long> {

	@Query(value="select * from chart where company_id=?1 AND label=?2",nativeQuery = true)
	ArrayList<Chart> findByCompanyIdAndLabel(Long companyId, String label);

	@Query(value="select * from chart where company_id=?1 AND label=?2 AND week=?3",nativeQuery = true)
	Chart findByCompanyIdAndLabelAndWeek(Long companyId, String label, String week);

	@Query(value="select * from chart where company_id=?1 AND label=?2 AND week=?3",nativeQuery = true)
	List<Chart> findByCompanyIdAndLabelAndWeekMultipleValue(Long companyId, String label, String string);

	

	
	
}
