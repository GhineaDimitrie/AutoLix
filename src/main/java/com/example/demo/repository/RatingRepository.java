package com.example.demo.repository;

import com.example.demo.entity.Masina;
import com.example.demo.entity.Rating;
import com.example.demo.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    Optional<Rating> findByUtilizatorAndMasina(Utilizator utilizator, Masina masina);


    @Query("SELECT AVG(r.nrStele) FROM Rating r WHERE r.masina = :masina")
    Double getAverageRatingForMasina(Masina masina);

    long countByMasina(Masina masina);


}
