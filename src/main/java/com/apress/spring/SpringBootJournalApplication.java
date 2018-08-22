package com.apress.spring;

import com.apress.spring.domain.User;
import com.apress.spring.repository.UserDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootJournalApplication {

	public static void main(String[] args) {



		SpringApplication.run(SpringBootJournalApplication.class, args);
	}


	/*String encoded=new BCryptPasswordEncoder().encode("admin@123");
	insert into users(username,password,enabled)
	values('admin','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',true);
	insert into authorities(username,authority)
	values('admin','ROLE_ADMIN');*/
}
