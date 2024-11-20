package org.example.station1_animalregistration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Entity
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

    // No-argument constructor
    public Animal() {}

    // Constructor with parameters
    public Animal(String registrationNumber, double weight, String origin, LocalDate arrivalDate) {
        this.registrationNumber = registrationNumber;
        this.weight = weight;
        this.origin = origin;
        this.arrivalDate = arrivalDate;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
