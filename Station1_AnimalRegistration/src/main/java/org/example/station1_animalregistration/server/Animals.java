package org.example.station1_animalregistration.server;

import java.util.Date;

public class Animals {
    private Long animal_id;
    private double weight;
    private String registration_information;
    private Date registration_date;

    public Animals(Long animalId, double weight, String registrationInformation, Date registrationDate) {
        this.animal_id = animalId;
        this.weight = weight;
        this.registration_information = registrationInformation;
        this.registration_date = registrationDate;
    }

    // Getters
    public Long getAnimalId() {
        return animal_id;
    }

    public double getWeight() {
        return weight;
    }

    public String getRegistrationInformation() {
        return registration_information;
    }

    public Date getRegistrationDate() {
        return registration_date;
    }

    // Setters
    public void setAnimalId(Long animalId) {
        this.animal_id = animalId;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setRegistrationInformation(String registrationInformation) {
        this.registration_information = registrationInformation;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registration_date = registrationDate;
    }

    // Optional: Override toString for better output representation
    @Override
    public String toString() {
        return "Animal [ID=" + animal_id + ", Weight=" + weight + " kg, Registration Info=" + registration_information
                + ", Registration Date=" + registration_date + "]";
    }
}
