package com.egelirli.springboot.firstrestapi.user;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

//@RepositoryRestController(path = )
public interface UserDetailsRestRepository 
       extends PagingAndSortingRepository<UserDetails, Long> {

	List<UserDetails> findByRole(String string);

	
	
}


