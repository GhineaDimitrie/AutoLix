package com.example.demo.controller;
import com.example.demo.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @GetMapping("/users")
    public String listaUtilizatori(Model model) {
        model.addAttribute("utilizatori", utilizatorRepository.findAll());
        return "admin-users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        utilizatorRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
