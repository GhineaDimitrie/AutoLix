package com.example.demo.repository;


import com.example.demo.entity.Masina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  MasinaRepository extends JpaRepository<Masina,String> {

    List<Masina> findByMarca(String marca);

    List<Masina> findByCuloarea(String culoarea);
    List<Masina> findByCombustibil(String combustibil);
    List<Masina> findByPretul(String pretul);
    List<Masina> findByAnul(Integer anul);
    List<Masina> findByPuterea(Integer puterea);

    List<Masina> findByMarcaAndCuloareaAndCombustibilAndPretulAndAnulAndPuterea(String marca,String culoarea,String combustibil,Double pretul,Integer anul,Integer puterea);
    List<Masina> findByUtilizatorUsername(String username);

}
