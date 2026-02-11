package com.example.demo.repository;


import com.example.demo.entity.Masina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  MasinaRepository extends JpaRepository<Masina,String> {

    List<Masina> findByMarca(String marca);
    List<Masina> findByCuloarea(String culoarea);
    List<Masina> findByCombustibil(String combustibil);
    List<Masina> findByMarcaAndCuloareaAndCombustibil(String marca,String culoarea,String combustibil);

}
