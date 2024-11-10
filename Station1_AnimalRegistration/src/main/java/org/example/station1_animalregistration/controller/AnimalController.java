package org.example.station1_animalregistration.controller;

import org.example.station1_animalregistration.model.Animal;
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
    public ResponseEntity<Animal> registerAnimal(@RequestBody Animal animal) {
        Animal savedAnimal = animalService.registerAnimal(animal);
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
}
