package com.bol.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Institution;


@Repository
public interface InstitutionRepository extends PagingAndSortingRepository<Institution,Long >{

	

}
