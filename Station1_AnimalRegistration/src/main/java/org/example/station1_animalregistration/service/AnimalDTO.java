package org.example.station1_animalregistration.service;

import java.time.LocalDate;

public class AnimalDTO {
  private String registrationNumber;
  private double weight;
  private String origin;
  private LocalDate arrivalDate;

  // Default constructor
  public AnimalDTO() {}

  // Getters and Setters
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
