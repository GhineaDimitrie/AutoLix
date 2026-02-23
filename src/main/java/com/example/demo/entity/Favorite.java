package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="favorite", uniqueConstraints ={@UniqueConstraint(columnNames = {"id_utilizator", "nr_inmatriculare"})})
public class Favorite
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false)
    @JoinColumn(name="id_utilizator",nullable=false)
    private Utilizator utilizator;
    @ManyToOne(optional=false)
    @JoinColumn(name="nr_inmatriculare",nullable=false)
    private Masina masina;



}
