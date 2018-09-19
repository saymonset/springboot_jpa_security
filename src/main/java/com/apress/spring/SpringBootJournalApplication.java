package com.apress.spring;

import com.apress.spring.domain.JournalEntry;
import com.apress.spring.domain.User;
import com.apress.spring.repository.JournalRepository;
import com.apress.spring.repository.UserDetailsDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories("com.*")
@EntityScan("com.apress.spring.domain")
public class SpringBootJournalApplication {
	@Autowired
	private JournalRepository repo;
	public static void main(String[] args) {



		SpringApplication.run(SpringBootJournalApplication.class, args);
	}

	@Bean
	InitializingBean saveData(JournalRepository repo){
		return () -> {
			repo.save(new JournalEntry("Get to know Spring Boot","Today I will learn Spring 					Boot","01/01/2016"));
			repo.save(new JournalEntry("Simple Spring Boot Project","I will do my first Spring 							Boot Project","01/02/2016"));
			repo.save(new JournalEntry("Spring Boot Reading","Read more about Spring 									Boot","02/01/2016"));
			repo.save(new JournalEntry("Spring Boot in the Cloud","Spring Boot using Cloud 											Foundry","03/01/2016"));
		};
	}

	/*String encoded=new BCryptPasswordEncoder().encode("admin@123");
	insert into users(username,password,enabled)
	values('admin','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',true);
	insert into authorities(username,authority)
	values('admin','ROLE_ADMIN');*/
}
