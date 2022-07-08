package com.bol.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.CommentsOnPost;

@Repository
public interface CommentOnPostRepository extends PagingAndSortingRepository<CommentsOnPost, Long> {

	List<CommentsOnPost> findByPostId(Long id);

	@Query(value="select p from CommentsOnPost p where p.postId=?1 order by p.id desc")
	List<CommentsOnPost> findByPostIdLimit(Long id, Pageable comPage);

	CommentsOnPost findById(Long postCommentId);

	void deleteByPostId(Long postId);

	void deleteByUserId(Long id);

}
