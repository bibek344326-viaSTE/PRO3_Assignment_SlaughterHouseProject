package org.example.station1_animalregistration.service;


import org.example.station1_animalregistration.model.Animal;
import org.example.station1_animalregistration.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    // Your service method
    public Animal registerAnimal(AnimalDTO animalDTO) {
        // Convert DTO to Entity
        Animal animal = new Animal();
        animal.setRegistrationNumber(animalDTO.getRegistrationNumber());
        animal.setWeight(animalDTO.getWeight());
        animal.setOrigin(animalDTO.getOrigin());
        animal.setArrivalDate(animalDTO.getArrivalDate());

        // Save the animal entity (ID will be auto-generated)
        return animalRepository.save(animal);
    }


    public List<Animal> getAnimalsByArrivalDate(LocalDate arrivalDate) {
        return animalRepository.findByArrivalDate(arrivalDate);
    }

    public List<Animal> getAnimalsByOrigin(String origin) {
        return animalRepository.findByOrigin(origin);
    }
    public Animal updateAnimal(Long id, Animal animalDetails) {
        Animal existingAnimal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found with id " + id));

        existingAnimal.setRegistrationNumber(animalDetails.getRegistrationNumber());
        existingAnimal.setWeight(animalDetails.getWeight());
        existingAnimal.setOrigin(animalDetails.getOrigin());
        existingAnimal.setArrivalDate(animalDetails.getArrivalDate());

        return animalRepository.save(existingAnimal);
    }

    public void deleteAnimal(Long id) {
        Animal existingAnimal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found with id " + id));
        animalRepository.delete(existingAnimal);
    }
}
