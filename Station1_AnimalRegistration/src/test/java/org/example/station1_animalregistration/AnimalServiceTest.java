package org.example.station1_animalregistration;

import org.example.station1_animalregistration.model.Animal;
import org.example.station1_animalregistration.repository.AnimalRepository;
import org.example.station1_animalregistration.service.AnimalDTO;
import org.example.station1_animalregistration.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    private Animal animal;

    @BeforeEach
    public void setup() {
        animal = new Animal("12345", 500.0, "Farm A", LocalDate.of(2023, 10, 10));
    }

    @Test
    public void testRegisterAnimal() {
        // Arrange: Create an AnimalDTO and mock the repository behavior
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setRegistrationNumber("12345");
        animalDTO.setWeight(500.0);
        animalDTO.setOrigin("Farm A");
        animalDTO.setArrivalDate(LocalDate.of(2023, 10, 10));

        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        // Act: Call the service method
        Animal savedAnimal = animalService.registerAnimal(animalDTO);

        // Assert: Validate the result
        assertNotNull(savedAnimal);
        assertEquals("12345", savedAnimal.getRegistrationNumber());
        assertEquals(500.0, savedAnimal.getWeight());
        assertEquals("Farm A", savedAnimal.getOrigin());
    }


    @Test
    public void testGetAnimalsByArrivalDate() {
        // Arrange: Mock the repository to return a list of animals
        when(animalRepository.findByArrivalDate(any(LocalDate.class))).thenReturn(List.of(animal));

        // Act: Call the service method
        List<Animal> animals = animalService.getAnimalsByArrivalDate(LocalDate.of(2023, 10, 10));

        // Assert: Validate the result
        assertNotNull(animals);
        assertEquals(1, animals.size());
        assertEquals("12345", animals.get(0).getRegistrationNumber());
    }

    @Test
    public void testGetAnimalsByOrigin() {
        // Arrange: Mock the repository to return a list of animals
        when(animalRepository.findByOrigin(anyString())).thenReturn(List.of(animal));

        // Act: Call the service method
        List<Animal> animals = animalService.getAnimalsByOrigin("Farm A");

        // Assert: Validate the result
        assertNotNull(animals);
        assertEquals(1, animals.size());
        assertEquals("Farm A", animals.get(0).getOrigin());
    }

    @Test
    public void testUpdateAnimal() {
        // Arrange: Mock the repository behavior for finding an animal
        Animal updatedAnimal = new Animal("12345", 550.0, "Farm B", LocalDate.of(2023, 10, 12));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(Animal.class))).thenReturn(updatedAnimal);

        // Act: Call the service method
        Animal result = animalService.updateAnimal(1L, updatedAnimal);

        // Assert: Validate the updated animal details
        assertNotNull(result);
        assertEquals(550.0, result.getWeight());
        assertEquals("Farm B", result.getOrigin());
    }

    @Test
    public void testDeleteAnimal() {
        // Arrange: Mock the repository behavior
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        // Act: Call the service method
        animalService.deleteAnimal(1L);

        // Assert: Verify the delete operation
        verify(animalRepository, times(1)).delete(any(Animal.class));
    }
}

