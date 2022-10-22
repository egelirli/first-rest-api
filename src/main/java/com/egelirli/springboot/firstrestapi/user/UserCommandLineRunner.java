package com.egelirli.springboot.firstrestapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private UserDetailsJpaRepository userRepo;
	
	public UserCommandLineRunner(UserDetailsJpaRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}



	@Override
	public void run(String... args) throws Exception {
        
		logger.info("In UserCommandLineRunner");
		userRepo.save(new UserDetails("Veli", "Admin"));
		userRepo.save(new UserDetails("Deli", "User"));
		userRepo.save(new UserDetails("Jali", "User"));
		
	 	//List<UserDetails> allUSers =  userRepo.findAll();
		List<UserDetails> users =  userRepo.findByRole("User");
		users.stream()
	 	       .forEach(u-> logger.info("User: {}", u.toString()));
	 	
		

	}

}
