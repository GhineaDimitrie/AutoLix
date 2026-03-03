package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_utilizator", "nr_inmatriculare"})
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int nrStele; // Valoarea 1-5

    @ManyToOne
    @JoinColumn(name = "id_utilizator", nullable = false)
    private Utilizator utilizator;

    @ManyToOne
    @JoinColumn(name = "nr_inmatriculare", nullable = false)
    private Masina masina;

    private LocalDateTime created;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }
}