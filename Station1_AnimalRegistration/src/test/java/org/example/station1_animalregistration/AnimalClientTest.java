package org.example.station1_animalregistration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.example.station1_animalregistration.model.Animal;
import org.example.station1_animalregistration.service.AnimalClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@SpringBootTest
public class AnimalClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private AnimalClient animalClient;

    @Test
    public void testGetAnimalsByDate_successfulResponse() {
        // Arrange: Mock the expected response from RestTemplate
        Animal[] mockAnimals = new Animal[]{
                new Animal("12345", 500, "Farm A", LocalDate.of(2023, 10, 10))
        };
        when(restTemplate.getForEntity(anyString(), eq(Animal[].class)))
                .thenReturn(ResponseEntity.ok(mockAnimals));

        // Act: Use .block() to retrieve Animal[] from Mono<Animal[]>
        Animal[] animals = animalClient.getAnimalsByDate("2023-10-10").block();  // Block the Mono to get the value

        // Assert: Validate that the response is correct
        assertNotNull(animals);
        assertEquals(1, animals.length);
        assertEquals("12345", animals[0].getRegistrationNumber());
        assertEquals(500, animals[0].getWeight());
        assertEquals(LocalDate.of(2023, 10, 10), animals[0].getArrivalDate());
        assertEquals("Farm A", animals[0].getOrigin());
    }

    @Test
    public void testGetAnimalsByDate_emptyResponse() {
        // Arrange: Mock an empty response from RestTemplate
        Animal[] emptyAnimals = new Animal[0];
        when(restTemplate.getForEntity(anyString(), eq(Animal[].class)))
                .thenReturn(ResponseEntity.ok(emptyAnimals));

        // Act: Use .block() to retrieve Animal[] from Mono<Animal[]>
        Animal[] animals = animalClient.getAnimalsByDate("2023-10-10").block();  // Block the Mono to get the value

        // Assert: Validate that the response is correct
        assertNotNull(animals);
        assertEquals(0, animals.length);
    }

    @Test
    public void testGetAnimalsByDate_errorResponse() {
        // Arrange: Mock an error response from RestTemplate
        when(restTemplate.getForEntity(anyString(), eq(Animal[].class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // Act and Assert: Expecting an exception when the client receives an error response
        Exception exception = assertThrows(RuntimeException.class, () -> {
            animalClient.getAnimalsByDate("2023-10-10");
        });

        assertTrue(exception.getMessage().contains("500"));
    }
}
