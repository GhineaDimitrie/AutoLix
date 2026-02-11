package com.example.demo.controller;

import com.example.demo.entity.Masina;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.service.MasinaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MasinaController {
    private final MasinaService masinaService;

    public MasinaController(MasinaService masinaService) {
        this.masinaService = masinaService;

    }

    @GetMapping("/masini")
    public String showMasini(Model model,@RequestParam(required = false) String marca,@RequestParam(required = false) String culoarea,@RequestParam(required = false) String combustibil) {

        model.addAttribute("masini", masinaService.filtrare(marca,culoarea,combustibil));

        return "masini";
    }

    @GetMapping("/masini/add")
    public String showAddForm(Model model) {
        model.addAttribute("masina", new Masina());
        return "adauga-masini";
    }

    // SALVARE MASINA
    @PostMapping("/masini/add")
    public String saveMasina(@Valid @ModelAttribute Masina masina, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "adauga-masini";

        }

        masinaService.saveMasina(masina);
        return "redirect:/masini";
    }


    @GetMapping("/masini/edit/{nr_inmatriculare}")
    public String showUpdateForm(Model model, @PathVariable String nr_inmatriculare) {
        Masina masina = masinaService.getMasina(nr_inmatriculare);
        model.addAttribute("masina", masina);
        return "edit-masini";
    }

    @PostMapping("/masini/edit")
    public String updateMasina( @Valid @ModelAttribute Masina masina,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-masini";
        }
        masinaService.saveMasina(masina);
        return "redirect:/masini";
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











}
