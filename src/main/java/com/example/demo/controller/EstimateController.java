package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstimateController {

    @GetMapping("/estimator")
    public String showEstimatorPage(Model model) {
        // Pagina nu are nevoie de date inițiale din DB,
        // deoarece calculul se face pe baza input-ului utilizatorului.
        return "estimator";
    }
}
