package com.bol.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Countries;

@Repository
public interface CountriesRepository extends PagingAndSortingRepository<Countries, Long> {

}
