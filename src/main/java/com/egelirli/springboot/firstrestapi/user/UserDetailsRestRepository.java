package com.egelirli.springboot.firstrestapi.user;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;

//@RepositoryRestController(path = )
public interface UserDetailsRestRepository 
       extends PagingAndSortingRepository<UserDetails, Long> {

	List<UserDetails> findByRole(String string);

	
	
}


