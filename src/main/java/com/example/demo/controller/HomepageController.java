package com.example.demo.controller;

import com.example.demo.entity.Masina;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.MasinaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class HomepageController {

    private final MasinaRepository masinaRepository;
    private final MasinaService masinaService;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteService favoriteService;


    public HomepageController(MasinaRepository masinaRepository,FavoriteRepository favoriteRepository,
                              FavoriteService favoriteService,MasinaService masinaService) {
        this.masinaRepository = masinaRepository;
        this.favoriteRepository = favoriteRepository;
        this.favoriteService = favoriteService;
        this.masinaService = masinaService;
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



    @GetMapping("/tip/{numeTip}")
    public String showMasiniByTip(
            @PathVariable String numeTip,
            Model model,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelul,
            @RequestParam(required = false) Double pretMin,
            @RequestParam(required = false) Double pretMax,
            @RequestParam(required = false) String combustibil,
            @RequestParam(required = false) String culoarea,
            @RequestParam(required = false) Integer anMin,
            @RequestParam(required = false) Integer anMax,
            @RequestParam(required = false) Integer putMin,
            @RequestParam(required = false) Integer putMax,
            @RequestParam(required = false) Double kmMin,
            @RequestParam(required = false) Double kmMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String sort)
    {
        Sort s = sortFromParam(sort);

        // IMPORTANT: Trimitem 'numeTip' ca parametru de filtrare obligatoriu
        Page<Masina> masiniPage = masinaService.filtrarePagini(
                marca, modelul, numeTip, null, pretMin, pretMax,
                combustibil, culoarea, anMin, anMax, putMin, putMax,
                kmMin, kmMax, s, page, size
        );

        model.addAttribute("masini", masiniPage.getContent());
        model.addAttribute("tipSelectat", numeTip);

        // Trebuie să adăugăm parametrii în model pentru ca input-urile din HTML să-și păstreze valorile (th:value)
        model.addAttribute("marca", marca);
        model.addAttribute("modelul", modelul);
        model.addAttribute("pretMin", pretMin);
        model.addAttribute("pretMax", pretMax);
        model.addAttribute("combustibil", combustibil);
        model.addAttribute("culoarea", culoarea);
        model.addAttribute("anMin", anMin);
        model.addAttribute("anMax", anMax);
        model.addAttribute("putMin", putMin);
        model.addAttribute("putMax", putMax);
        model.addAttribute("kmMin", kmMin);
        model.addAttribute("kmMax", kmMax);
        model.addAttribute("sort", sort);

        return "vehicle-types";
    }


    private Sort sortFromParam(String sort)
    {
        if (sort == null || sort.isBlank())
            return  Sort.unsorted();


        return switch (sort) {
            case "price_asc"  -> Sort.by("pretul").ascending();
            case "price_desc" -> Sort.by("pretul").descending();
            case "year_asc" -> Sort.by("anul").ascending();
            case "year_desc" -> Sort.by("anul").descending();
            case "km_asc" -> Sort.by("kilometraj").ascending();
            case "km_desc" -> Sort.by("kilometraj").descending();
            case"pwr_asc" -> Sort.by("puterea").ascending();
            case "pwr_desc" -> Sort.by("puterea").descending();

            default -> Sort.unsorted();
        };
    }



    @GetMapping("/marca/{numeMarca}")
    public String showMasiniByMarca(
            @PathVariable String numeMarca,
            Model model,
            @RequestParam(required = false) String tip,
            @RequestParam(required = false) String modelul,
            @RequestParam(required = false) Double pretMin,
            @RequestParam(required = false) Double pretMax,
            @RequestParam(required = false) String combustibil,
            @RequestParam(required = false) String culoarea,
            @RequestParam(required = false) Integer anMin,
            @RequestParam(required = false) Integer anMax,
            @RequestParam(required = false) Integer putMin,
            @RequestParam(required = false) Integer putMax,
            @RequestParam(required = false) Double kmMin,
            @RequestParam(required = false) Double kmMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String sort)
    {
        // 1. Pregătim sortarea
        Sort s = sortFromParam(sort);

        // 2. Apelăm serviciul.
        // ATENȚIE: 'numeMarca' este primul parametru (marca), iar 'tip' este al treilea.
        Page<Masina> masiniPage = masinaService.filtrarePagini(
                numeMarca,  // marca (fixată din URL)
                modelul,    // model
                tip,        // tip caroserie
                null,       // categoria (o lăsăm null dacă nu ai filtru separat pentru ea)
                pretMin, pretMax,
                combustibil, culoarea,
                anMin, anMax,
                putMin, putMax,
                kmMin, kmMax,
                s, page, size
        );

        // 3. Adăugăm rezultatele în model
        model.addAttribute("listaMasini", masiniPage.getContent());
        model.addAttribute("masiniPage", masiniPage); // util pentru paginare
        model.addAttribute("marcaSelectata", numeMarca);

        // 4. Trimiterea parametrilor înapoi către HTML (pentru th:value)
        // Dacă uiți unul, căsuța de input se va goli după ce apeși "Caută"
        model.addAttribute("tip", tip);
        model.addAttribute("modelul", modelul);
        model.addAttribute("pretMin", pretMin);
        model.addAttribute("pretMax", pretMax);
        model.addAttribute("combustibil", combustibil);
        model.addAttribute("culoarea", culoarea);
        model.addAttribute("anMin", anMin);
        model.addAttribute("anMax", anMax);
        model.addAttribute("putMin", putMin);
        model.addAttribute("putMax", putMax);
        model.addAttribute("kmMin", kmMin);
        model.addAttribute("kmMax", kmMax);
        model.addAttribute("sort", sort);

        return "brand_results";
    }




}