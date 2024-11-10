package org.example.station1_animalregistration.repository;


import org.example.station1_animalregistration.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByArrivalDate(LocalDate arrivalDate);
    List<Animal> findByOrigin(String origin);
}
