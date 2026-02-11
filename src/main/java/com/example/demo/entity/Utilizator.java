package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="utilizatori")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_utilizator;
    private String nume;
    @Column(nullable=false,unique=true)
    private String username;
    @Column(nullable=false)
    private String parola;
    @Column(nullable=false)
    private String rol;


    public int getId_utilizator() {
        return id_utilizator;
    }

    public void setId_utilizator(int id_utilizator) {
        this.id_utilizator = id_utilizator;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
