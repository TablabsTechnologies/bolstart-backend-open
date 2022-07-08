package com.bol.core.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bol.core.dto.Users;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, Long> {

	@Query("from Users where mobile_number=?1 and password=?2 and is_email_verified='true' ")
	Users findByMobileNumberAndPassword(String email, String password);

	@Query("from Users where email_id=?1 and password=?2 and is_email_verified='true' ")
	Users findByEmailIdAndPassword(String emailId, String password);

	List<Users> findByEmailIdOrMobileNumber(String emailId, String string);

	List<Users> findAll();
	
	@Query("from Users where email_id=?1  and is_email_verified='true' ")
	Users findByEmailId(String emailId);

	@Query("from Users where mobile_number=?1  and is_email_verified='true' ")
	Users findByMobileNumber(String mobileNumber);

	Users findById(Long id);

	@Query("from Users where email_id=?1 and is_logged_google='true' and is_email_verified='true' ")
	Users findByEmailIdAndIsGoogleLogged(String emailId);

	@Query("from Users where email_id=?1 and is_fb_logged='true' and is_email_verified='true' ")
	Users findByEmailIdAndIsFbLogged(String emailId);

	void deleteById(Long id);

	@Query("from Users where type_of_user=?1 and is_email_verified='true' ")
	List<Users> findByTypeOfUser(Long userType);

	@Query("from Users where first_name like %:name% and is_email_verified='true' ")
	List<Users> findByNames(@Param("name") String name);
	

	List<Users> findByIndustryName(String industryName);

	@Query("from Users where is_mobile_verified='false' ")
	List<Users> findUsersForVerification();

//	@Query("from Users where is_email_verified='true' ")
//	List<Users> findAllVerifiedUsers();

	@Query(value="select * users where user_role=?1",nativeQuery = true)
	List<Users> findByUserRole(String userRole);
	
	@Query(value="select * from users where email_id IN ('rushabhshah92011@gmail.com','piyush@bolstart.com')",nativeQuery = true)
	List<Users> findSepcificUserByEmail();

	@Query(value="select * from users where user_role='owner' AND id=?1",nativeQuery = true)
	Users findUserByRoleOwner(Long userId);

	
	@Query("from Users")
	Page<Users> findAllVerifiedUsers(Pageable page);

	Page<Users>findAllByOrderByIdDesc(Pageable page);
	
	
	@Query(value = "SELECT * FROM bol_start.users where user_role=?1", nativeQuery = true)
	List<Users> findByIsUserOrAdmin(String userRole);
	
	@Query(value = "SELECT * FROM bol_start.users where type_of_user=?1", nativeQuery = true)
	List<Users> findByIsUserOrAdmin2(Long typeOfUser);
	
	
}
