package org.example.station2_cuttingandpartsregistration.repository;

import org.example.station2_cuttingandpartsregistration.model.AnimalPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalPartRepository extends JpaRepository<AnimalPart, Integer> {
    List<AnimalPart> findByAnimalId(Integer animalId);
}
