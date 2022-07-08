package com.bol.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.University;

@Repository
public interface UniversityRepository extends PagingAndSortingRepository<University, Long> {

}
