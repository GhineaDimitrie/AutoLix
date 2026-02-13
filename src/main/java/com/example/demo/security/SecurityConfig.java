package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/","/marketplace","/images/**","/login", "/signup", "/signup/**").permitAll()
                        .requestMatchers("/my-profile/**").hasAnyRole("USER","ADMIN","EDITOR")
                        // USER + EDITOR pot vedea lista și filtra
                        .requestMatchers("/masini", "/masini/filtru").hasAnyRole( "EDITOR","ADMIN")

                        // doar EDITOR poate face CRUD
                        .requestMatchers("/masini/add", "/masini/edit/**", "/masini/delete/**")
                        .hasAnyRole("EDITOR","USER")

                        // doar ADMIN (opțional)
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // restul trebuie să fie logați
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/marketplace", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()

                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                )

                .csrf(csrf -> csrf.disable());


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}