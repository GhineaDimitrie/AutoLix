package com.example.demo.service;

import com.example.demo.entity.Utilizator;
import com.example.demo.repository.UtilizatorRepository;
import org.springframework.stereotype.Service;

@Service
public class UtilizatorService {

    private final UtilizatorRepository utilizatorRepository;
    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public void saveUtilizator(Utilizator utilizator){utilizatorRepository.save(utilizator);}



}
