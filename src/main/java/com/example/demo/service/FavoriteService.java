package com.example.demo.service;

import com.example.demo.entity.Masina;
import com.example.demo.entity.Utilizator;
import com.example.demo.entity.Favorite;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.repository.UtilizatorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class FavoriteService
{


    private final FavoriteRepository favoriteRepository;
    private final UtilizatorRepository utilizatorRepository;
    private final MasinaRepository masinaRepository;
    @Transactional
    public  boolean toggleFavorite(Long userId,String nr)
    {
        Optional<Favorite> favorite = favoriteRepository.findFav(userId, nr);
        if(favorite.isPresent())
        {
            favoriteRepository.delete(favorite.get());
            return false;
        }else{
            var uRef=utilizatorRepository.getReferenceById(userId);
            var mRef=masinaRepository.getReferenceById(nr);

            Favorite f=new Favorite();
            f.setUtilizator(uRef);
            f.setMasina(mRef);
            favoriteRepository.save(f);


            return true;

        }


    }



    public List<Masina>getFavoriteCarsByUser(String username)
    {
        Utilizator user=utilizatorRepository.findByUsername(username);
        List <Favorite> favorites=favoriteRepository.findAllByUserId(user.getId_utilizator());
        return favorites.stream().map(Favorite::getMasina).toList();

    }

}
