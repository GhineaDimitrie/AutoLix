package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@Entity

@Table(name="masini")
public class Masina {
    @Id @NotBlank(message = "Nu ati introdus nr de inmatriculare!")
    private String nr_inmatriculare;
    @ManyToOne
    @JoinColumn(name="id_utilizator")
    private  Utilizator utilizator;
    @NotBlank(message = "Nu ati introdus marca masinii!")
    private String marca;
    @NotBlank(message = "Nu ati introdus modelul masinii!")
    private String modelul;
    @NotBlank(message = "Nu ati introdus culoarea masinii!" )
    private String culoarea;
     @Positive @NotNull(message = "Positive number!")
    private int capacitatea_cilindrica;
    @NotBlank(message = "Nu ati introdus combustibilul masinii!" )
    private String combustibil;
    @Positive(message = "Ati introdus o valoare negativa!") @NotNull(message = "Nu ati introdus puterea!")
    private Integer puterea;
    @NotNull(message = "Nu ati introdus cuplul!") @Positive(message = "Ati introdus o valoare negativa!")
    private Integer cuplul;
   @Positive(message = "Ati introdus o valoare negativa!")
    private Integer volumul_portbagajului;
   @NotNull (message = "Nu ati introdus pretul!")@Positive(message = "Ati introdus o valoare negativa!")
    private Double pretul;




}
