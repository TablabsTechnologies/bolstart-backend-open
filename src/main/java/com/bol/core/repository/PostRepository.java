/**
 * 
 */
package com.bol.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Post;

/**
 * @author sachin
 *
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

	List<Post> findByUserIdOrderByIdDesc(Long userId);

	Post findById(Long postId);

	Page<Post> findByUserId(Long userId, Pageable pageable);

	void deleteById(Long postId);

	void deleteByUserId(Long id);

}
