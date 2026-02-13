package com.example.demo.controller;

import com.example.demo.dto.SignUpForm;
import com.example.demo.entity.Utilizator;
import com.example.demo.repository.UtilizatorRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UtilizatorRepository utilizatorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtilizatorRepository utilizatorRepository, PasswordEncoder passwordEncoder) {
        this.utilizatorRepository = utilizatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("form", new SignUpForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid @ModelAttribute("form") SignUpForm form,
                               BindingResult bindingResult) {

        // 1) validare parole
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Parolele nu coincid!");
        }

        // 2) username unic
        if (form.getUsername() != null && utilizatorRepository.findByUsername(form.getUsername()) != null) {
            bindingResult.rejectValue("username", "username.exists", "Username-ul există deja!");
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // 3) salvează user
        Utilizator u = new Utilizator();
        u.setUsername(form.getUsername());
        u.setParola(passwordEncoder.encode(form.getPassword()));
        u.setRol("USER"); // IMPORTANT: trebuie fără "ROLE_" la tine
        u.setNume(form.getNume());

        utilizatorRepository.save(u);

        return "redirect:/login?signupSuccess=true";
    }
}
