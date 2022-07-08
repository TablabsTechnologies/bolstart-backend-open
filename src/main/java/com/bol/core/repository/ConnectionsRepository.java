package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Connections;

@Repository
public interface ConnectionsRepository extends PagingAndSortingRepository<Connections, Long> {

	Connections findById(Long id);

	@Query(value="select * from connections where connected_user_id=?1 and is_connected ='false'",nativeQuery = true)
	List<Connections> findByConnectionForUser(Long userId);

	@Query(value="select * from connections where (user_id=?1 and is_connected='true') or (connected_user_id=?1 and is_connected='true') ",nativeQuery = true)
	List<Connections> findByUserId(Long userId);
	
	@Query(value="select * from connections where user_id=?1  and (is_connected='true' or is_connected='false' or is_connected=NULL) or connected_user_id=?1 and (is_connected='true' or is_connected='false' or is_connected=NULL)",nativeQuery = true)
	List<Connections> findSuggestionsByUserId(Long userId);
	

	@Query(value="select * from connections where user_id=?1 and is_followed='true'",nativeQuery = true)
	List<Connections> findFollowingByUserId(Long userId);

	
	@Query(value="select * from connections where user_id=?1 and is_follow_back='true'",nativeQuery = true)
	List<Connections> findFollowersByUserId(Long userId);

	Connections findByUserIdAndConnectedUserId(Long userId, Long connectedUserId);

	Connections findByConnectedUserIdAndUserId(Long userId, Long connectedUserId);

	@Query(value="select * from connections where connected_user_id=?1 and is_follow_back='true'",nativeQuery = true)
	List<Connections> findFollowingByConnectedUserId(Long userId);

	@Query(value="select * from connections where connected_user_id=?1 and is_followed='true'",nativeQuery = true)
	List<Connections> findFollowersByConnectedUserId(Long userId);

	void deleteById(Long id);

	@Query(value="select * from connections where is_connected='false'",nativeQuery = true)
	List<Connections> findPendingRequests();

	@Query(value="select * from connections where user_id=?1 or connected_user_id=?1",nativeQuery = true)
	Connections findUser(Long id);
	
	List<Connections> findByUserIdOrConnectedUserId(Long userId,Long connectedUserId);
	
	List<Connections> findByConnectedUserIdAndIsConnected(Long connectedUserId,String isConnected);
}
