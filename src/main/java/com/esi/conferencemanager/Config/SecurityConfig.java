package com.esi.conferencemanager.Config;

import com.esi.conferencemanager.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService ;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }
    @Bean
    public BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}
    @Autowired
    protected void configureGlobale(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/author/**").hasRole("AUTHOR")
                .antMatchers("/reviewer/**").hasRole("REVIEWER")
                .antMatchers("/","/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                //.antMatchers("/r/**").hasRole("REVIEWER")
                .and()
                .formLogin()
                .loginPage("/auth/login").defaultSuccessUrl("/home",true)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/auth/login")
                .permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**");
    }


}
