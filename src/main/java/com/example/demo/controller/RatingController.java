package com.example.demo.controller;

import com.example.demo.entity.Masina;
import com.example.demo.entity.Utilizator;
import com.example.demo.repository.MasinaRepository;
import com.example.demo.repository.UtilizatorRepository;
import com.example.demo.service.MasinaService;
import com.example.demo.service.RatingService;
import com.example.demo.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController
{

    @Autowired
    private RatingService ratingService;

    @Autowired
    private MasinaService masinaService;

    private final MasinaRepository masinaRepository;

    private final UtilizatorRepository utilizatorRepository;



    @Autowired
    private UtilizatorService utilizatorService;

    public RatingController(MasinaRepository masinaRepository, UtilizatorService utilizatorService, MasinaService masinaService,UtilizatorRepository utilizatorRepository) {
        this.masinaRepository = masinaRepository;
        this.utilizatorService = utilizatorService;
        this.masinaService = masinaService;
        this.utilizatorRepository = utilizatorRepository;

    }

    @PostMapping("/vote")
    public ResponseEntity<?> voteaza(@RequestParam String nrInmatriculare, @RequestParam int stele, Principal principal)
    {

        if(principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Masina masina = masinaRepository.findById(nrInmatriculare).orElseThrow(() -> new RuntimeException("Masina nu exista"));
        Utilizator utilizator=utilizatorRepository.findByUsername(principal.getName());
        ratingService.salveazaSauActualizeaza(utilizator,masina,stele);

        Map<String,Object> response = new HashMap<>();
        response.put("medie", ratingService.getMedie(masina));
        response.put("totalVoturi", ratingService.getTotalVoturi(masina));
        return ResponseEntity.ok(response);








    }

}
