package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="utilizatori")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_utilizator;
    private String nume;
    @Column(nullable=false,unique=true)
    private String username;
    @Column(nullable=false)
    private String parola;
    @Column(nullable=false)
    private String rol;



}
