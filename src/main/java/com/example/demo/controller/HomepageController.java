package com.example.demo.controller;

import com.example.demo.entity.Masina;
import com.example.demo.repository.MasinaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomepageController {

    private final MasinaRepository masinaRepository;

    public HomepageController(MasinaRepository masinaRepository) {
        this.masinaRepository = masinaRepository;
    }

    @GetMapping("/homepage")
    public String index(Model model) {
        // 1. Luăm doar ultimele 3 sau 4 mașini adăugate pentru secțiunea "Recomandate"
        // Folosim PageRequest pentru a limita numărul de rezultate
        List<Masina> featuredMasini = masinaRepository.findAll(
                PageRequest.of(0, 6, Sort.by("anul").descending())
        ).getContent();

        // 2. Trimitem lista către homepage.html
        model.addAttribute("featuredMasini", featuredMasini);

        // 3. Putem trimite și alte date, de exemplu numărul total de mașini de pe site
        model.addAttribute("totalMasini", masinaRepository.count());

        return "homepage";
    }
}