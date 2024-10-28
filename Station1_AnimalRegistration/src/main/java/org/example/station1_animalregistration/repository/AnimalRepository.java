package org.example.station1_animalregistration.repository;


import org.example.station1_animalregistration.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    Animal findTopByOrderByAnimalIdDesc();
}

