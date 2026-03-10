package com.example.demo.controller;


import com.example.demo.entity.Masina;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.repository.UtilizatorRepository;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.MasinaService;
import com.example.demo.service.RatingService;
import com.example.demo.service.UtilizatorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AnalyticsController {


    private final MasinaService masinaService;
    private final UtilizatorRepository utilizatorRepository;
    private final MasinaRepository masinaRepository;
    private final FileStorageService fileStorageService;

    private final UtilizatorService utilizatorService;
    private final FavoriteRepository favoriteRepository;
    private final PasswordEncoder passwordEncoder;
    private final RatingService ratingService;


    public AnalyticsController(MasinaService masinaService, UtilizatorRepository utilizatorRepository, MasinaRepository masinaRepository, FileStorageService fileStorageService, FavoriteRepository favoriteRepository, UtilizatorService utilizatorService, PasswordEncoder passwordEncoder, RatingService ratingService) {
        this.masinaService = masinaService;
        this.utilizatorRepository = utilizatorRepository;
        this.masinaRepository = masinaRepository;
        this.fileStorageService = fileStorageService;
        this.utilizatorService = utilizatorService;
        this.passwordEncoder = passwordEncoder;
        this.ratingService = ratingService;


        this.favoriteRepository = favoriteRepository;
    }



    @GetMapping("/analytics")
    public String showAnalytics(Model model) {
        List<Masina> toate = masinaService.getMasini();

        long totalElectric = toate.stream()
                .filter(m -> m.getCombustibil() != null && m.getCombustibil().equalsIgnoreCase("Electric"))
                .count();

        double kmMediu = toate.stream()
                .mapToDouble(m -> m.getKilometraj() != null ? m.getKilometraj() : 0)
                .average()
                .orElse(0.0);

        // Calculăm prețul mediu (evităm împărțirea la zero)
        double pretMediu = toate.stream().mapToDouble(Masina::getPretul).average().orElse(0.0);

        // Grupăm pentru graficul Doughnut (Combustibil)
        Map<String, Long> combustibilData = toate.stream()
                .collect(Collectors.groupingBy(Masina::getCombustibil, Collectors.counting()));

        // Grupăm pentru graficul Bar (Top 5 Marci)
        Map<String, Long> marciData = toate.stream()
                .collect(Collectors.groupingBy(Masina::getMarca, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        model.addAttribute("totalAnunturi", toate.size());
        model.addAttribute("pretMediu", Math.round(pretMediu));

        // Trimitem datele pentru JavaScript
        model.addAttribute("labelsCombustibil", combustibilData.keySet());
        model.addAttribute("valuesCombustibil", combustibilData.values());
        model.addAttribute("labelsMarci", marciData.keySet());
        model.addAttribute("valuesMarci", marciData.values());
        model.addAttribute("totalElectric", totalElectric);
        model.addAttribute("kmMediu", Math.round(kmMediu));

        return "analytics";
    }

}
