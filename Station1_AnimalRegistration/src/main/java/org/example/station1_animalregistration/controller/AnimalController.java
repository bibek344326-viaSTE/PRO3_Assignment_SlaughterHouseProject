package org.example.station1_animalregistration.controller;

import org.example.station1_animalregistration.model.Animal;
import org.example.station1_animalregistration.service.AnimalDTO;
import org.example.station1_animalregistration.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/register")
    public ResponseEntity<Animal> registerAnimal(@RequestBody AnimalDTO animalDTO) {
        Animal savedAnimal = animalService.registerAnimal(animalDTO);
        return ResponseEntity.ok(savedAnimal);
    }


    @GetMapping("/by-date")
    public ResponseEntity<List<Animal>> getAnimalsByArrivalDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate) {
        List<Animal> animals = animalService.getAnimalsByArrivalDate(arrivalDate);
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/by-origin")
    public ResponseEntity<List<Animal>> getAnimalsByOrigin(@RequestParam String origin) {
        List<Animal> animals = animalService.getAnimalsByOrigin(origin);
        return ResponseEntity.ok(animals);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal animalDetails) {
        Animal updatedAnimal = animalService.updateAnimal(id, animalDetails);
        return ResponseEntity.ok(updatedAnimal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
