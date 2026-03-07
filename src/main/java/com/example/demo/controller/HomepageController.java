package com.example.demo.controller;

import com.example.demo.entity.Masina;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.service.FavoriteService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class HomepageController {

    private final MasinaRepository masinaRepository;

    private final FavoriteRepository favoriteRepository;
    private final FavoriteService favoriteService;


    public HomepageController(MasinaRepository masinaRepository,FavoriteRepository favoriteRepository,FavoriteService favoriteService) {
        this.masinaRepository = masinaRepository;
        this.favoriteRepository = favoriteRepository;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/homepage")
    public String index(Model model, Principal principal)
    {
        List<Masina> featuredMasini = masinaRepository.findAll(
                PageRequest.of(0, 6, Sort.by("anul").descending())
        ).getContent();

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("favoriteMasini",favoriteService.getFavoriteCarsByUser(username));

        }

        List<String> tipuri = List.of("Sedan", "SUV", "Hatchback", "Coupe", "Convertible", "Compact");


        var vehicleTypes = tipuri.stream().map(t -> Map.of(
                "nume", t,
                "count", masinaRepository.countByTipIgnoreCase(t))).toList();

        model.addAttribute("featuredMasini", featuredMasini);
        model.addAttribute("totalMasini", masinaRepository.count());
        model.addAttribute("vehicleTypes", vehicleTypes);

        return "homepage";
    }
}