package com.example.demo.service;

import com.example.demo.entity.Masina;
import com.example.demo.repository.MasinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasinaService {
    private final MasinaRepository repository;

    public  MasinaService(MasinaRepository repository) {
        this.repository = repository;
    }

    public List<Masina> getMasini()
    {
        return repository.findAll();
    }
    public void saveMasina(Masina masina)
    {
        repository.save(masina);
    }


    public Masina getMasina(String nr_inmatriculare)
    {
        return repository.findById(nr_inmatriculare).orElse(null);
    }


    public boolean deleteMasina(String nr_inmatriculare)
    {
        if(repository.existsById(nr_inmatriculare)) {
            repository.deleteById(nr_inmatriculare);
            return true;
        }
        return false;
    }

    public List<Masina> filtrare(String marca, String culoarea, String combustibil,Double pretul,Integer anul,Integer puterea) {
        boolean hasMarca = marca != null && !marca.isBlank();
        boolean hasCuloarea = culoarea != null && !culoarea.isBlank();
        boolean hasCombustibil = combustibil != null && !combustibil.isBlank();
        boolean hasPretul = pretul!= null;
        boolean hasAnul = anul != null ;
        boolean hasPuterea = puterea != null ;

        // You MUST 'return' the result of the repository call!
        if (hasMarca && hasCuloarea && hasCombustibil && hasPretul && hasAnul && hasPuterea) {
            return repository.findByMarcaAndCuloareaAndCombustibilAndPretulAndAnulAndPuterea(marca, culoarea, combustibil, pretul, anul, puterea);
        }

        if (hasMarca) return repository.findByMarca(marca);
        if (hasCuloarea) return repository.findByCuloarea(culoarea);
        if (hasCombustibil) return repository.findByCombustibil(combustibil);
        if (hasPretul) return repository.findByPretul(String.valueOf(pretul));
        if (hasAnul) return repository.findByAnul(anul);
        if (hasPuterea) return repository.findByPuterea(puterea);


        // If no filters are applied, only then return everything
        return repository.findAll();
    }

}
