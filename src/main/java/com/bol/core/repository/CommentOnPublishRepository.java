package com.bol.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.CommentOnPublish;

@Repository
public interface CommentOnPublishRepository extends PagingAndSortingRepository<CommentOnPublish, Long> {

	@Query(value="select * from comment_on_publish where publish_id=?1",nativeQuery = true)
	List<CommentOnPublish> findCommentsOnPublish(Long id);

	@Query(value="select p from CommentOnPublish p where p.publishId=?1 order by p.id desc")
	List<CommentOnPublish> findByPublishIdLimit(Long id, Pageable comPage);


}
