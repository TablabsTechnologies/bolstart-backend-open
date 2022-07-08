package com.bol.core.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Accomplishment;

@Repository
public interface AccomplishmentRepository extends PagingAndSortingRepository<Accomplishment, Long> {

	List<Accomplishment> findByUserId(Long id);

	Accomplishment findById(Long id);

	void deleteById(Long accomplishmentId);

	void deleteByUserId(Long id);

}
