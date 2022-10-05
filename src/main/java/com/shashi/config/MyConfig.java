package com.shashi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shashi.serviceImplementation.UserDetailsServiceImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class MyConfig  extends WebSecurityConfigurerAdapter{

    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserDetailsServiceImpl();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }

    // configuration----

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());


    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(configurer ->
                        configurer.antMatchers("/").permitAll()
                                .antMatchers("/createblog/**").hasAnyAuthority("AUTHOR","ADMIN")
                                .antMatchers("/delete/{id}").hasAnyAuthority("AUTHOR","ADMIN")
                                .antMatchers("/fullarticle/{id}/update").hasAnyAuthority("AUTHOR","ADMIN")
                )
                .formLogin()
                .and()
                .logout(LogoutConfigurer::permitAll);
    }


}