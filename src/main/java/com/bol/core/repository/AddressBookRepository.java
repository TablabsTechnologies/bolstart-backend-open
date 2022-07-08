package com.bol.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.AddressBook;

@Repository
public interface AddressBookRepository extends PagingAndSortingRepository<AddressBook,Long>{

	List<AddressBook> findByCompanyId(Long companyid);

}
