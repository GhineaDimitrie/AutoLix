package com.example.demo.repository;

import com.example.demo.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator,Long> {

    Utilizator findByUsername(String username);
}
