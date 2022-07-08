package com.bol.core.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Skills;

@Repository
public interface SkillsRepository extends PagingAndSortingRepository<Skills, Long> {

	List<Skills> findByUserId(Long id);

	Skills findById(Long id);

	void deleteById(Long skillId);

	void deleteByUserId(Long id);

}
