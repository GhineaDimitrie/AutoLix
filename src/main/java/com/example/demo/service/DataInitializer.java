package com.example.demo.service;
import com.example.demo.entity.Utilizator;
import com.example.demo.repository.UtilizatorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UtilizatorRepository utilizatorRepository,PasswordEncoder passwordEncoder)
    {
        return args ->
        {
            if(utilizatorRepository.findByUsername("Adelina")==null)
            {
                Utilizator u1= new Utilizator();
                u1.setUsername("Adelina");
                u1.setParola(passwordEncoder.encode("asd"));
                u1.setRol("ADMIN");
                utilizatorRepository.save(u1);
                System.out.println("Utilizatorul 'Adelina' a fost creat!");
            }else{
                System.out.println("Utilizatorul 'Adelina' exista deja!");
            }


        };

    }

}
