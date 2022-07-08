package com.bol.core.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.States;

@Repository
public interface StatesRepository extends PagingAndSortingRepository<States, Long> {

	List<States> findByCountryId(Long countryId);

}
