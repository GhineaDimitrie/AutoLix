package com.example.demo.controller;

import com.example.demo.entity.Masina;
import com.example.demo.entity.Utilizator;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.repository.UtilizatorRepository;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.MasinaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MasinaController {
    private final MasinaService masinaService;
    private final UtilizatorRepository utilizatorRepository;
    private final MasinaRepository masinaRepository;
    private final FileStorageService fileStorageService;

    private final FavoriteRepository favoriteRepository;



    public MasinaController(MasinaService masinaService, UtilizatorRepository utilizatorRepository, MasinaRepository masinaRepository, FileStorageService fileStorageService, FavoriteRepository favoriteRepository) {
        this.masinaService = masinaService;
        this.utilizatorRepository = utilizatorRepository;
        this.masinaRepository=masinaRepository;
        this.fileStorageService = fileStorageService;


        this.favoriteRepository = favoriteRepository;
    }

    @GetMapping("/masini")
    public String showMasini(
            Model model,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String culoarea,
            @RequestParam(required = false) String combustibil,
            @RequestParam(required = false) Double pretMin,
            @RequestParam(required = false) Double pretMax,
            @RequestParam(required = false) Integer anMin,
            @RequestParam(required = false) Integer anMax,
            @RequestParam(required = false) Integer putMin,
            @RequestParam(required = false) Integer putMax,
            @RequestParam(required = false) String modelul,
            @RequestParam(required = false) Double kmMin,
            @RequestParam(required = false) Double kmMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,@RequestParam(required = false) String sort
    ) {
        Sort s = sortFromParam(sort); // <- ADAUGĂ
        Page<Masina> masiniPage = masinaService.filtrarePagini(
                marca, modelul, pretMin, pretMax, combustibil, culoarea,
                anMin, anMax, putMin, putMax,
                kmMin, kmMax,s
                ,page, size
        );

        model.addAttribute("masiniPage", masiniPage);
        model.addAttribute("masini", masiniPage.getContent());

        model.addAttribute("marca", marca);
        model.addAttribute("culoarea", culoarea);
        model.addAttribute("combustibil", combustibil);
        model.addAttribute("pretMin", pretMin);
        model.addAttribute("pretMax", pretMax);
        model.addAttribute("anMin", anMin);
        model.addAttribute("anMax", anMax);
        model.addAttribute("putMin", putMin);
        model.addAttribute("putMax", putMax);
        model.addAttribute("modelul", modelul);
        model.addAttribute("kmMin", kmMin);
        model.addAttribute("kmMax", kmMax);
        model.addAttribute("sort", sort);

        return "masini";
    }
    @GetMapping("/masini/add")
    public String showAddForm(Model model) {
        model.addAttribute("masina", new Masina());
        return "adauga-masini";
    }

    // SALVARE MASINA
    @PostMapping("/masini/add")
    public String saveMasina(@Valid @ModelAttribute Masina masina,
                             BindingResult bindingResult,
                             @RequestParam(value="imageFiles", required=false) MultipartFile[] imageFiles,
                             Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            return "adauga-masini";
        }

        Utilizator user = utilizatorRepository.findByUsername(principal.getName());
        masina.setUtilizator(user);

        // 1) salvezi fiecare fișier și strângi filenames
        List<String> filenames = new ArrayList<>();

        if (imageFiles != null) {
            for (MultipartFile f : imageFiles) {
                if (f == null || f.isEmpty()) continue;
                String fn = fileStorageService.storeImage(f);
                if (fn != null) filenames.add(fn);
            }
        }

        // 2) setezi poza principală (optional, dar util ca fallback)
        if (!filenames.isEmpty()) {
            masina.setImageName(filenames.get(0));
        }

        // 3) setezi CSV-ul în câmpul unic
        masina.setImageNames(String.join(",", filenames));

        masinaService.saveMasina(masina);
        return "redirect:/my-profile";
    }



    @GetMapping("/masini/edit/{nr_inmatriculare}")
    public String showUpdateForm(Model model, @PathVariable String nr_inmatriculare) {
        Masina masina = masinaService.getMasina(nr_inmatriculare);
        model.addAttribute("masina", masina);
        return "edit-masini";
    }

    @PostMapping("/masini/edit")
    public String updateMasina(@Valid @ModelAttribute("masina") Masina masina,
                               BindingResult bindingResult,
                               @RequestParam(value="imageFiles", required=false) MultipartFile[] imageFiles)
            throws IOException {

        if (bindingResult.hasErrors()) {
            return "edit-masini";
        }

        Masina existing = masinaService.getMasina(masina.getNr_inmatriculare());

        // copiem campurile editabile
        existing.setAnul(masina.getAnul());
        existing.setKilometraj(masina.getKilometraj());
        existing.setMarca(masina.getMarca());
        existing.setModelul(masina.getModelul());
        existing.setCuloarea(masina.getCuloarea());
        existing.setCapacitatea_cilindrica(masina.getCapacitatea_cilindrica());
        existing.setCombustibil(masina.getCombustibil());
        existing.setPuterea(masina.getPuterea());
        existing.setCuplul(masina.getCuplul());
        existing.setVolumul_portbagajului(masina.getVolumul_portbagajului());
        existing.setPretul(masina.getPretul());

        // daca NU urci poze noi -> pastrezi pozele vechi
        boolean hasNew = false;
        List<String> filenames = new ArrayList<>();

        if (imageFiles != null) {
            for (MultipartFile f : imageFiles) {
                if (f == null || f.isEmpty()) continue;
                hasNew = true;
                String fn = fileStorageService.storeImage(f);
                if (fn != null) filenames.add(fn);
            }
        }

        if (hasNew && !filenames.isEmpty()) {
            existing.setImageName(filenames.get(0));
            existing.setImageNames(String.join(",", filenames));
        }
        // altfel nu atingi imageName/imageNames

        masinaService.saveMasina(existing);
        return "redirect:/my-profile";
    }

    @PostMapping("masini/delete/{nr_inmatriculare}")
    public String deleteMasina(@PathVariable String nr_inmatriculare, RedirectAttributes ra) {
        boolean deleted=masinaService.deleteMasina(nr_inmatriculare);

        if(deleted)
        {
            ra.addFlashAttribute("successMessage","Masina a fost stearsa!");
        }else {
            ra.addFlashAttribute("errorMessage", "Masina nu a fost gasita!");
        }
        return "redirect:/masini";


    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping({"/", "/marketplace"})
    public String showMasiniMarket(
            Model model,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String culoarea,
            @RequestParam(required = false) String combustibil,
            @RequestParam(required = false) Double pretMin,
            @RequestParam(required = false) Double pretMax,
            @RequestParam(required = false) Integer anMin,
            @RequestParam(required = false) Integer anMax,
            @RequestParam(required = false) Integer putMin,
            @RequestParam(required = false) Integer putMax,
            @RequestParam(required = false) String modelul,
            @RequestParam(required = false) Double kmMin,
            @RequestParam(required = false) Double kmMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,Principal principal,@RequestParam(required = false) String sort
    ) {

        Sort s = sortFromParam(sort);


        Page<Masina> masiniPage = masinaService.filtrarePagini(
                marca, modelul, pretMin, pretMax, combustibil, culoarea,
                anMin, anMax, putMin, putMax,
                kmMin, kmMax,s,
                page, size
        );

        model.addAttribute("masiniPage", masiniPage);
        model.addAttribute("masini", masiniPage.getContent());

        model.addAttribute("marca", marca);
        model.addAttribute("culoarea", culoarea);
        model.addAttribute("combustibil", combustibil);
        model.addAttribute("pretMin", pretMin);
        model.addAttribute("pretMax", pretMax);

        model.addAttribute("anMin", anMin);
        model.addAttribute("anMax", anMax);
        model.addAttribute("putMin", putMin);
        model.addAttribute("putMax", putMax);

        model.addAttribute("modelul", modelul);
        model.addAttribute("kmMin", kmMin);
        model.addAttribute("kmMax", kmMax);
        model.addAttribute("sort", sort);

        java.util.Set<String> favoriteNrs = java.util.Collections.emptySet();

        if (principal != null) {
            Utilizator u = utilizatorRepository.findByUsername(principal.getName());
            favoriteNrs = favoriteRepository.findAllByUserId(u.getId_utilizator())
                    .stream()
                    .map(f -> f.getMasina().getNr_inmatriculare())
                    .collect(java.util.stream.Collectors.toSet());
        }

        model.addAttribute("favoriteNrs", favoriteNrs);
        return "marketplace";
    }



    @GetMapping("/my-profile")
    public String myProfile(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("masini", masinaRepository.findByUtilizatorUsername(username));
        return "my-profile";
    }
    @GetMapping("/listing/{nr_inmatriculare}")
    public String listing(@PathVariable String nr_inmatriculare, Model model, CsrfToken csrfToken,Principal principal) {
        Masina m = masinaService.getMasina(nr_inmatriculare);
        List<String> images= Collections.emptyList();
        String csv = m.getImageNames();
        if (csv != null && !csv.isBlank()) {
            images = java.util.Arrays.stream(csv.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }

        boolean isFavorite=false;
        if(principal!=null)
        {
            Utilizator u=utilizatorRepository.findByUsername(principal.getName());
            isFavorite=favoriteRepository.existsFav(u.getId_utilizator(),nr_inmatriculare);
        }

        model.addAttribute("m", m);
        model.addAttribute("images", images);
        model.addAttribute("_csrf", csrfToken);
        model.addAttribute("isFavorite", isFavorite);

        return "listing";
    }






    private Sort sortFromParam(String sort)
    {
        if (sort == null || sort.isBlank()) return Sort.unsorted();

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






}
