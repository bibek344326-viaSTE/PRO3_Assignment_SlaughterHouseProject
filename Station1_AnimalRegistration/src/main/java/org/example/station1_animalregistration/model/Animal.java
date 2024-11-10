package org.example.station1_animalregistration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonProperty("registration_number")
    private String registrationNumber;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    @JsonProperty("arrival_date")
    private LocalDate arrivalDate;

    public Animal() {}

    public Animal(String registrationNumber, double weight, String origin, LocalDate arrivalDate) {
        this.registrationNumber = registrationNumber;
        this.weight = weight;
        this.origin = origin;
        this.arrivalDate = arrivalDate;
    }
}