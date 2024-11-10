package org.example.station1_animalregistration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private LocalDate arrivalDate;

    public Animal() {}

    // Custom constructor for easier instantiation without ID
    public Animal(String registrationNumber, double weight, String origin, LocalDate arrivalDate) {
        this.registrationNumber = registrationNumber;
        this.weight = weight;
        this.origin = origin;
        this.arrivalDate = arrivalDate;
    }
}