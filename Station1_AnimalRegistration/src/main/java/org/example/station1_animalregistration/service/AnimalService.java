package org.example.station1_animalregistration.service;


import org.example.station1_animalregistration.model.Animal;
import org.example.station1_animalregistration.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal registerAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    public List<Animal> getAnimalsByArrivalDate(LocalDate arrivalDate) {
        return animalRepository.findByArrivalDate(arrivalDate);
    }

    public List<Animal> getAnimalsByOrigin(String origin) {
        return animalRepository.findByOrigin(origin);
    }
}
