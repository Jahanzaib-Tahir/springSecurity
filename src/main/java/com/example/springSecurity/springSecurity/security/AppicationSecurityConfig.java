package com.example.springSecurity.springSecurity.security;

import com.example.springSecurity.springSecurity.student.Student;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.springSecurity.springSecurity.security.ApplicationUserRole.*;
import static com.example.springSecurity.springSecurity.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class AppicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable() //To do
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMIN_TRAINEE.name())
                .anyRequest()
                .authenticated()
                .and().httpBasic();

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails jahanzaibUser = User.builder()
                    .username("jahanzaib")
                    .password(passwordEncoder.encode("password"))
                //    .roles(ApplicationUserRole.STUDENT.name())
                .authorities(STUDENT.getGrantedAuthority()).build();



       UserDetails lindaUser = User.builder()
                                .username("linda")
                                .password(passwordEncoder.encode("password123"))
               //                 .roles(ApplicationUserRole.ADMIN.name())
               .authorities(ADMIN.getGrantedAuthority())
               .build();



        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password123"))
               // .roles(ApplicationUserRole.ADMIN_TRAINEE.name()) //Admin Trainee Role
                .authorities(ADMIN_TRAINEE.getGrantedAuthority())
                .build();

        return new  InMemoryUserDetailsManager(
                jahanzaibUser,
                lindaUser,
                tomUser
        );

    }
}
