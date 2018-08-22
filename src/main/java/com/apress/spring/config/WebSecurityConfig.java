package com.apress.spring.config;

import com.apress.spring.domain.User;
import com.apress.spring.repository.UserDetailsDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Created by simon on 22/08/18.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements InitializingBean {
    @Autowired
    UserDetailsDao userDetailsDao;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/login**").permitAll()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/loginAction").permitAll()
                .and()
                .logout().logoutSuccessUrl("/login").permitAll()
                .and()
                .csrf().disable();*/

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .httpBasic();
    }


    public void afterPropertiesSet() throws Exception {
        /*User user = new User();
        user.setUsername("simon");
        user.setPassword(new BCryptPasswordEncoder().encode("simon"));
        user.setEnabled(true);
        userDetailsDao.save(user);*/
    }
}