package com.example.demo.controller;

import com.example.demo.entity.Favorite;
import com.example.demo.entity.Utilizator;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.repository.UtilizatorRepository;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.MasinaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UtilizatorRepository utilizatorRepository;
    private final FavoriteRepository favoriteRepository;


    public FavoriteController(FavoriteService favoriteService, UtilizatorRepository utilizatorRepository, FavoriteRepository favoriteRepository) {
        this.favoriteService = favoriteService;
        this.utilizatorRepository = utilizatorRepository;

        this.favoriteRepository = favoriteRepository;
    }

    @PostMapping("/favorites/toggle/{nr}")
    @ResponseBody
    public Map<String,Object> toggle(@PathVariable String nr, Principal principal)
    {
        String username = principal.getName();
        Utilizator u=utilizatorRepository.findByUsername(username);
        Long userId=u.getId_utilizator();
        boolean fav=favoriteService.toggleFavorite(userId,nr);
        return Map.of("favorite", fav);


    }
    @GetMapping("/favorites")
    public String favorites(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        Utilizator u = utilizatorRepository.findByUsername(principal.getName());

        List<Favorite> favorites = favoriteRepository
                .findAllByUserId(u.getId_utilizator());

        model.addAttribute("favorites", favorites);

        return "favorites";
    }
}

