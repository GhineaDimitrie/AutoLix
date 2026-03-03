package com.example.demo.service;

import com.example.demo.entity.Masina;
import com.example.demo.entity.Rating;
import com.example.demo.entity.Utilizator;
import com.example.demo.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void salveazaSauActualizeaza(Utilizator utilizator, Masina masina,int nrStele)
    {

        Optional<Rating> ratingOptional=ratingRepository.findByUtilizatorAndMasina(utilizator,masina);
        Rating rating;
        if(ratingOptional.isPresent())
        {
           rating=ratingOptional.get();
           rating.setNrStele(nrStele);
        }else{
            rating=new Rating();
            rating.setUtilizator(utilizator);
            rating.setMasina(masina);
            rating.setNrStele(nrStele);
        }
        ratingRepository.save(rating);

    }


    public Double getMedie(Masina masina)
    {
        Double medie=ratingRepository.getAverageRatingForMasina( masina );
        return (medie != null) ? medie : 0.0;
    }


    public long getTotalVoturi(Masina masina) {
        return ratingRepository.countByMasina(masina);
    }


    public int getNotaDeLaUser(Utilizator utilizator, Masina masina) {
        return ratingRepository.findByUtilizatorAndMasina(utilizator, masina)
                .map(Rating::getNrStele) // Dacă există, extragem nota
                .orElse(0);             // Dacă nu există, returnăm 0
    }
}
