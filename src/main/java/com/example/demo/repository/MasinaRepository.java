package com.example.demo.repository;


import com.example.demo.entity.Masina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface  MasinaRepository extends JpaRepository<Masina,String>, JpaSpecificationExecutor<Masina> {


    List<Masina> findByUtilizatorUsername(String username);

}
