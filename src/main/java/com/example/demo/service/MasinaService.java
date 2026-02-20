package com.example.demo.service;
import org.springframework.data.jpa.domain.Specification;
import com.example.demo.entity.Masina;
import com.example.demo.repository.MasinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Service
public class MasinaService
{
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


    public Page<Masina> filtrarePagini(
            String marca,
            String modelul,
            Double pretMin,
            Double pretMax,
            String combustibil,
            String culoarea,
            Integer anMin,
            Integer anMax,
            Integer putMin,
            Integer putMax,
            Double kmMin,
            Double kmMax,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Masina> spec = (root, query, cb) -> cb.conjunction();

        if (marca != null && !marca.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("marca"), marca));
        }

        if (modelul != null && !modelul.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("modelul"), modelul));
        }

        // PREȚ
        if (pretMin != null) {
            spec = spec.and((root, query, cb) -> cb.ge(root.get("pretul"), pretMin));
        }
        if (pretMax != null) {
            spec = spec.and((root, query, cb) -> cb.le(root.get("pretul"), pretMax));
        }

        if (combustibil != null && !combustibil.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("combustibil"), combustibil));
        }

        if (culoarea != null && !culoarea.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("culoarea"), culoarea));
        }

        // AN
        if (anMin != null) {
            spec = spec.and((root, query, cb) -> cb.ge(root.get("anul"), anMin));
        }
        if (anMax != null) {
            spec = spec.and((root, query, cb) -> cb.le(root.get("anul"), anMax));
        }

        // PUTERE
        if (putMin != null) {
            spec = spec.and((root, query, cb) -> cb.ge(root.get("puterea"), putMin));
        }
        if (putMax != null) {
            spec = spec.and((root, query, cb) -> cb.le(root.get("puterea"), putMax));
        }

        // KM
        if (kmMin != null) {
            spec = spec.and((root, query, cb) -> cb.ge(root.get("kilometraj"), kmMin));
        }
        if (kmMax != null) {
            spec = spec.and((root, query, cb) -> cb.le(root.get("kilometraj"), kmMax));
        }

        return repository.findAll(spec, pageable);
    }




}
