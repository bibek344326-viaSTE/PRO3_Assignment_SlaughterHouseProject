package org.example.station1_animalregistration.service;

import org.example.station1_animalregistration.model.Animal;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AnimalClient {

    private final WebClient webClient;

    public AnimalClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/animals").build();
    }

    public Mono<String> registerAnimal(Animal animal) {
        return webClient.post()
                .uri("/register")
                .bodyValue(animal)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Animal[]> getAnimalsByDate(String arrivalDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/by-date")
                        .queryParam("arrivalDate", arrivalDate)
                        .build())
                .retrieve()
                .bodyToMono(Animal[].class);
    }
}
