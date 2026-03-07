package com.example.demo.repository;

import com.example.demo.entity.Favorite;
import com.example.demo.entity.Masina;
import com.example.demo.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("select f from Favorite f where f.utilizator.id_utilizator = :userId and f.masina.nr_inmatriculare = :nr")
    Optional<Favorite> findFav(@Param("userId") Long userId, @Param("nr") String nr);

    @Query("select count(f) > 0 from Favorite f where f.utilizator.id_utilizator = :userId and f.masina.nr_inmatriculare = :nr")
    boolean existsFav(@Param("userId") Long userId, @Param("nr") String nr);
    @Query("select f from Favorite f where f.utilizator.id_utilizator = :userId")
    List<Favorite> findAllByUserId(@Param("userId") Long userId);


}
