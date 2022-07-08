package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Cities;


@Repository
public interface CitiesRepository extends PagingAndSortingRepository<Cities, Long> {

	List<Cities> findByStateId(Long stateId);
	
	List<Cities> findByNameContainsIgnoreCase(String title);
	


}
