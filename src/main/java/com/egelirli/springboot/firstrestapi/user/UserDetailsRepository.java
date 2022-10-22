package com.egelirli.springboot.firstrestapi.user;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository 
       extends JpaRepositoryImplementation<UserDetails, Long> {

	List<UserDetails> findByRole(String string);

	
	
}
