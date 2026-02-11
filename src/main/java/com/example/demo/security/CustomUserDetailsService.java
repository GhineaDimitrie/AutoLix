package com.example.demo.security;

import com.example.demo.entity.Utilizator;
import com.example.demo.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilizator u=utilizatorRepository.findByUsername(username);
        if(u==null){
            throw new UsernameNotFoundException(username);
        }

        return User.builder().username(u.getUsername()).password(u.getParola()).roles(u.getRol()).build();
    }


}
