package com.bol.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bol.core.dto.AddressBookMemberEntity;
import java.lang.String;

@Repository
public interface AddressBookMemberRepository extends PagingAndSortingRepository<AddressBookMemberEntity, Long> {

		@Query(value="select * from addressbook_member where group_id=?1",nativeQuery = true)
		ArrayList<AddressBookMemberEntity> getByGroupId(Long id);

	@Modifying
	@Transactional
	@Query(value="delete from addressbook_member where group_id=?1",nativeQuery = true)
	void deleteAllByGroupId(Long id);
	
	List<AddressBookMemberEntity> findByEmailid(String emailid);
	
	List<AddressBookMemberEntity> findByGrupId(Long groupId);

}
